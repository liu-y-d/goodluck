$(function () {


})

/**
 * 登录
 */
function login() {
    let account = $("#account").val();
    let password = $("#password").val();
    if ((!account) || (!password)) {
        alert("Account and Password is required！");
    }
    let loginParams = {
        "username":account,
        "password":password,
        "grant_type":"captcha",
        "scope": "all"
    }
    //
    $.post("localhost:88/luck-auth/oauth/token",loginParams,function(data,status){
        alert("Data: " + data + "nStatus: " + status);
    });
}