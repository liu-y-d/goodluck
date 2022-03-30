package com.luck.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义密码工厂
 *
 * @author QM.JM
 * @description
 * @time 2020/06/09 8:52 PM
 **/
public class LuckPasswordEncoderFactories {

   /**
    * Creates a {@link DelegatingPasswordEncoder} with default mappings. Additional
    * mappings may be added and the encoding will be updated to conform with best
    * practices. However, due to the nature of {@link DelegatingPasswordEncoder} the
    * updates should not impact users. The mappings current are:
    *
    * <ul>
    * <li>truth - {@link TruthPasswordEncoder} (sha1(md5("password")))</li>
    * <li>bcrypt - {@link BCryptPasswordEncoder} (Also used for encoding)</li>
    * <li>noop - {@link TruthNoOpPasswordEncoder}</li>
    * <li>pbkdf2 - {@link Pbkdf2PasswordEncoder}</li>
    * <li>scrypt - {@link SCryptPasswordEncoder}</li>
    * </ul>
    *
    * @return the {@link PasswordEncoder} to use
    */
   public static PasswordEncoder createDelegatingPasswordEncoder() {

      String encodingId = "noop";
      Map<String, PasswordEncoder> encoders = new HashMap<>(16);

      encoders.put(encodingId, TruthNoOpPasswordEncoder.getInstance());

      return new DelegatingPasswordEncoder(encodingId, encoders);

   }

   private LuckPasswordEncoderFactories() {
   }

}
