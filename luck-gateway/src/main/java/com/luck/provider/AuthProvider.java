package com.luck.provider;



import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 *
 * @author QM.JM
 * @description
 * @time 2020/05/08 5:19 PM
 **/
public class AuthProvider {

   public static final String AUTH_KEY = "Luck-Auth";

   private static final List<String> DEFAULT_SKIP_URL = new ArrayList<>();

   static {
      DEFAULT_SKIP_URL.add("/example");
      DEFAULT_SKIP_URL.add("/luck-auth/oauth/token/**");
      DEFAULT_SKIP_URL.add("/renren-fast/**");
      DEFAULT_SKIP_URL.add("/oauth/token/**");
      DEFAULT_SKIP_URL.add("/oauth/captcha/**");
      DEFAULT_SKIP_URL.add("/oauth/clear-cache/**");
      DEFAULT_SKIP_URL.add("/oauth/user-info");
      DEFAULT_SKIP_URL.add("/oauth/render/**");
      DEFAULT_SKIP_URL.add("/oauth/callback/**");
      DEFAULT_SKIP_URL.add("/oauth/revoke/**");
      DEFAULT_SKIP_URL.add("/oauth/refresh/**");
      DEFAULT_SKIP_URL.add("/token/**");
      DEFAULT_SKIP_URL.add("/actuator/**");
      DEFAULT_SKIP_URL.add("/v2/api-docs/**");
      DEFAULT_SKIP_URL.add("/auth/**");
      DEFAULT_SKIP_URL.add("/log/**");
      DEFAULT_SKIP_URL.add("/menu/routes");
      DEFAULT_SKIP_URL.add("/menu/auth-routes");
      DEFAULT_SKIP_URL.add("/tenant/info");
      DEFAULT_SKIP_URL.add("/process/resource-view");
      DEFAULT_SKIP_URL.add("/process/diagram-view");
      DEFAULT_SKIP_URL.add("/manager/check-upload");
      DEFAULT_SKIP_URL.add("/error/**");
      DEFAULT_SKIP_URL.add("/assets/**");
      DEFAULT_SKIP_URL.add("/truth-admin-crm/apply/**");
   }

   /**
    * 默认无需鉴权的API
    */
   public static List<String> getDefaultSkipUrl() {
      return DEFAULT_SKIP_URL;
   }

}
