package com.luck.config;

import lombok.SneakyThrows;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 客户端信息
 *
 * @author QM.JM
 * @description
 * @time 2020/06/09 6:12 PM
 **/
@Component
public class LuckClientDetailsServiceImpl extends JdbcClientDetailsService {

   public LuckClientDetailsServiceImpl(DataSource dataSource) {
      super(dataSource);
   }

   /**
    * 缓存客户端信息
    *
    * @param clientId 客户端id
    */
   @Override
   @SneakyThrows
   public ClientDetails loadClientByClientId(String clientId) {
      return super.loadClientByClientId(clientId);
   }

}
