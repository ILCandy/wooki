/**
 * Created by Administrator on 2017/12/17 0017.
 */

/* Array.prototype */
Array.prototype.contains = function (obj) {  
    var i = this.length;  
    while (i--) {  
        if (this[i] === obj) {  
            return true;  
        }  
    }  
    return false;  
} 

/* url */
var Request = new Object();
function GetRequest() {
    var url = location.search;
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

var common = {
    // ajax
    Ajax: function (url, mydata, requestType, callback, token) {
        $.ajax({
            type: requestType,
            contentType: "application/json",
            dataType : "json",
            url: "" + url,
            data: mydata,
            beforeSend: function(request) {
                request.setRequestHeader("token", token);
            },
            success: function (res) {
                if(res.code == "999"){
                    // alert(token);
                    alert("登录信息失效");
                    top.location.href = "/";
                }
                callback(res);
            },
            error: function (err) {
                console.log(err);
            }
        })
    }
};
window.common = common;

/* browser */
function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}

/* keyNumPress */
var keyNumPress = function () {
    var keyCode = event.keyCode;
    event.returnValue = (keyCode >= 48 && keyCode <= 57);
};
/* keyNumPress */

/* cookie */
function setCookie(name, value, expires) {
    var exp = new Date();
    exp.setTime(exp.getTime() + expires  * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}
/* cookie */

/* rename */
var tempRename;
function setTempRename(txt){
    return tempRename = txt;
}
function setRename(that,renameId,type){
    var inputText = $(that).val();
    if(inputText == ""){
        layer.msg("名字不能为空");
        if($(that).next(".text").length > 0){
            $(that).next(".text").html(tempRename);
        }
        $(that).val(tempRename);
        return false;
    }
    if(tempRename != inputText){
        var token = getCookie("token");
        var mydata = JSON.stringify({
            "id":renameId,
            "name":inputText
        });
        var requestType = "post";
        var url;
        if(type == "area"){
            url = "/group/update";
        }
        if (type == "scene") {
            url = "/scene/update";
        };
        if (type == "device") {
            url = "/device/update";
        };
        if (type == "meeting") {
            url = "/metting/update";
        };
        common.Ajax(url, mydata, requestType, function (res) {
            console.log(res);
            if(res.code == "200"){
                layer.msg(res.msg,{icon:1,time:1000});
            } else {
                layer.msg(res.msg,{icon:2,time:1000});
            }
        },token);
    }
}
function getValue(that,txt){
    $(that).next(".text").html(txt);
}

/* time-taker */
var thisTimer;
var thisTimeVal;
function timeTaker(that){
    if($(".layui-laydate.my-layui-laydate").length > 0){
        $(".layui-laydate.my-layui-laydate").remove();
    }
    thisTimer = $(that);
    var hh = $(that).val().split(":")[0] || "00";
    var mm = $(that).val().split(":")[1] || "00";
    var html = "";
    html += '<div class="layui-laydate my-layui-laydate" style="">';
    html += '<div class="layui-laydate-main laydate-main-list-0 laydate-time-show">';
    html += '<div class="layui-laydate-content">';
    html += '<ul class="layui-laydate-list laydate-time-list">';
    html += '<li><p>时</p>';
    html += '<ol class="layui-hour">';
    for (var i = 0; i < 24; i++){
        if(((i<10?"0":"") + i) == hh){
            html += '<li class="layui-this" onclick="addlayuiThis(this,' + i + ',\'hh\');">' + (i<10?"0":"") + i + '</li>';
        } else {
            html += '<li class="" onclick="addlayuiThis(this,' + i + ',\'hh\');">' + (i<10?"0":"") + i + '</li>';
        }
        
    }
    html += '</ol>';
    html += '</li>';
    html += '<li><p>分</p>';
    html += '<ol class="layui-minute">';
    for (var j = 0; j < 60; j++){
        if(((j<10?"0":"") + j) == mm){
            html += '<li class="layui-this" onclick="addlayuiThis(this,' + j + ',\'mm\');">' + (j<10?"0":"") + j + '</li>';
        } else {
            html += '<li class="" onclick="addlayuiThis(this,' + j + ',\'mm\');">' + (j<10?"0":"") + j + '</li>';
        }
    }
    html += '</ol>';
    html += '</li>';
    html += '</ul>';
    html += '</div>';
    html += '</div>';
    html += '<div class="layui-laydate-footer">';
    html += '<div class="laydate-footer-btns">';
    html += '<span lay-type="clear" class="laydate-btns-clear" onclick="cancelLaydate()">取消</span>';
    html += '<span lay-type="clear" class="laydate-btns-clear" onclick="clearLaydate()">清空</span>';
    html += '<span lay-type="confirm" class="laydate-btns-confirm" onclick="submitLaydate()">确定</span>';
    html += '</div>';
    html += '</div>';
    html += '</div>';
    $(that).after(html);
    return thisTimer;
}
function cancelLaydate(that){
    $(".layui-laydate").remove();
}

function clearLaydate(){
    thisTimer.val("");
    $(".layui-laydate").remove();
}
function addlayuiThis(that,val,type){
    $(that).siblings("li").removeClass("layui-this");
    $(that).addClass("layui-this");
}
function submitLaydate(){
    var hh,dd;
    hh = $(".layui-hour").find("li.layui-this").html();
    mm = $(".layui-minute").find("li.layui-this").html();
    thisTimer.val(hh + ":" + mm);
    $(".layui-laydate").remove();
}

/* isLogin */
var token = getCookie("token");
function isLogin(url){
    var returnUrl = encodeURIComponent(url);
    if(token == null){
        if(url == undefined || url == ""){
            parent.location.href = "/";
        } else {
            parent.location.href = "/?returnUrl=" + returnUrl;
        }
    }
}

function loadSlider(){
    getUsermsg();
    var menuVal = [
            {
                "icon": "list-icon-meeting",
                "url": "../temp/meeting.html",
                "text": "会议室申请"
            },
            {
                "icon": "list-icon-workacBooking",
                "url": "../temp/workac.html",
                "text": "空调申请"
            },
            {
                "icon": "list-icon-workacList",
                "url": "../temp/order.html",
                "text": "我的订单"
            },
            {
                "icon": "list-icon-chargeTable",
                "url": "../temp/chargeTable.html",
                "text": "计费结算"
            }
            
        ];

        /*
            {
                "icon": "list-icon-report",
                "url": "../temp/report.html",
                "text": "报表查询"
            },
            {
                "icon": "list-icon-manage",
                "url": "../temp/manage.html",
                "text": "设备管控"
            },
            {
                "icon": "list-icon-scene",
                "url": "../temp/scene.html",
                "text": "情景模式"
            },
            {
                "icon": "list-icon-linkage",
                "url": "../temp/linkage.html",
                "text": "联动模式"
            },
            {
                "icon": "list-icon-office",
                "url": "../temp/office.html",
                "text": "办公设置"
            },
            {
                "icon": "list-icon-systemLogs",
                "url": "../temp/systemLogs.html",
                "text": "设备日志"
            },
            {
                "icon": "list-icon-notices",
                "url": "../temp/notices.html",
                "text": "新闻公告"
            },
            {
                "icon": "list-icon-workacList",
                "url": "../temp/record.html",
                "text": "申请记录"
            }
        */

        /*
        */

        /* 
            {
                "icon": "list-icon-office",
                "url": "../temp/companyList.html",
                "text": "公司列表"
            },
            {
                "icon": "list-icon-linkage",
                "url": "../temp/bindDevice.html",
                "text": "绑定网关"
            },
            {
                "icon": "list-icon-office",
                "url": "../temp/deviceList.html",
                "text": "中控列表"
            },
            {
                "icon": "list-icon-office",
                "url": "../temp/acList.html",
                "text": "空调列表"
            }
         */

    var html = "";
    html += '<div class="mobile-header">';
    html += '<span class="nav-toggle glyphicon glyphicon-menu-hamburger" onclick="sliderShow();"></span>';
    html += '<img class="nav-logo" src="../assets/img/logo.png" alt="logo">';
    html += '</div>';
    html += '<div class="mobile-slider">';
    html += '<div class="custom-mark" onclick="sliderHide();"></div>';
    html += '<div class="mobile-slider-nav">';
    html += '<a onclick="loadmark()" href="usermsg.html" class="slide-company"><div class="headimg" style="background-image:url(../assets/img/logo.png)"></div><p class="tc white" id="companyName">&nbsp;</p></a>';
    html += '<ul>';
    for(var i = 0; i < menuVal.length; i++){
        html += '<li>';
        html += '<a onclick="loadmark()" href="' + menuVal[i].url + '"><span class="icon ' + menuVal[i].icon + '"></span>' + menuVal[i].text + '</a>';
        html += '</li>';
    }
    html += '</ul>';
    html +='<div class="aside-summary">';
    html +='<div class="text">智能节电</div>';
    html +='<div class="main text-center"><span class="f20 t-green" id="useDays">0</span><span class="unit"> 天</span></div>';
    html +='</div>';
    html += '</div>';
    html += '</div>';
    $("body").prepend(html);
    $(".container").css("marginTop","40px");
}

function getUsermsg(){
    var url = "/company/info";
    var token = getCookie("token");
    var mydata = "";
    var requestType = "get";
    common.Ajax(url, mydata, requestType, function (res) {
        if(res.code == "200"){
            console.log(res);
            var useDays = (Date.parse(new Date()) - res.data.addTime);
            var day = 1000 * 60 * 60 * 24;
            $("#useDays").html(parseInt(useDays / day));
            $("#companyName").html(res.data.name);
            // $(".headimg").css("backgroundImage","url(" + res.data.logoUrl + ")");
        }
    },token);
}

function loadmark(){
    sliderHide();
    layer.load(1, {time: 10*1000});
}

function sliderShow(){
    $(".custom-mark").addClass("active");
    $(".mobile-slider-nav").addClass("active");
    // $("body").css("overflow","hidden");
}
function sliderHide(){
    $(".mobile-slider-nav").removeClass("active");
    $(".custom-mark").removeClass("active");
    // $("body").css("overflow","");
}

function timeAdd(m) {
    return m < 10 ? '0' + m : m
}
function format(timestamp) {
    //shijianchuo是整数，否则要parseInt转换
    var time = new Date(timestamp);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y + '-' + timeAdd(m) + '-' + timeAdd(d) + ' ' + timeAdd(h) + ':' + timeAdd(mm) + ':' + timeAdd(s);
}

if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function() {
        FastClick.attach(document.body);
    }, false);
}