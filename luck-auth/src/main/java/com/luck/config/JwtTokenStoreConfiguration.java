package com.luck.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author QM.JM
 * @description
 * @time 2020/06/09 6:20 PM
 **/
@Configuration
@ConditionalOnProperty(prefix = "truth.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
public class JwtTokenStoreConfiguration {

   /**
    * 使用jwtTokenStore存储token
    */
   @Bean
   public TokenStore jwtTokenStore() {
      return new JwtTokenStore(jwtAccessTokenConverter());
   }

   /**
    * 用于生成jwt
    */
   @Bean
   public JwtAccessTokenConverter jwtAccessTokenConverter() {
      JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
      accessTokenConverter.setSigningKey("luck");
      return accessTokenConverter;
   }

   /**
    * 用于扩展jwt
    */
   @Bean
   @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
   public TokenEnhancer jwtTokenEnhancer(JwtAccessTokenConverter jwtAccessTokenConverter) {
      return new TruthJwtTokenEnhancer(jwtAccessTokenConverter);
   }

}
