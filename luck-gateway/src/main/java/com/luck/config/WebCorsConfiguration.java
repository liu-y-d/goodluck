package com.luck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author Liuyunda
 * @date 2022/4/13 22:26
 * @email man021436@163.com
 */
@Configuration
public class WebCorsConfiguration {
    @Configuration
    public class CorsConfig {
        @Bean
        public CorsWebFilter corsWebFilter(){
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration corsConfig = new CorsConfiguration();
            // 配置跨域
            corsConfig.addAllowedHeader("*");
            corsConfig.addAllowedMethod("*");
            corsConfig.addAllowedOrigin("*");
            corsConfig.setAllowCredentials(true);
            source.registerCorsConfiguration("/**", corsConfig);
            return new CorsWebFilter(source);
        }
    }
}
