$(function () {
    let LuckAuth = $.cookie("Luck-Auth");
    if (LuckAuth) {
        location.href="http://"+localIp+":88/luck-lottery/web/activity";
    }

})

/**
 * 登录
 */
function login() {
    let account = $("#account").val();
    let password = $("#password").val();
    if ((!account) || (!password)) {
        alert("Account and Password is required！");
        return;
    }
    let loginParams = {
        "username":account,
        "password":password,
        "grant_type":"captcha",
        "scope": "all"
    }
    debugger
    $.ajax({
        url:"http://"+localIp+":88/luck-auth/oauth/token",
        type:"post",
        dataType: "json",
        contentType:"application/json",
        headers:commonHeaders,
        crossDomain: true,
        data: JSON.stringify(loginParams),
        success:function (data,state) {
            $.cookie("Luck-Auth",data.access_token,{ path: '/', expires: new Date().getTime() + (24 * 60 * 60 * 1000) })
            debugger
            $.cookie("Current-User",JSON.stringify(data),{ path: '/', expires: new Date().getTime() + (24 * 60 * 60 * 1000) })
            commonHeaders["Luck-Auth"] = data.access_token;
            //去活动首页
            location.href="http://"+localIp+":88/luck-lottery/web/activity";
        },
        error:function (state) {
            alert(state.responseText+"帐号或密码错误1111！");
            alert(state.status+"帐号或密码错误1111！");
            alert(state.statusText+"帐号或密码错误1111！");
            alert(state.responseText+"帐号或密码错误1111！");
        }
    })
}