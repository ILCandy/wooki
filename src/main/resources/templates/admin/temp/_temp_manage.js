
/* slideArea */
function slideArea(type){
    if(type == "0"){
        $(".area-container").addClass("active");
    } else {
        $(".area-container").removeClass("active");
    }
}

/* toggleArea */
function toggleArea(that){
    $(that).closest(".area-container").toggleClass("active");
}

/* getDevice */
function getDevice(){
    // isLogin();
    var url = "/device/list";  // ../res/json/manage.analy.json
    var token = getCookie("token") || "s5w25asd23fefdasdgr";
    var mydata = "";
    common.Ajax(url, mydata,"get", function (res) {
        console.log(res);
        var html = "";
        if(res.code == "200"){
            for(var i = 0; i < res.data.length; i++){
                if(res.data[i][0].groupId != "0"){
                    html += '<div class="col-md-6 col-sm-12">';
                    html += '<div class="area-container">';
                    html += '<div class="area-header">';
                    html += '<div class="area-names pull-left f20" data-id="' + res.data[i][0].groupId + '"><input type="text" oninput="getValue(this,this.value)" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + res.data[i][0].groupId + '\',\'area\')" class="rename-text w-full" value="' + res.data[i][0].groupName + '"/><span class="text" style="visibility:hidden;"> ' + res.data[i][0].groupName + '</span><i class="layui-icon">&#xe642;</i> <i class="layui-icon delete" title="删除该分区" onclick="delGroup(this,\'' + res.data[i][0].groupId + '\')">&#xe640;</i></div>';
                    html += '<div class="area-plans pull-right">';
                    // html += '<span class="clock glyphicon glyphicon-time f20" onclick="setClock(this);"></span>';
                    html += '</div>';
                    html += '<div class="area-switch"  onclick="totalSwitch(this,\'area\')"><span class="glyphicon glyphicon-off"></span></div>';
                    html += '<div class="area-toggle" onclick="toggleArea(this);"><span class="glyphicon glyphicon-menu-down"></span></div>';
                    html += '</div>';
                    html += '<div class="area-article">';
                    var html1 = '';
                    var html2 = '';
                    var html3 = '';
                    for(var j = 0; j < res.data[i].length; j++){
                        switch(res.data[i][j].deviceId){
                            case "0002":
                                html1 += '<li class="switchs-list" onmouseover="setDevice(this)" onmouseleave="clearSetDevice(this)" data-id="' + res.data[i][j].id + '" data-endpoint="' + res.data[i][j].endPoint + '" data-type="0002" data-open="' + res.data[i][j].switchStatus + '" data-name="' + res.data[i][j].name + '"><span class="icon icon-lamp ' + (res.data[i][j].switchStatus==1?"":"off") + '"></span></li>';
                                break;
                            case "2":
                                html2 += '<li class="switchs-list" onmouseover="setDevice(this)" onmouseleave="clearSetDevice(this)" data-id="' + res.data[i][j].id + '" data-endpoint="' + res.data[i][j].endPoint + '" data-type="2" data-open="' + res.data[i][j].switchStatus + '" data-name="' + res.data[i][j].name + '"><span class="icon icon-ariC ' + (res.data[i][j].switchStatus==1?"":"off") + '"></span></li>';
                                break;
                            case "3":
                                html3 += '<li class="switchs-list" onmouseover="setDevice(this)" onmouseleave="clearSetDevice(this)" data-id="' + res.data[i][j].id + '" data-endpoint="' + res.data[i][j].endPoint + '" data-type="3" data-open="' + res.data[i][j].switchStatus + '" data-name="' + res.data[i][j].name + '"><span class="icon icon-socket ' + (res.data[i][j].switchStatus==1?"":"off") + '"></span></li>';
                                break;
                        }
                    }
                    html += '<div class="switchs-container">';
                    html += '<ul class="switchs">';
                    html += html1;
                    html += '<li><span class="icon icon-add" onclick="addDevice(this);"></span></li>';
                    html += '</ul>';
                    html += '<div class="total-switch" onclick="totalSwitch(this,\'total\')">';
                    html += '<div class="text">灯</div>';
                    html += '<div class="icon"><span class="glyphicon glyphicon-off"></span></div>';
                    html += '</div>';
                    html += '</div>';

                    html += '<div class="switchs-container">';
                    html += '<ul class="switchs">';
                    html += html2;
                    html += '<li><span class="icon icon-add" onclick="addDevice(this);"></span></li>';
                    html += '</ul>';
                    html += '<div class="total-switch" onclick="totalSwitch(this,\'total\')">';
                    html += '<div class="text">空调</div>';
                    html += '<div class="icon glyphicon glyphicon-off"></div>';
                    html += '</div>';
                    html += '</div>';

                    html += '<div class="switchs-container">';
                    html += '<ul class="switchs">';
                    html += html3;
                    html += '<li><span class="icon icon-add" onclick="addDevice(this);"></span></li>';
                    html += '</ul>';
                    html += '<div class="total-switch" onclick="totalSwitch(this,\'total\')">';
                    html += '<div class="text">电器</div>';
                    html += '<div class="icon glyphicon glyphicon-off"></div>';
                    html += '</div>';
                    html += '</div>';

                    html += '</div>';
                    html += '<div class="area-footer">';
                    html += '<div class="tips pull-left">在连设备数：<span class="t-green">20</span></div>';
                    html += '<div class="checkbox pull-right">';
                    html += '<label><input type="checkbox">&nbsp;&nbsp;计入分区统计</label>';
                    html += '</div>';
                    html += '</div>';
                    html += '</div>';
                    html += '</div>';
                }
                
            }
        }
        $("#container").prepend(html);
    },token);
}
getDevice();

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
function totalSwitch(that,switchType){
    var thisTemp;
    if (switchType == "total"){
        thisTemp = $(that).prev(".switchs");
    } else {
        thisTemp = $(that).parent().next();
    }
    var tempArr = [];
    layer.confirm('确认要控制全部开关吗？',function(index){
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

    clearTimeout(timer);
    timer = setTimeout(function () {
        var html = "";
        html += '<div class="switch-detail" onmouseleave="removeDet(this);" style="top:' + vheight + 'px;">';
        // html += '<span class="angle size12" style="left:'+ vleft +'px;"></span>';
        switch (devideType){
            case "0002":
                html += '<div class="leftC"><div class="switch-control lamp ' + (devideState==1?"":"off") + '" onclick="switchControl(this,\'' + deviceId + '\',\'' + end_point + '\')"></div><div class="icon-clock"><span class="glyphicon glyphicon-time"></span></div></div>';
                html += '<ul class="rightC">';
                html += '<li class="rightC-list">';
                html += '<div class="tit">设备名称：</div>';
                html += '<div class="main t-white"><input type="text" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + deviceId + '\')" class="rename-text w-full" value="' + deviceName + '"/></div>';
                html += '</li>';
                html += '<li class="rightC-list">';
                html += '<div class="tit">设备类型：</div><div class="main">照明灯</div></li>';
                break;
            case "2":
                html += '<div class="leftC"><div class="switch-control ariC ' + (devideState==1?"":"off") + '" onclick="switchControl(this,\'' + deviceId + '\',\'' + end_point + '\')"></div><div class="icon-clock"><span class="glyphicon glyphicon-time"></span></div></div>';
                html += '<ul class="rightC">';
                html += '<li class="rightC-list">';
                html += '<div class="tit">设备名称：</div>';
                html += '<div class="main t-white"><input type="text" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + deviceId + '\')" class="rename-text w-full" value="' + deviceName + '"/></div>';
                html += '</li>';
                html += '<li class="rightC-list">';
                html += '<div class="tit">设备类型：</div><div class="main">空调</div></li>';
                break;
            case "3":
                html += '<div class="leftC"><div class="switch-control socket ' + (devideState==1?"":"off") + '" onclick="switchControl(this,\'' + deviceId + '\',\'' + end_point + '\')"></div><div class="icon-clock"><span class="glyphicon glyphicon-time"></span></div></div>';
                html += '<ul class="rightC">';
                html += '<li class="rightC-list">';
                html += '<div class="tit">设备名称：</div>';
                html += '<div class="main t-white"><input type="text" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + deviceId + '\')" class="rename-text w-full" value="' + deviceName + '"/></div>';
                html += '</li>';
                html += '<li class="rightC-list">';
                html += '<div class="tit">设备类型：</div><div class="main">电器</div></li>';
                break;
        }
        html += '<li class="rightC-list">';
        html += '<div class="tit">设备型号：</div>';
        html += '<div class="main t-white">sa1fdwf13adew</div>';
        html += '</li>';
        html += '<li class="rightC-list">';
        html += '<div class="tit">设备定时：</div>';
        html += '<div class="main t-white">';
        html += '<dl class="device-timer">';

        html += '</dl>';
        html += '</div>';
        html += '</li>';
        html += '<li class="rightC-list">';
        html += '<div class="tit">&nbsp;</div>';
        html += '<div class="main"><span onclick="addTimer(this)" class="add-set glyphicon glyphicon-plus"></span></div>';
        html += '</li>';
        html += '</ul>';
        html += '</div>';
        if ($(".switch-detail").length > 0) {
            $(".switch-detail").remove();
        }
        thisTemp.closest(".switchs-container").prepend(html);
        $('.dropdown-toggle').dropdown();
    }, 500);
    return tempDev;
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

function switchControl(that,devId,end_point){
    if($(that).hasClass("off")){
        $(that).removeClass("off");
        tempDev.attr("data-open","1");
        tempDev.children(".icon").removeClass("off");

        controlDevice(that,end_point,"open");
    } else {
        $(that).addClass("off");
        tempDev.attr("data-open","0");
        tempDev.children(".icon").addClass("off");
        controlDevice(that,end_point,"close");
    }
}

/* addArea */
function addArea(that){
    // isLogin();
    var url = "/group/add";
    var token = getCookie("token");
    var mydata = JSON.stringify({
        "name":"新建分区"
    });
    var requestType = "post";
    common.Ajax(url, mydata, requestType, function (data) {
        console.log(res);
        if(res.code == "200"){
            var html = '<div class="col-md-6 col-sm-12">'+
                '<div class="area-container active">'+
                '<div class="area-header">'+
                '<div class="area-names pull-left f20" data-id="' + res.data.Id + '"><input type="text" onfocus="setTempRename(this.value)" onblur="setRename(this,\'' + res.data.Id + '\',\'area\')" class="rename-text" value="' + res.data.Id + '"/><i class="layui-icon" style="font-size: 20px; color: #ccc;">&#xe642;</i></div>'+
                '<div class="area-plans pull-right">'+
                // '<span class="clock glyphicon glyphicon-time f20" onclick="setClock(this);"></span>'+
                '<div class="dropdown-timing">'+
                '<span class="angle"></span>'+
                '<div class="add-set glyphicon glyphicon-plus"></div>'+
                '</div>'+
                '</div>'+
                '<div class="area-switch"><span class="glyphicon glyphicon-off"></span></div>'+
                '<div class="area-toggle" onclick="toggleArea(this);"><span class="glyphicon glyphicon-menu-down"></span></div>'+
                '</div>'+
                '<div class="area-article">'+
                '<div class="switchs-container">'+
                '<ul class="switchs">'+
                '<li><span class="icon icon-add" onclick="addDevice(this);"></span></li>'+
                '</ul>'+
                '</div>'+
                '</div>'+
                '<div class="area-footer">&nbsp;</div>'+
                '</div>'+
                '</div>';
            $(that).parent().before(html);
        }
    },token);
}

/* addDevice */
function addDevice(that){
    var vwidth = $(that).width();
    var n = $(that).parent("li").index();
    var vleft = (n % 5 + 0.5) * vwidth;
    var vheight = parseInt(n / 5 +1) * 80;
    var html = '<div class="dropdown-adddevice" style="top:' + vheight + 'px;">' +
        '<span class="angle size12" style="left:'+ vleft +'px;"></span>'+
        '<dl>'+
        '<dt>'+
        '<input type="checkbox" name="device"/>'+
        '<span>&nbsp;&nbsp;全部未分区设备</span>'+
        '</dt>'+
        '<dd class="dropDown-list">'+
        '<div class="line"></div>'+
        '<input type="checkbox" name="device"/>'+
        '<span class="text">&nbsp;&nbsp;【未分区】设备【灯1】</span>'+
        '<span class="msg">2017-12-22 19:03&nbsp;&nbsp;首次上线</span>'+
        '</dd>'+
        '<dd class="dropDown-list">'+
        '<div class="line"></div>'+
        '<input type="checkbox" name="device"/>'+
        '<span class="text">&nbsp;&nbsp;【未分区】设备【灯2】</span>'+
        '<span class="msg">2017-12-22 19:03&nbsp;&nbsp;首次上线</span>'+
        '</dd>'+
        '<dd class="dropDown-list">'+
        '<div class="line"></div>'+
        '<input type="checkbox" name="device"/>'+
        '<span class="text">&nbsp;&nbsp;【未分区】设备【灯3】</span>'+
        '<span class="msg">2017-12-22 19:03&nbsp;&nbsp;首次上线</span>'+
        '</dd>'+
        '</dl>'+
        '<a class="add" href="javascript:void(0);">添加已分区设备</a>'+

        '<div class="btn-cont w200 center mb20 mt20" style="overflow:hidden">'+
        '<a class="user-btn btnmini white pull-left" href="javascript:void(0);" onclick="removeDropdown(this);">&nbsp;&nbsp;取消&nbsp;&nbsp;</a>'+
        '<a class="user-btn btnmini green pull-right" href="javascript:void(0);" onclick="saveDropdown(this);">&nbsp;&nbsp;保存&nbsp;&nbsp;</a>'+
        '</div>'+
        '</div>';
    if($(that).closest(".switchs-container").find(".dropdown-adddevice").length > 0){
        $(that).closest(".switchs-container").find(".dropdown-adddevice").remove();
    } else {
        $(".dropdown-adddevice").remove();
        $(that).closest(".switchs").after(html);
    }
}

function removeDropdown(that){
    $(that).closest(".dropdown-adddevice").remove();
}

function saveDropdown(that){
    $(that).closest(".dropdown-adddevice").remove();
}


function controlDevice(that,id,status){
    // isLogin();
    var url = "/device/send";
    var token = getCookie("token");
    var mydata = JSON.stringify({
        "id":id,
        "status":status
    });
    var requestType = "post";
    common.Ajax(url, mydata, requestType, function (data) {
        console.log(res);
        if(res.code == "200"){
            layer.msg(res.msg,{icon:1,time:1000});
        } else {
            layer.msg(res.msg,{icon:2,time:1000});
        }
    },token);
}

function delGroup(that,delId){
    // isLogin();
    var url = "/group/delete";
    var token = getCookie("token");
    var mydata = JSON.stringify({
        "id":delId,
        "token":token
    });
    var requestType = "post";
    common.Ajax(url, mydata, requestType, function (data) {
        console.log(res);
        if(res.code == "200"){
            layer.msg(res.msg,{icon:1,time:1000});
        } else {
            layer.msg(res.msg,{icon:2,time:1000});
        }
    },token);
}