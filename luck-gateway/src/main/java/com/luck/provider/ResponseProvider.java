package com.luck.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求响应返回
 *
 * @author QM.JM
 * @description
 * @time 2020/05/08 5:17 PM
 **/
public class ResponseProvider {

   /**
    * 成功
    *
    * @param message 信息
    * @return
    */
   public static Map<String, Object> success(String message) {
      return response(200, message);
   }

   /**
    * 失败
    *
    * @param message 信息
    * @return
    */
   public static Map<String, Object> fail(String message) {
      return response(400, message);
   }

   /**
    * 未授权
    *
    * @param message 信息
    * @return
    */
   public static Map<String, Object> unAuth(String message) {
      return response(401, message);
   }

   /**
    * 请求过于频繁
    *
    * @param message 信息
    * @return
    */
   public static Map<String, Object> tooManyRequests(String message) {
      return response(429, message);
   }

   /**
    * 服务器异常
    *
    * @param message 信息
    * @return
    */
   public static Map<String, Object> error(String message) {
      return response(500, message);
   }

   /**
    * 构建返回的JSON数据格式
    *
    * @param status  状态码
    * @param message 信息
    * @return
    */
   public static Map<String, Object> response(int status, String message) {
      Map<String, Object> map = new HashMap<>(16);
      map.put("code", status);
      map.put("msg", message);
      map.put("data", null);
      return map;
   }

}
