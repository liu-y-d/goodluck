package com.luck.config;

import com.luck.constant.TokenConstant;
import com.luck.util.TokenUtil;
import com.luck.vo.LuckUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt返回参数增强
 *
 * @author QM.JM
 * @description
 * @time 2020/06/09 6:21 PM
 **/
@AllArgsConstructor
public class TruthJwtTokenEnhancer implements TokenEnhancer {

   private final JwtAccessTokenConverter jwtAccessTokenConverter;

   @Override
   public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

      LuckUserDetails principal = (LuckUserDetails) authentication.getUserAuthentication().getPrincipal();

      // token参数增强
      Map<String, Object> info = new HashMap<>(16);
      info.put(TokenConstant.CLIENT_ID, TokenUtil.getClientIdFromHeader());
      info.put(TokenConstant.USER_ID, principal.getUserId()==null?"":principal.getUserId());
      info.put(TokenConstant.USER_NAME, principal.getUsername());
      info.put("phone", principal.getPhone());
      info.put(TokenConstant.LICENSE, TokenConstant.LICENSE_NAME);
      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

      //token状态设置
      // if (jwtProperties.getState()) {
      //    final OAuth2AccessToken oAuth2AccessToken = jwtAccessTokenConverter.enhance(accessToken, authentication);
      //    String tokenValue = oAuth2AccessToken.getValue();
      //    String tenantId = principal.getTenantId();
      //    String userId = Func.toStr(principal.getUserId());
      //    JwtUtil.addAccessToken(tenantId, userId, tokenValue, oAuth2AccessToken.getExpiresIn());
      // }

      return accessToken;

   }

}
