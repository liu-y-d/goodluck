package com.luck.constant;

/**
 * 授权校验常量
 *
 * @author QM.JM
 * @description
 * @time 2020/06/09 6:15 PM
 **/
public interface AuthConstant {

   /**
    * 默认密码加密规则，com.qm.truth.admin.auth.support.TruthPasswordEncoder
    */
   String ENCRYPT = "{truth}";

   /**
    * BCrypt密码加密规则，org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
    */
   String BCRYPT_ENCRYPT = "{bcrypt}";

   /**
    * truth_client表字段
    */
   String CLIENT_FIELDS =
      "ClientId AS client_id " +
         ", CONCAT('{noop}', ClientSecret) AS client_secret " +
         ", ResourceIds AS resource_ids " +
         ", Scope AS scope " +
         ", AuthorizedGrantTypes AS authorized_grant_types " +
         ", WebServerRedirectURI AS web_server_redirect_uri " +
         ", Authorities AS authorities " +
         ", AccessTokenValidity AS access_token_validity " +
         ", RefreshTokenValidity AS refresh_token_validity " +
         ", AdditionalInformation AS additional_information " +
         ", AutoApprove AS autoapprove ";

   /**
    * truth_client查询语句
    */
   String BASE_STATEMENT = "SELECT " + CLIENT_FIELDS + " FROM crm_client ";

   /**
    * truth_client查询排序
    */
   String DEFAULT_FIND_STATEMENT = BASE_STATEMENT + " ORDER BY ClientId ";

   /**
    * 查询client_id
    */
   String DEFAULT_SELECT_STATEMENT = BASE_STATEMENT + " WHERE ClientId = ? ";

   /**
    * 微信开放平台-TRUTH产品登录
    */
   String WECHAT_OPEN = "wechat_open";

   /**
    * 微信开放平台-GROWTH产品登录
    */
   String WECHAT_GROWTH = "wechat_growth";

   /**
    * 微信开放平台，白色样式
    */
   String WECHAT_STYLE_WRITE = "&style=white";

}
