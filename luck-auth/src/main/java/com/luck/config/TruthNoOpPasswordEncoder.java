package com.luck.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 无密码加密
 *
 * @author QM.JM
 * @description
 * @time 2020/06/09 8:54 PM
 **/
public class TruthNoOpPasswordEncoder implements PasswordEncoder {

   private static final PasswordEncoder INSTANCE = new TruthNoOpPasswordEncoder();

   private TruthNoOpPasswordEncoder() {
   }

   @Override
   public String encode(CharSequence rawPassword) {
      return rawPassword.toString();
   }

   @Override
   public boolean matches(CharSequence rawPassword, String encodedPassword) {
      return rawPassword.toString().equals(encodedPassword);
   }

   /**
    * Get the singleton {@link TruthNoOpPasswordEncoder}.
    */
   public static PasswordEncoder getInstance() {
      return INSTANCE;
   }

}
