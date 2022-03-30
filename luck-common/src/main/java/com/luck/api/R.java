package com.luck.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author QM.JM
 * @description 统一API响应结果封装
 * @time 2020/3/30 15:49
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
public class R<T> implements Serializable {

   private static final long serialVersionUID = -6770890809344015349L;

   private int code;

   private boolean success;

   private T data;

   private String msg;

   private R(IResultCode resultCode) {
      this(resultCode, null, resultCode.getMessage());
   }

   private R(IResultCode resultCode, String msg) {
      this(resultCode, null, msg);
   }

   private R(IResultCode resultCode, T data) {
      this(resultCode, data, resultCode.getMessage());
   }

   private R(IResultCode resultCode, T data, String msg) {
      this(resultCode.getCode(), data, msg);
   }

   private R(int code, T data, String msg) {
      this.code = code;
      this.data = data;
      this.msg = msg;
      this.success = ResultCode.SUCCESS.code == code;
   }

   /**
    * 判断返回是否为成功
    *
    * @param result Result
    * @return 是否成功
    */
   public static boolean isSuccess(@Nullable R<?> result) {
      return Optional.ofNullable(result)
         .map(x -> ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.code, x.code))
         .orElse(Boolean.FALSE);
   }

   /**
    * 判断返回是否为成功
    *
    * @param result Result
    * @return 是否成功
    */
   public static boolean isNotSuccess(@Nullable R<?> result) {
      return !R.isSuccess(result);
   }

   /**
    * 返回R
    *
    * @param data 数据
    * @param <T>  T 泛型标记
    * @return R
    */
   public static <T> R<T> data(T data) {
      return data(data, "操作成功");
   }

   /**
    * 返回R
    *
    * @param data 数据
    * @param msg  消息
    * @param <T>  T 泛型标记
    * @return R
    */
   public static <T> R<T> data(T data, String msg) {
      return data(HttpServletResponse.SC_OK, data, msg);
   }

   /**
    * 返回R
    *
    * @param code 状态码
    * @param data 数据
    * @param msg  消息
    * @param <T>  T 泛型标记
    * @return R
    */
   public static <T> R<T> data(int code, T data, String msg) {
      return new R<>(code, data, data == null ? "暂无承载数据" : msg);
   }

   /**
    * 返回R
    *
    * @param msg 消息
    * @param <T> T 泛型标记
    * @return R
    */
   public static <T> R<T> success(String msg) {
      return new R<>(ResultCode.SUCCESS, msg);
   }

   /**
    * 返回R
    *
    * @param resultCode 业务代码
    * @param <T>        T 泛型标记
    * @return R
    */
   public static <T> R<T> success(IResultCode resultCode) {
      return new R<>(resultCode);
   }

   /**
    * 返回R
    *
    * @param resultCode 业务代码
    * @param msg        消息
    * @param <T>        T 泛型标记
    * @return R
    */
   public static <T> R<T> success(IResultCode resultCode, String msg) {
      return new R<>(resultCode, msg);
   }

   /**
    * 返回R
    *
    * @param msg 消息
    * @param <T> T 泛型标记
    * @return R
    */
   public static <T> R<T> fail(String msg) {
      return new R<>(ResultCode.FAILURE, msg);
   }

   /**
    * 返回R
    *
    * @param code 状态码
    * @param msg  消息
    * @param <T>  T 泛型标记
    * @return R
    */
   public static <T> R<T> fail(int code, String msg) {
      return new R<>(code, null, msg);
   }

   /**
    * 返回R
    *
    * @param resultCode 业务代码
    * @param <T>        T 泛型标记
    * @return R
    */
   public static <T> R<T> fail(IResultCode resultCode) {
      return new R<>(resultCode);
   }

   /**
    * 返回R
    *
    * @param resultCode 业务代码
    * @param msg        消息
    * @param <T>        T 泛型标记
    * @return R
    */
   public static <T> R<T> fail(IResultCode resultCode, String msg) {
      return new R<>(resultCode, msg);
   }

   /**
    * 返回R
    *
    * @param flag 成功状态
    * @return R
    */
   public static <T> R<T> status(boolean flag) {
      return flag ? success("操作成功") : fail("操作失败");
   }

}
