package com.luck.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import org.springframework.http.MediaType;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 默认 Spring Security OAuth2 获取token接口(/oauth/token) 请求为POST类型，但参数却是以GET方式在URL中传递。
 * 该过滤器将body json的参数转换成key value的Map形式
 *
 * @author QM.JM
 * @description
 * @time 2020/12/02 10:11 PM
 **/
@Component
public class JsonToUrlEncodedAuthenticationFilter extends OncePerRequestFilter {

   private static final String GET_TOKEN_ENDPOINT = "/oauth/token";

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      if (isTokenEndPoint(request)) {
         byte[] json = ByteStreams.toByteArray(request.getInputStream());
         Map<String, String> jsonMap = new ObjectMapper().readValue(json, Map.class);
         Map<String, String[]> parameters = jsonMap.entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> new String[]{e.getValue()})
         );
         HttpServletRequest requestWrapper = new RequestWrapper(request, parameters);
         filterChain.doFilter(requestWrapper, response);
      } else {
         filterChain.doFilter(request, response);
      }
   }

   /**
    * 判断当前请求是否是默认的TokenEndPoint，且ContentType为`application/json`
    *
    * @param request
    */
   private boolean isTokenEndPoint(HttpServletRequest request) {
      String servletPath = request.getServletPath().toLowerCase();
      String contentType = request.getContentType();
      if (!StringUtils.isEmpty(contentType)) {
         contentType = StringUtils.trimAllWhitespace(contentType.toLowerCase());
      }
      final String jsonContentType = MediaType.APPLICATION_JSON_UTF8_VALUE.toLowerCase();
      if (Objects.equals(servletPath, GET_TOKEN_ENDPOINT) && PatternMatchUtils.simpleMatch(contentType + "*", jsonContentType)) {
         return true;
      }
      return false;
   }

   private class RequestWrapper extends HttpServletRequestWrapper {

      private final Map<String, String[]> params;

      RequestWrapper(HttpServletRequest request, Map<String, String[]> params) {
         super(request);
         this.params = params;
      }

      @Override
      public String getParameter(String name) {
         if (this.params.containsKey(name)) {
            return this.params.get(name)[0];
         }
         return "";
      }

      @Override
      public Map<String, String[]> getParameterMap() {
         return this.params;
      }

      @Override
      public Enumeration<String> getParameterNames() {
         return new Enumerator<>(params.keySet());
      }

      @Override
      public String[] getParameterValues(String name) {
         return params.get(name);
      }

   }

}
