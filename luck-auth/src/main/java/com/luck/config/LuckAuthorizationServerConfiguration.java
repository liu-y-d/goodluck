package com.luck.config;


import com.luck.Granter.CaptchaTokenGranter;
import com.luck.constant.AuthConstant;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 认证服务器配置
 *
 * @author QM.JM
 * @description
 * @time 2020/06/09 2:29 PM
 **/
@Order
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class LuckAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

   private final DataSource dataSource;
   private final AuthenticationManager authenticationManager;

   private final UserDetailsService userDetailsService;

   private final TokenStore tokenStore;

   private final TokenEnhancer jwtTokenEnhancer;

   private final JwtAccessTokenConverter jwtAccessTokenConverter;



   @Override
   public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

      // 获取自定义tokenGranter
      List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
      granters.add(new CaptchaTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
      TokenGranter tokenGranter  = new CompositeTokenGranter(granters);
      // 配置端点
      endpoints.tokenStore(tokenStore)
         .authenticationManager(authenticationManager)
         .userDetailsService(userDetailsService)
         .tokenGranter(tokenGranter);

      // 扩展token返回结果
      if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
         TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
         List<TokenEnhancer> enhancerList = new ArrayList<>();
         enhancerList.add(jwtTokenEnhancer);
         enhancerList.add(jwtAccessTokenConverter);
         tokenEnhancerChain.setTokenEnhancers(enhancerList);
         // jwt增强
         endpoints.tokenEnhancer(tokenEnhancerChain).accessTokenConverter(jwtAccessTokenConverter);
      }

   }

   /**
    * 配置客户端信息
    */
   @Override
   @SneakyThrows
   public void configure(ClientDetailsServiceConfigurer clients) {
      LuckClientDetailsServiceImpl clientDetailsService = new LuckClientDetailsServiceImpl(dataSource);
      clientDetailsService.setSelectClientDetailsSql(AuthConstant.DEFAULT_SELECT_STATEMENT);
      clientDetailsService.setFindClientDetailsSql(AuthConstant.DEFAULT_FIND_STATEMENT);
      clients.withClientDetails(clientDetailsService);
   }

   @Override
   public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
      oauthServer
         .allowFormAuthenticationForClients()
         .tokenKeyAccess("permitAll()")
         .checkTokenAccess("isAuthenticated()");
   }

}
