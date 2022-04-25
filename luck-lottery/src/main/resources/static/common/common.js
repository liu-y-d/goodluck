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
/**
 * 服务器本地ip
 * @type {string}
 */
var localIp = "192.168.0.101";
$(function () {
    // 判断是否登录
    if (window.location.href.indexOf("/luck-lottery/web/index")<=0) {

        let luckAuth = $.cookie('Luck-Auth');
        if (!luckAuth) {
            window.location.href="/luck-lottery/web/index";
        }
    }
    // 设置用户名
    let currentUserJSON = $.cookie('Current-User');
    if (currentUserJSON) {
        let currentUser = JSON.parse(currentUserJSON);
        $("#userName").text(currentUser.user_name);
    }
})
