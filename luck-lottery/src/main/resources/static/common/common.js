/**
 * Basic 认证密钥
 * @type {string}
 */
var basicSecret = "Basic bHVjazpsdWNrX3NlY3JldA==";
var commonHeaders = {
    "Authorization":basicSecret,
    "Luck-Auth":"",
    "Access-Control-Allow-Origin":"*",
    "Access-Control-Allow-Methods":"POST,GET",
    "Access-Control-Max-Age":360,
    "Access-Control-Allow-Headers":"x-requested-with,content-type",
    "Content-Type":"application/json;charset=utf-8"
}
