
/* isLogin */
isLogin();
    
/* slider */
if(!IsPC()){
    loadSlider();
}
$(".load-mark").remove();

if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function() {
        FastClick.attach(document.body);
    }, false);
}

/* slideArea */
function slideArea(type){
    if(type == "0"){
        $(".area-container").addClass("active");
    } else {
        $(".area-container").removeClass("active");
    }
}

/* toggleArea */
function toggleArea(that,groupId){
    $(that).closest(".area-container").toggleClass("active");
    if($(that).attr("data-load") == "load"){
        var obj = $(that).closest(".area-container").find(".area-article");
        getDevice(obj,groupId);
        $(that).removeAttr("data-load")
    }
}

var onlineNum = {};
function getOnlineNum(){
    var token = getCookie("token");
    $.ajax({
        async: false,
        type: "get",
        contentType: "application/json",
        dataType : "json",
        url: "/group/onlineCount",
        data: "",
        beforeSend: function(request) {
            request.setRequestHeader("token", token);
        },
        success: function (res) {
            if(res.code == "200"){
                onlineNum = res.data;
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
}

/* getGroup */
var areaObj = {};
function getGroup(){
    getOnlineNum();
    var url = "/group/list";
    var token = getCookie("token");
    var mydata = "";
    common.Ajax(url, mydata,"get", function (res) {
        console.log(res);
        var html = "";
        if(res.code == "200" && res.data != null){
            for(var i = 0; i < res.data.length; i++){
                html += '<div class="col-md-6 col-sm-12">';
                html += '<div class="area-container">';
                html += '<div class="area-header">';
                html += '<div class="area-names pull-left" data-id="' + res.data[i].id + '"><input type="text" oninput="getValue(this,this.value)" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + res.data[i].id + '\',\'area\')" class="rename-text w-full" value="' + res.data[i].name + '"/><span class="text" style="visibility:hidden;"> ' + res.data[i].name + '</span><i class="layui-icon">&#xe642;</i> <i class="layui-icon delete" title="删除该分区" onclick="delGroup(this,\'' + res.data[i].id + '\')">&#xe640;</i></div>';
                html += '<div class="area-plans pull-right">';
                // html += '<span class="clock glyphicon glyphicon-time f20" onclick="setClock(this);"></span>';
                html += '</div>';
                html += '<div class="area-switch" onclick="totalSwitch(this,\'area\',' + res.data[i].id + ')"><span class="glyphicon glyphicon-off"></span></div>';
                html += '<div class="area-toggle" onclick="toggleArea(this,' + res.data[i].id + ');" data-load="load"><span class="glyphicon glyphicon-menu-down"></span></div>';
                html += '</div>';

                    html += '<div class="area-article">加载中...';
                    html += '</div>';

                html += '<div class="area-footer">';
                html += '<div class="tips pull-left">在连设备数：<span class="t-green">' + (onlineNum[i + 1]?onlineNum[i + 1].count:0) + '</span></div>';
                html += '<div class="checkbox pull-right">';
                // html += '<label><input type="checkbox">&nbsp;&nbsp;计入分区统计</label>';
                html += '</div>';
                html += '</div>';
                html += '</div>';
                html += '</div>';
                
            }
            areaObj = res.data;
        }
        $("#container").prepend(html);
    },token);
}
getGroup();

function getDevice(obj,groupId){
    var token = getCookie("token");
    var data = JSON.stringify({
        "groupId": groupId
    });
    common.Ajax("/group/device", data, "post", function (res) {
        if(res.code == "200"){
            var html = '';
            var html1 = '';
            var html2 = '';
            var html3 = '';
            var ctrlHtml = "";
            for(var j = 0; j < res.data.length; j++){
                if(!IsPC()){
                    ctrlHtml = 'onclick="setDeviceDetail(this)"';
                } else {
                    ctrlHtml = 'onmouseover="setDevice(this)" onmouseleave="clearSetDevice(this)"';
                }
                switch(res.data[j].deviceId){
                    case "0002":
                        html1 += '<li class="switchs-list" ' + ctrlHtml + ' data-id="' + res.data[j].id + '" data-endpoint="' + res.data[j].endPoint + '" data-type="0002" data-open="' + res.data[j].switchStatus + '" data-name="' + res.data[j].name + '"><span class="icon icon-lamp ' + (res.data[j].switchStatus==1?"":"off") + '"></span></li>';
                        break;
                    case "2":
                        html2 += '<li class="switchs-list" ' + ctrlHtml + ' data-id="' + res.data[j].id + '" data-endpoint="' + res.data[j].endPoint + '" data-type="2" data-open="' + res.data[j].switchStatus + '" data-name="' + res.data[j].name + '"><span class="icon icon-ariC ' + (res.data[j].switchStatus==1?"":"off") + '"></span></li>';
                        break;
                    case "0009":
                        html3 += '<li class="switchs-list" ' + ctrlHtml + ' data-id="' + res.data[j].id + '" data-endpoint="' + res.data[j].endPoint + '" data-type="0009" data-open="' + res.data[j].switchStatus + '" data-name="' + res.data[j].name + '"><span class="icon icon-socket ' + (res.data[j].switchStatus==1?"":"off") + '"></span></li>';
                        break;
                }
            }
            html += '<div class="switchs-container">';
            html += '<ul class="switchs">';
            html += html1;
            html += '<li><span class="icon icon-add" data-type="0002" onclick="addDevice(this,' + groupId + ');"></span></li>';
            html += '</ul>';
            /*html += '<div class="total-switch" onclick="totalSwitch(this,\'total\')">';
            html += '<div class="text">灯</div>';
            html += '<div class="icon"><span class="glyphicon glyphicon-off"></span></div>';
            html += '</div>';*/
            html += '</div>';

            html += '<div class="switchs-container">';
            html += '<ul class="switchs">';
            html += html2;
            html += '<li><span class="icon icon-add" data-type="2" onclick="addDevice(this,' + groupId + ');"></span></li>';
            html += '</ul>';
            /*html += '<div class="total-switch" onclick="totalSwitch(this,\'total\')">';
            html += '<div class="text">空调</div>';
            html += '<div class="icon glyphicon glyphicon-off"></div>';
            html += '</div>';*/
            html += '</div>';

            html += '<div class="switchs-container">';
            html += '<ul class="switchs">';
            html += html3;
            html += '<li><span class="icon icon-add" data-type="0009" onclick="addDevice(this,' + groupId + ');"></span></li>';
            html += '</ul>';
            /*html += '<div class="total-switch" onclick="totalSwitch(this,\'total\')">';
            html += '<div class="text">电器</div>';
            html += '<div class="icon glyphicon glyphicon-off"></div>';
            html += '</div>';*/
            html += '</div>';
            obj.html(html);
        }
    },token);
}

/* setClock */
function setClock(that){
    if($(that).next(".dropdown-timing.active").length >0){
        $(that).next(".dropdown-timing.active").remove();
    } else {
        var html = '';
        html += '<div class="dropdown-timing active">';
        html += '<span class="angle"></span>';
        html += '<ul>';
        html += '</ul>';
        html += '<div class="add-set glyphicon glyphicon-plus" onclick="addClock(this)"></div>';
        html += '</div>';
        $(that).after(html);
    }
}
/* delClock */
function delClock(that){
    layer.confirm('确认要删除该定时吗？',function(index){
        $(that).closest(".timing-list").remove();
        layer.close(index);
    });
}

/* addClock */
function addClock(that){
    var thisCont = $(that).closest(".dropdown-timing");
    var html = '';
        html += '<li class="timing-list">';
        html += '<div class="main">';
        html += '<div class="input-group date">';
        html += '<input type="text" class="form-control" value="09:00" title="" onfocus="timeTaker(this)">';
        html += '<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>';
        html += '</div>';
        html += '<div class="timing-switch form-group">';
        html += '<div class="text control-label pull-left gray6">全区设备</div>';
        html += '<select class="select form-control pull-left" style="width:80px;" title="">';
        html += '<option value="1">开启</option>';
        html += '<option value="2">关闭</option>';
        html += '</select>';
        html += '</div>';
        html += '</div>';
        html += '<div class="handle">';
        html += '<a href="javascript:void(0);" class="handle-btn">暂停</a>';
        html += '<a href="javascript:void(0);" class="handle-btn" onclick="delClock(this)">×</a>';
        html += '</div>';
        html += '</li>';
    thisCont.find("ul").append(html);
}

/* totalSwitch */
function totalSwitch(that,switchType,ctrlId){
    var thisTemp;
    var url;
    var mydata;
    var status;
    var token = getCookie("token");
    if (switchType == "total"){
        thisTemp = $(that).prev(".switchs");
    } else {
        thisTemp = $(that).parent().next();
        if($(that).hasClass("off")){
            status = "open";
        } else {
            status = "close";
        }
        url = "/device/sendList";
        mydata = JSON.stringify({
            "groupId":ctrlId,
            "status":status
        });
    }
    var tempArr = [];
    layer.confirm('确定要控制全部开关吗？',function(index){
        if($(that).hasClass("off")){
            $(that).removeClass("off");
            thisTemp.find(".switchs-list").each(function(){
                thisTemp.find(".icon").removeClass("off");
                tempArr.push(thisTemp.find(".switchs-list").attr("data-id"));
                return tempArr;
            });
            console.log(tempArr);
        } else {
            $(that).addClass("off");
            thisTemp.find(".switchs-list").each(function(){
                thisTemp.find(".icon").addClass("off");
                tempArr.push(thisTemp.find(".switchs-list").attr("data-id"));
                return tempArr;
            });
            console.log(tempArr);
        }

        common.Ajax(url, mydata, "post", function (res) {
            console.log(res);
            if(res.code == "200"){
                layer.msg(res.msg,{icon:1,time:1000});
            } else {
                layer.msg(res.msg,{icon:2,time:1000});
            }
        },token);

        layer.close(index);
    });
}

/* checkSwitch */
function checkSwitch(){
    console.log(0);
}

/**/
var timer,tempDev;
function setDevice(that) {
    clearTimeout(timer);
    timer = setTimeout(function () {
        setDeviceDetail(that);
    }, 500);
    return tempDev;
}

function setDeviceDetail(that){

    tempDev = $(that);
    var thisTemp = $(that);
    var deviceId = $(that).attr("data-id");
    var deviceName = $(that).attr("data-name");
    var end_point = $(that).attr("data-endpoint");
    var devideType = $(that).attr("data-type");
    var devideState = $(that).attr("data-open");

    var vwidth = $(that).width();
    var n = $(that).index();
    var vleft = (n % 5 + 0.5) * vwidth;
    var vheight = parseInt(n / 5) * 80;

    if(!IsPC()){
        $("body").append('<div class="transparent-mark" onclick="removeDetailPhone();"></div>');
    }
    var html = "";
    html += '<div class="switch-detail" onmouseleave="removeDet(this);" style="top:' + vheight + 'px;">';
    // html += '<span class="angle size12" style="left:'+ vleft +'px;"></span>';
    switch (devideType){
        case "0002":
            html += '<div class="leftC"><div class="switch-control lamp ' + (devideState==1?"":"off") + '" onclick="switchControl(this,\'' + deviceId + '\')"></div></div>';
            // html += '<div class="leftC"><div class="switch-control lamp ' + (devideState==1?"":"off") + '" onclick="switchControl(this,\'' + deviceId + '\')"></div><div class="icon-clock"><span class="glyphicon glyphicon-time"></span></div></div>';
            html += '<ul class="rightC">';
            html += '<li class="rightC-list">';
            html += '<div class="tit">设备名称：</div>';
            html += '<div class="main t-white"><input type="text" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + deviceId + '\',\'device\')" class="rename-text w-full" value="' + deviceName + '"/></div>';
            html += '</li>';
            html += '<li class="rightC-list">';
            html += '<div class="tit">设备类型：</div><div class="main">照明灯</div></li>';
            break;
        case "2":
            html += '<div class="leftC"><div class="switch-control ariC ' + (devideState==1?"":"off") + '" onclick="switchControl(this,\'' + deviceId + '\')"></div><div class="icon-clock"><span class="glyphicon glyphicon-time"></span></div></div>';
            html += '<ul class="rightC">';
            html += '<li class="rightC-list">';
            html += '<div class="tit">设备名称：</div>';
            html += '<div class="main t-white"><input type="text" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + deviceId + '\',\'device\')" class="rename-text w-full" value="' + deviceName + '"/></div>';
            html += '</li>';
            html += '<li class="rightC-list">';
            html += '<div class="tit">设备类型：</div><div class="main">空调</div></li>';
            break;
        case "0009":
            html += '<div class="leftC"><div class="switch-control socket ' + (devideState==1?"":"off") + '" onclick="switchControl(this,\'' + deviceId + '\')"></div></div>';
            html += '<ul class="rightC">';
            html += '<li class="rightC-list">';
            html += '<div class="tit">设备名称：</div>';
            html += '<div class="main t-white"><input type="text" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + deviceId + '\',\'device\')" class="rename-text w-full" value="' + deviceName + '"/></div>';
            html += '</li>';
            html += '<li class="rightC-list">';
            html += '<div class="tit">设备类型：</div><div class="main">电器</div></li>';
            break;
    }
    html += '<li class="rightC-list">';
    html += '<div class="tit">设备型号：</div>';
    html += '<div class="main t-white">' + end_point + '</div>';
    html += '</li>';
    /*html += '<li class="rightC-list">';
    html += '<div class="tit">设备定时：</div>';
    html += '<div class="main t-white">';
    html += '<dl class="device-timer">';

    html += '</dl>';
    html += '</div>';
    html += '</li>';
    html += '<li class="rightC-list">';
    html += '<div class="tit">&nbsp;</div>';
    html += '<div class="main"><span onclick="addTimer(this)" class="add-set glyphicon glyphicon-plus"></span></div>';
    html += '</li>';*/
    html += '</ul>';
    html += '</div>';
    if ($(".switch-detail").length > 0) {
        $(".switch-detail").remove();
    }
    thisTemp.closest(".switchs-container").prepend(html);
    $('.dropdown-toggle').dropdown();
}

function clearSetDevice() {
    clearTimeout(timer);
}

function removeDet(that){
    // 关闭浮窗
    setTimeout(function (){
        $(that).remove();
    },500);
}

function removeDetailPhone(){
    $(".switch-detail").remove();
    $(".transparent-mark").remove();
}

/* lampSwitch */
function lampSwitch(that){
    if (!$(that).hasClass("active")) {
        $(that).addClass("active");
    } else {
        $(that).removeClass("active");
    }
}

/* addTimer */
function addTimer(that){
    var thisTemp = $(that).closest(".rightC").find(".device-timer");
    var html = '';
        html += '<dd>';
        html += '<input type="text" class="timing" title="" value="09:00" onfocus="timeTaker(this)"/>';
        html += '<span class="glyphicon glyphicon-time"></span>';
        html += '<div class="btn-group timing-select">';
        html += '<button class="btn btn-default btn-xs dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">';
        html += '选择&nbsp;&nbsp;<span class="caret"></span>';
        html += '</button>';
        html += '<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">';
        html += '<li><a href="javascript:void(0);">开启</a></li>';
        html += '<li><a href="javascript:void(0);">关闭</a></li>';
        html += '</ul>';
        html += '</div>';
        html += '<div class="switch bBlue small" onclick="lampSwitch(this)"><div class="dot"></div></div>';
        html += '<a href="javascript:void(0);" class="pull-right white f20" onclick="delTimer(this)">×</a>';
        html += '</dd>';
    thisTemp.append(html);
}

/* delTimer */
function delTimer(that){
    layer.confirm('确认要删除该定时吗？',function(index){
        $(that).closest("dd").remove();
        layer.close(index);
    });
}

function switchControl(that,devId){
    var status;
    if($(that).hasClass("off")){
        status = "open";
    } else {
        status = "close";
    }

    var url = "/device/send";
    var token = getCookie("token");
    var mydata = JSON.stringify({
        "id":devId,
        "status":status
    });
    var requestType = "post";
    common.Ajax(url, mydata, requestType, function (res) {
        console.log(res);
        if(res.code == "200"){
            layer.msg(res.msg,{icon:1,time:1000});
            if(status == "open"){
                $(that).removeClass("off");
                tempDev.attr("data-open","1");
                tempDev.children(".icon").removeClass("off");

            } else {
                $(that).addClass("off");
                tempDev.attr("data-open","0");
                tempDev.children(".icon").addClass("off");
            }
        } else {
            layer.msg(res.msg,{icon:2,time:1000});
            return false;
        }
    },token);
}
function controlDevice(that,id,status){
    // isLogin();
}

/* addGroup */
function addGroup(that){
    // isLogin();
    var url = "/group/add";
    var token = getCookie("token");
    var mydata = JSON.stringify({
        "name":"新建分区"
    });
    var requestType = "post";
    common.Ajax(url, mydata, requestType, function (res) {
        console.log(res);
        if(res.code == "200"){
            var html = "";
            html += '<div class="col-md-6 col-sm-12">';
            html += '<div class="area-container active">';
            html += '<div class="area-header">';
            html += '<div class="area-names pull-left f20" data-id="' + res.data.id + '"><input type="text" oninput="getValue(this,this.value)" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + res.data.id + '\',\'area\')" class="rename-text" value="' + res.data.name + '"/><span class="text" style="visibility:hidden;"> ' + res.data.name + '</span><i class="layui-icon">&#xe642;</i> <i class="layui-icon delete" title="删除该分区" onclick="delGroup(this,\'' + res.data.id + '\')">&#xe640;</i></div>';
            html += '<div class="area-plans pull-right">';
            // html += '<span class="clock glyphicon glyphicon-time f20" onclick="setClock(this);"></span>';
            html += '<div class="dropdown-timing">';
            html += '<span class="angle"></span>'
            html += '<div class="add-set glyphicon glyphicon-plus"></div>';
            html += '</div>';
            html += '</div>';
            html += '<div class="area-switch" onclick="totalSwitch(this,\'area\',' + res.data.id + ')"><span class="glyphicon glyphicon-off"></span></div>';
            html += '<div class="area-toggle" onclick="toggleArea(this);"><span class="glyphicon glyphicon-menu-down"></span></div>';
            html += '</div>';
            html += '<div class="area-article">';
            html += '<div class="switchs-container">';
            html += '<ul class="switchs">';
            html += '<li><span class="icon icon-add" onclick="addDevice(this);"></span></li>';
            html += '</ul>';
            html += '</div>';
            html += '</div>';
            html += '<div class="area-footer">&nbsp;</div>';
            html += '</div>';
            html += '</div>';
            $(that).parent().before(html);
        }
    },token);
}

/* addDevice */
function addDevice(that,addGroupId){
    var vwidth = $(that).width();
    var n = $(that).parent("li").index();
    var vleft = (n % 5 + 0.5) * vwidth;
    var vheight = parseInt(n / 5 +1) * 80;
    var html = "";
    var url = "/group/device";
    var token = getCookie("token");
    var mydata = JSON.stringify({
        "groupId": "0"
    });

    if($(that).closest(".switchs-container").find(".dropdown-adddevice").length > 0){
        $(that).closest(".switchs-container").find(".dropdown-adddevice").remove();
    } else {
        $(".dropdown-adddevice").remove();
        common.Ajax(url, mydata,"post", function (res){
            console.log(res);
            if(res.code == "200"){
                html += '<div class="dropdown-adddevice" style="top:' + vheight + 'px;">';
                html += '<span class="angle size12" style="left:'+ vleft +'px;"></span>';
                html += '<dl class="weifenqu">';
                html += '<dt>';
                html += '<input type="checkbox" name="device"/>';
                html += '<span>&nbsp;&nbsp;全部未分区设备</span>';
                html += '</dt>';
                if (res.data == null || res.data.length <= 0){
                    html += '<dd>暂无未分区设备</dd>';
                } else {
                    for(var i = 0; i < res.data.length; i++){
                        if(res.data[i].deviceId == "0002" || res.data[i].deviceId == "0009"){
                            if(res.data[i].groupId == "0" || res.data[i].groupId == ""){
                                html += '<dd class="dropDown-list">';
                                html += '<div class="line"></div>';
                                html += '<label class="tx-box">';
                                html += '<input type="checkbox" name="device" value="' + res.data[i].id + '"/>';
                                html += '<span class="text">【未分区】设备【' + (res.data[i].name || "未命名") + '】</span>';
                                html += '<span class="msg">' + new Date(res.data[i].addTime).toLocaleString() + '&nbsp;&nbsp;首次上线</span>';
                                html += '</label>';
                                html += '</dd>';
                            }
                        }
                    }
                }
                html += '</dl>';

                html += '<dl class="yifenqu" data-load="">';
                html += '<dt onclick="addDeviceArea(this,' + addGroupId + ');">';
                html += '<a class="add" href="javascript:void(0);">添加已分区设备</a>';
                html += '</dt>';
                html += '</dl>';

                html += '<div class="btn-cont w200 center mb20 mt20" style="overflow:hidden">';
                html += '<a class="user-btn btnmini white pull-left" href="javascript:void(0);" onclick="removeDropdown(this);">&nbsp;&nbsp;取消&nbsp;&nbsp;</a>';
                html += '<a class="user-btn btnmini green pull-right" href="javascript:void(0);" onclick="saveDropdown(this,' + addGroupId + ');">&nbsp;&nbsp;保存&nbsp;&nbsp;</a>';
                html += '</div>';
                html += '</div>';
                $(that).closest(".switchs").after(html);
            }
        },token);
    }
}

function AddAllDevice(groupId,groupName,addTime,id,name){
    this.groupId = groupId;
    this.groupName = groupName;
    this.addTime = addTime;
    this.id = id;
    this.name = name;
}
var allDevice = [];
function getAllDevice(groupId,addGroupId,groupName){
    var token = getCookie("token");
    var data = JSON.stringify({
        "groupId": groupId
    });
    if(groupId != "0" && groupId != addGroupId){

        $.ajax({
            async: false,
            type: "post",
            contentType: "application/json",
            dataType : "json",
            url: "/group/device",
            data: data,
            beforeSend: function(request) {
                request.setRequestHeader("token", token);
            },
            success: function (res) {
                if(res.code == "200"){
                    for (var i = 0; i < res.data.length; i++) {
                        allDevice.push(new AddAllDevice(groupId,groupName,res.data[i].addTime,res.data[i].id,res.data[i].name));
                    };
                }
            },
            error: function (err) {
                console.log(err);
            }
        });

    }
}
// AreaDevice
function addDeviceArea(that,addGroupId){
    var loadState = $(that).closest(".yifenqu").attr("data-load");
    if(loadState == ""){
        for (var i = 0; i < areaObj.length; i++) {
            getAllDevice(areaObj[i].id,addGroupId,areaObj[i].name);
        };
        console.log(allDevice);

        var html = '';
        if (allDevice == null || allDevice.length <= 0){
            html += '<dd>暂无设备</dd>';
        } else {
            for(var i = 0; i < allDevice.length; i++){
                html += '<dd class="dropDown-list" data-groupid="' + allDevice[i].groupId + '" data-id="' + allDevice[i].id + '">';
                html += '<div class="line"></div>';
                html += '<label class="tx-box">';
                html += '<input type="checkbox" name="device" value="' + allDevice[i].id + '"/>';
                html += '<span class="text">【' + allDevice[i].groupName + '】设备【' + allDevice[i].name + '】</span>';
                html += '<span class="msg">' + new Date(allDevice[i].addTime).toLocaleString() + '&nbsp;&nbsp;首次上线</span>';
                html += '</label>';
                html += '</dd>';
            }
        }
        $(that).closest(".yifenqu").append(html);
        $(that).closest(".yifenqu").attr("data-load","load");
        allDevice = [];
    }
    
}

function removeDropdown(that){
    $(that).closest(".dropdown-adddevice").remove();
}

function ObjStory(groupId,id) {
    this.GroupId = groupId;
    this.Id= id;
}
function saveDropdown(that,addGroupId){
    var ids = [];
    var crtlGroup = [];
    $(".dropdown-adddevice input[type=checkbox]:checked").each(function(){
        var thisid = $(this).val();
        var thisgroupid = $(this).closest(".dropDown-list").attr("data-groupid");
        ids.push(thisid);
        crtlGroup.push(new ObjStory(thisgroupid,thisid));
    });
    console.log(ids);

    if(ids.length > 0){
        var url = "/device/setGroup";
        var token = getCookie("token");
        var mydata = JSON.stringify({
            "id":ids[0],
            "groupId":addGroupId
        });
        var requestType = "post";
        common.Ajax(url, mydata, requestType, function (res) {
            console.log(res);
            if(res.code == "200"){
                layer.msg(res.msg,{icon:1,time:1000});
                var sysHtml = "";
                for(var i = 0; i < crtlGroup.length; i++){
                    
                }
                var time = setTimeout(function () {
                    window.location.reload();
                }, 800);
            } else {
                layer.msg(res.msg,{icon:2,time:1000});
            }
        },token);
        $(that).closest(".dropdown-adddevice").remove();
    } else {
        layer.msg("请选择设备");
    }
}

function delGroup(that,delId){
    // isLogin();
    layer.confirm('确认要删除该分区吗？',function(index){
        var url = "/group/delete";
        var token = getCookie("token");
        var mydata = JSON.stringify({
            "id":delId,
            "token":token
        });
        var requestType = "post";
        common.Ajax(url, mydata, requestType, function (res) {
            console.log(res);
            if(res.code == "200"){
                layer.msg(res.msg,{icon:1,time:1000});
                $(that).closest(".area-container").parent().remove();
            } else {
                layer.msg(res.msg,{icon:2,time:1000});
            }
        },token);
        layer.close(index);
    });
}