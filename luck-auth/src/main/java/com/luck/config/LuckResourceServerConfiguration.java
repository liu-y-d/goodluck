package com.luck.config;

import com.luck.filter.JsonToUrlEncodedAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 自定义登录成功配置
 *
 * @author QM.JM
 * @description
 * @time 2020/06/09 6:19 PM
 **/
@Configuration
@AllArgsConstructor
@EnableResourceServer
public class LuckResourceServerConfiguration extends ResourceServerConfigurerAdapter {

   /**
    * 将body json的参数转换成key value的Map形式
    */
   private final JsonToUrlEncodedAuthenticationFilter jsonFilter;

   /**
    * 自定义登录成功处理器
    */
   private final AuthenticationSuccessHandler appLoginInSuccessHandler;

   @Override
   @SneakyThrows
   public void configure(HttpSecurity http) {
      http.headers().frameOptions().disable();
      http.addFilterAfter(jsonFilter, BasicAuthenticationFilter.class)
         .formLogin()
         .successHandler(appLoginInSuccessHandler)
         .and()
         .authorizeRequests()
         .antMatchers(
            "/actuator/**",
            "/oauth/captcha",
            "/oauth/logout",
            "/oauth/clear-cache",
            "/oauth/render/**",
            "/oauth/callback/**",
            "/oauth/revoke/**",
            "/oauth/refresh/**",
            "/token/**",
            "/truth-admin-crm/apply/**",
            "/mobile/**",
            "/v2/api-docs",
            "/v2/api-docs-ext").permitAll()
         .anyRequest().authenticated().and()
         .csrf().disable();
   }

}
