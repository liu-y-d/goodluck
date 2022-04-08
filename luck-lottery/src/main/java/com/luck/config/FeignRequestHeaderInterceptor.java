package com.luck.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * <p>
 * https://blog.csdn.net/u014519194/article/details/77160958
 * http://tietang.wang/2016/02/25/hystrix/Hystrix%E5%8F%82%E6%95%B0%E8%AF%A6%E8%A7%A3/
 * https://github.com/Netflix/Hystrix/issues/92#issuecomment-260548068
 * </p>
 *
 * @author QM.JM
 * @description feign 传递Request header
 * @time 2020/4/3 14:25
 **/
public class FeignRequestHeaderInterceptor implements RequestInterceptor {

   @Override
   public void apply(RequestTemplate requestTemplate) {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (attributes != null) {
         HttpServletRequest request = attributes.getRequest();
         //获取浏览器发起的请求头
         Enumeration<String> headerNames = request.getHeaderNames();
         if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
               String name = headerNames.nextElement(); //请求头名称 Authorization
               String value = request.getHeader(name);//请求头数据 "Bearer b1dbb4cf-7de6-41e5-99e2-0e8b7e8fe6ee"
               requestTemplate.header(name, value);
            }
         }
      }
   }

}
