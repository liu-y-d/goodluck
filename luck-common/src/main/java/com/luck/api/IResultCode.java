package com.luck.api;

import java.io.Serializable;

/**
 * @author QM.JM
 * @description 业务代码接口
 * @time 2020/3/30 15:45
 **/
public interface IResultCode extends Serializable {

   /**
    * 获取消息
    *
    * @return
    */
   String getMessage();

   /**
    * 获取状态码
    *
    * @return
    */
   int getCode();

}
