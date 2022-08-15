$(function () {
    // 隐藏非控制者的抽奖按钮
    debugger
    let currentUserJSON = $.cookie('Current-User');
    if (currentUserJSON) {
        let currentUser = JSON.parse(currentUserJSON);
        let currentUserId = currentUser.user_id;
        let controllerId = $(".buy-course").find(".btn").eq(0).attr('data-flag');
        if (controllerId && controllerId != currentUserId) {
            $(".buy-course").find(".btn").eq(0).hide();
        }
    }
})
/**
 * 抽奖
 * @param activityId 活动id
 * @param times 次数
 * @param flag 抽奖地址标记 0 lottery/get 1 lotteryShuffle/get
 */
function lottery(activityId,times,flag) {
    debugger
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    var joinTime = new Date().Format("yyyy-MM-dd HH:mm:ss");
    let lotteryParams = {
        "activityId":activityId,
        "getTimes":times,
        "timestamp":joinTime
    }
    $("#loading").fadeIn(500);
    commonHeaders["Luck-Auth"] = $.cookie("Luck-Auth");
    let url;
    if (flag == 0) {
        url= "http://"+localIp+":88/luck-lottery/api/lottery/get";
    }else {
        url= "http://"+localIp+":88/luck-lottery/api/lotteryShuffle/get";
    }
    $.ajax({
        url: url,
        type:"post",
        dataType: "json",
        contentType:"application/json",
        headers:commonHeaders,
        crossDomain: true,
        data: JSON.stringify(lotteryParams),
        success:function (data,state) {
            getResult(activityId,times,joinTime,flag)
        },
        error:function (state) {
            $("#loading").fadeOut(100);
            alert("请稍后重试！")
        }
    })
}
function getResult(activityId,times,joinTime,flag,isClick) {
    $("#loading").fadeIn(500);
    let url;
    if (flag == 0) {
        url= "http://"+localIp+":88/luck-lottery/api/lottery/getResult";
    }else {
        url= "http://"+localIp+":88/luck-lottery/api/lotteryShuffle/getResult";
    }
    let lotteryParams = {
        "activityId":activityId,
        "getTimes":times,
        "timestamp":joinTime
    }
    commonHeaders["Luck-Auth"] = $.cookie("Luck-Auth");
    $.ajax({
        url:url,
        type:"post",
        dataType: "json",
        contentType:"application/json",
        headers:commonHeaders,
        crossDomain: true,
        data: JSON.stringify(lotteryParams),
        success:function (data,state) {
            $("#loading").fadeOut(100);
            if (flag == 0) {
                var winPrizesDom = "<ul>";
                for (let i = 0; i < data["data"]["prizes"].length; i++) {
                    winPrizesDom += "<li>"+(i+1)+": "+data["data"]["prizes"][i].prizeName+"*"+data["data"]["prizes"][i].prizeNumber+"</li>";
                }
                winPrizesDom += "</ul>";
            }else {
                var winPrizesDom = "<ul>";
                for (let i = 0; i < data["data"].length; i++) {
                    winPrizesDom += "<li>"+(i+1)+": "+data["data"][i]["userName"]+" -> "+data["data"][i]["prizes"][0].prizeName+"*"+data["data"][i]["prizes"][0].prizeNumber+"</li>";
                }
                winPrizesDom += "</ul>";
            }
            $("#winPrizes").html(winPrizesDom);
            $('#exampleModalCenter').modal('show');
        },
        error:function (state) {
            if (isClick != 1) {
                setTimeout(function () {
                    getResult(activityId,times,joinTime)
                },1000)
            }else {
                $("#loading").fadeOut(100);
                var noHave = '<h2 style="color: red">暂无抽奖结果！</h2>'
                $("#winPrizes").html(noHave);
                $('#exampleModalCenter').modal('show');
            }
        }
    })
}