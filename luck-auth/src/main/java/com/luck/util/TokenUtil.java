package com.luck.util;

import com.luck.constant.TokenConstant;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;

/**
 * @author QM.JM
 * @description
 * @time 2020/06/09 3:00 PM
 **/
public class TokenUtil {

   public final static String AVATAR = TokenConstant.AVATAR;
   public final static String USER_NAME = TokenConstant.USER_NAME;
   public final static String NICK_NAME = TokenConstant.NICK_NAME;
   public final static String USER_ID = TokenConstant.USER_ID;
   public final static String DEPT_ID = TokenConstant.DEPT_ID;
   public final static String POST_ID = TokenConstant.POST_ID;
   public final static String ROLE_ID = TokenConstant.ROLE_ID;
   public final static String ROLE_NAME = TokenConstant.ROLE_NAME;
   public final static String TENANT_ID = TokenConstant.TENANT_ID;
   public final static String TENANT_NAME = TokenConstant.TENANT_NAME;
   public final static String OAUTH_ID = TokenConstant.OAUTH_ID;
   public final static String CLIENT_ID = TokenConstant.CLIENT_ID;
   public final static String LICENSE = TokenConstant.LICENSE;
   public final static String LICENSE_NAME = TokenConstant.LICENSE_NAME;

   public final static String CAPTCHA_HEADER_KEY = "Captcha-Key";
   public final static String CAPTCHA_HEADER_CODE = "Captcha-Code";
   public final static String CAPTCHA_NOT_CORRECT = "Captcha expired, please try again.";
   public final static String TENANT_HEADER_KEY = "Tenant-Id";
   public final static String TENANT_PARAM_KEY = "tenant_id";
   public final static String DEFAULT_TENANT_ID = "000000";
   public final static String TENANT_NOT_FOUND = "租户ID未找到";
   public final static String USER_TYPE_HEADER_KEY = "User-Type";
   public final static String DEFAULT_USER_TYPE = "web";
   public final static String USER_NOT_FOUND = "用户名不存在或密码错误";
   public final static String SOCIAL_UNBOUND = "尚未绑定QuestMobile账号，请先绑定";
   public final static String USER_HAS_NO_ROLE = "未获得用户的角色信息";
   public final static String USER_HAS_NO_TENANT = "未获得用户的租户信息";
   public final static String USER_HAS_NO_TENANT_PERMISSION = "租户授权已过期,请联系管理员";
   public final static String EN_LOGIN_UNAUTHORIZED = "未获得英文登录权限";
   public final static String HEADER_KEY = "Authorization";
   public final static String HEADER_PREFIX = "Basic ";
   public final static String DEFAULT_AVATAR = "";




   /**
    * 解码
    */
   @SneakyThrows
   public static String[] extractAndDecodeHeader() {

      String header = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getHeader(TokenUtil.HEADER_KEY);

      if (header == null || !header.startsWith(TokenUtil.HEADER_PREFIX)) {
         throw new UnapprovedClientAuthenticationException("No authorization in Request Header");
      }

      byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8.name());

      byte[] decoded;
      try {
         decoded = Base64.getDecoder().decode(base64Token);
      } catch (IllegalArgumentException var7) {
         throw new BadCredentialsException("Failed to decode basic authentication token");
      }

      String token = new String(decoded, StandardCharsets.UTF_8.name());
      int index = token.indexOf(":");
      if (index == -1) {
         throw new BadCredentialsException("Invalid basic authentication token");
      } else {
         return new String[]{token.substring(0, index), token.substring(index + 1)};
      }

   }

   /**
    * 获取请求头中的客户端id
    */
   public static String getClientIdFromHeader() {
      String[] tokens = extractAndDecodeHeader();
      return tokens[0];
   }

   /**
    * 获取token过期时间(次日凌晨3点)
    *
    * @return expire
    */
   public static int getTokenValiditySecond() {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_YEAR, 1);
      cal.set(Calendar.HOUR_OF_DAY, 3);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.MILLISECOND, 0);
      return (int) (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
   }

   /**
    * 获取refreshToken过期时间
    *
    * @return expire
    */
   public static int getRefreshTokenValiditySeconds() {
      return 60 * 60 * 24 * 15;
   }

}
