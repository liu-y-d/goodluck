package com.luck.Granter;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 验证码TokenGranter
 *
 * @author QM.JM
 * @description
 * @time 2020/06/09 2:56 PM
 **/
public class CaptchaTokenGranter extends AbstractTokenGranter {

   private static final String GRANT_TYPE = "captcha";

   private final AuthenticationManager authenticationManager;


   public CaptchaTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
      this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
   }

   protected CaptchaTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
      super(tokenServices, clientDetailsService, requestFactory, grantType);
      this.authenticationManager = authenticationManager;
   }

   @Override
   protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();


      Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());

      String username = parameters.get("username");
      String password = parameters.get("password");
      // Protect from downstream leaks of password
      parameters.remove("password");

      Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
      ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
      try {
         userAuth = authenticationManager.authenticate(userAuth);
      } catch (AccountStatusException | BadCredentialsException ase) {
         //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
         throw new InvalidGrantException(ase.getMessage());
      }
      // If the username/password are wrong the spec says we should send 400/invalid grant

      if (userAuth == null || !userAuth.isAuthenticated()) {
         throw new InvalidGrantException("Could not authenticate user: " + username);
      }

      OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
      return new OAuth2Authentication(storedOAuth2Request, userAuth);

   }

}
