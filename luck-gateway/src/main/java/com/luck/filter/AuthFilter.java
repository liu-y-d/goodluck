package com.luck.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luck.provider.AuthProvider;
import com.luck.provider.RequestProvider;
import com.luck.provider.ResponseProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 鉴权认证
 *
 * @author QM.JM
 * @description
 * @time 2020/05/08 9:35 PM
 **/
@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {


   private final ObjectMapper objectMapper;


   private final AntPathMatcher antPathMatcher = new AntPathMatcher();

   @Override
   public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

      //校验 Token 放行
      String originalRequestUrl = RequestProvider.getOriginalRequestUrl(exchange);
      String path = exchange.getRequest().getURI().getPath();
      if (isSkip(path) || isSkip(originalRequestUrl)) {
         return chain.filter(exchange);
      }

      //校验 Token 合法性
      ServerHttpResponse resp = exchange.getResponse();
      String headerToken = exchange.getRequest().getHeaders().getFirst(AuthProvider.AUTH_KEY);
      String paramToken = exchange.getRequest().getQueryParams().getFirst(AuthProvider.AUTH_KEY);
      if (StringUtils.isAllBlank(headerToken, paramToken)) {
         return unAuth(resp, "缺失令牌,鉴权失败");
      }
      String auth = StringUtils.isBlank(headerToken) ? paramToken : headerToken;
      String token = getToken(auth);
      Claims claims = parseJWT(token);
      if (token == null || claims == null) {
         return unAuth(resp, "请求未授权");
      }

      //判断 Token 状态
      // if (jwtProperties.getState()) {
      //    String tenantId = String.valueOf(claims.get(TokenConstant.TENANT_ID));
      //    String userId = String.valueOf(claims.get(TokenConstant.USER_ID));
      //    String accessToken = JwtUtil.getAccessToken(tenantId, userId, token);
      //    if (!token.equalsIgnoreCase(accessToken)) {
      //       return unAuth(resp, "令牌已失效");
      //    }
      // }

      return chain.filter(exchange);

   }
   private String getToken(String auth) {
      if ((auth != null) && (auth.length() > 7)) {
         String headStr = auth.substring(0, 6).toLowerCase();
         if (headStr.compareTo("bearer") == 0) {
            auth = auth.substring(7);
         }
         return auth;
      }
      return null;
   }

   private Claims parseJWT(String jsonWebToken) {
      try {
         return Jwts.parser()
                 .setSigningKey(Base64.getDecoder().decode(getBase64Security()))
                 .parseClaimsJws(jsonWebToken).getBody();
      } catch (Exception ex) {
         return null;
      }
   }
   /**
    * 签名加密
    */
   private String getBase64Security() {
      return Base64.getEncoder().encodeToString("luck".getBytes(StandardCharsets.UTF_8));
   }
   private boolean isSkip(String path) {
      // return AuthProvider.getDefaultSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path))
      //    || authProperties.getSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
         return AuthProvider.getDefaultSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
   }

   private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
      resp.setStatusCode(HttpStatus.UNAUTHORIZED);
      resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
      String result = "";
      try {
         result = objectMapper.writeValueAsString(ResponseProvider.unAuth(msg));
      } catch (JsonProcessingException e) {
         log.error(e.getMessage(), e);
      }
      DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
      return resp.writeWith(Flux.just(buffer));
   }

   @Override
   public int getOrder() {
      return -100;
   }

}
