package com.wooki.system.analy;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.common.GatewayClient;
import com.wooki.system.tbl.bindGateway.service.TblBindGatewayService;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.gateway.service.TblGatewayService;
import com.wooki.system.tbl.report.entity.TblReport;
import com.wooki.system.tbl.report.service.TblReportService;
import com.wooki.util.common.bean.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wooki.system.common.GatewayClient.formatString;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 17/12/31
 * Time : 上午10:39
 */
@Controller
public class TestController {

    private final Logger logger = LoggerFactory.getLogger( getClass() ) ;

    @Autowired
    TblReportService reportService;

    @Autowired
    TblDeviceService deviceService;

    @Autowired
    TblGatewayService tblGatewayService;

    @Autowired
    TblBindGatewayService bindGatewayService;

//    @RequestMapping("login")
//    public String login(){
//        return "login";
//    }

    @RequestMapping(value="tt")
    public ModelAndView tt(){
        logger.error("你是傻逼");
        if(true)
//            throw new CustomException(ReturnEnum.LACK_TOKEN_ERROR);
            throw new RuntimeException("aaa");
        return new ModelAndView("login");
    }
    @PostMapping("testSend")
    @ResponseBody
//    public Object testSend(@RequestBody TestSend analy)throws Exception{
    public Object testSend(@RequestBody TestSend test)throws Exception{
//        System.out.println("sendMsg :"+analy.getCode());
        int gateWay = 36381;
        // 发送消息
        GatewayClient.send(gateWay,test.getCode());
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 测试接收云端服务器消息
     * @param testReceive
     * @return
     */
    @PostMapping("testReceive")
    @ResponseBody
    public Object testReceive(@RequestBody TestReceive testReceive){

        // 解析
        TblDevice updateInfo = analy(testReceive);

        // 报告
        TblReport report = BeanMapper.map(testReceive,TblReport.class);
        report.setAddTime(new Date());
        report.setData(formatString(report.getData()));
        reportService.insert(report);

        // 设备的添加时间
        TblDevice device = new TblDevice();
        device.setEndPoint(updateInfo.getEndPoint());
        device.setShortAddr(updateInfo.getShortAddr());
        device.setGatewayId(updateInfo.getGatewayId());

        EntityWrapper deviceEW = new EntityWrapper(device);
//        deviceService.update();
        deviceService.update(updateInfo,deviceEW);
        return null;
    }

    /**
     * 云端的设备列表解析
     * @return
     */
    @RequestMapping("/listFromCloud")
    @ResponseBody
    public Object list(){
        analy3();
        return null;
    }

    /**
     * 面板开关操作解析
     * 接收云端返回数据解析
     * @param testReceive
     * @return
     */
    public TblDevice analy(TestReceive testReceive){
        String data = testReceive.getData();
        String newData = GatewayClient.formatString(data);
        String []dataArr = newData.split(" ");

        // 前面6位固定包头
//        Integer length = Integer.valueOf(dataArr[])   // 数据总长度
        // 从第七位开始解析
        String type = dataArr[6];
        Integer lastLength = Integer.valueOf(dataArr[7]);// 后面数据长度
        String shortAddr = dataArr[9]+dataArr[8]; // 短地址
        String endPoint = dataArr[10];              // 终端
        Integer status = Integer.valueOf(dataArr[dataArr.length-2]);

        System.out.println(newData);
        TblDevice device = new TblDevice();
        device.setShortAddr(shortAddr);
        device.setEndPoint(endPoint);
        device.setSwitchStatus(status);
        device.setUpdateTime(new Date());
        device.setData(newData);
        device.setGatewayId(testReceive.getGatewayId());

        System.out.println(device);
        return device;
    }


    /**
     * 门磁操作解析
     * 接收云端返回数据解析
     * @param testReceive
     * @return
     */
    private TblDevice analy2(TestReceive testReceive){
        String data = testReceive.getData();
        String newData = GatewayClient.formatString(data);
        String []dataArr = newData.split(" ");

        // 前面6位固定包头
//        Integer length = Integer.valueOf(dataArr[])   // 数据总长度
        String type = dataArr[6];
        Integer lastLength = Integer.valueOf(dataArr[7]);// 后面数据长度
        String shortAddr = dataArr[9]+dataArr[8]; // 短地址
        String endPoint = dataArr[10];              // 终端
        Integer status = Integer.valueOf(dataArr[dataArr.length-2]);

        System.out.println(newData);
        TblDevice device = new TblDevice();
        device.setShortAddr(shortAddr);
        device.setEndPoint(endPoint);
        device.setSwitchStatus(status);
        device.setUpdateTime(new Date());
        device.setData(newData);
        device.setGatewayId(testReceive.getGatewayId());
        return device;
    }

    // 模拟云端返回的设备列表
    public boolean analy3(){
        // 模拟云端返回的设备列表
        List<String> deviceList = new ArrayList<>();
        String s = "2F 00 0B 00 29 00 01 27 89 F2 12 04 01 02 00 01 00 03 68 D1 3A 15 00 4B 12 00 0F 46 42 35 36 2B 5A 53 57 31 49 4B 4A 31 2E 39 00 00 00 00 00";
        deviceList.add(s);
        TblDevice tblDevice = null;

        List<TblDevice> tblDeviceList = new ArrayList<>();
        for(String str :deviceList){
            String[] strArr = str.split(" ");
            Integer index = 0;
            // 设备
            tblDevice = new TblDevice();
            // 数据总长
            Integer allLength = Integer.valueOf(strArr[index+1]+strArr[index++],16);
            index++;
            // 数据类型
            Integer dataType = Integer.valueOf(strArr[index+1]+strArr[index++],16);
            index++;
            // 内容数据长度
            Integer dataLength = Integer.valueOf(strArr[index+1]+strArr[index++],16);
            index++;
            // 固定返回 0x01
            String response = strArr[index++];
            // 后续数据长度
            Integer followLength = Integer.valueOf(strArr[index++],16);
            // 设备短地址
            String shortAddr = strArr[index+1]+strArr[index++];
            index++;
            // 终端
            String endPoint = strArr[index++];
            // profile_id
            String profileId = strArr[index+1]+strArr[index++];
            index++;
            // 设备 device_id,设备的大类类型
            String deviceId = strArr[index+1]+strArr[index++];
            index++;
            // 开关状态
            Integer status = Integer.valueOf(strArr[index++],16);
            // 名字长度
            Integer nameLength = Integer.valueOf(strArr[index++],16);
            // 名字
            StringBuffer sb = new StringBuffer();
            for(int i = 0;i<nameLength;i++){
                sb.append(strArr[index++]);
                if(i<nameLength-1)
                    sb.append(" ");
            }
            String name = sb.toString();
            // 后续的下标起始
//            Integer index = 17+nameLength;
            // 在线状态
            Integer onlineStatus = Integer.valueOf(strArr[index++],16);
            // ieee ,固定8字节
            StringBuffer ieeeBuffer = new StringBuffer();
            for(int i = 0; i< Constant.IEEE_LENGTH; i++){
                ieeeBuffer.append(strArr[index++]);
            }
            // ieee
            String ieee = ieeeBuffer.toString();
            // sn长度
            Integer snLength = Integer.valueOf(strArr[index++],16);
            // sn码
            StringBuffer snBuffer = new StringBuffer();
            for(int i = 0;i<snLength;i++){
                snBuffer.append(strArr[index++]);
//                if(i<snLength-1)
//                    snBuffer.append(" ");
            }
            // sn 码
            String sn = snBuffer.toString();
            // 大类下面的小类
            String zoneType = strArr[index+1]+strArr[index++];
            // 最后三位暂时未确定
            String lasta = strArr[index++];
            String lastb = strArr[index++];
            String lastc = strArr[index++];

            tblDevice.setShortAddr(shortAddr);
            tblDevice.setEndPoint(endPoint);
            tblDevice.setProfileId(profileId);
            tblDevice.setDeviceId(deviceId);
            tblDevice.setIeee(ieee);
            tblDevice.setName(name);
            tblDevice.setZoneType(zoneType);
            tblDevice.setSwitchStatus(status);
            tblDevice.setSn(sn);
            tblDevice.setOnline(onlineStatus);
            tblDeviceList.add(tblDevice);
        }
        deviceService.insertBatch(tblDeviceList);
        return true;
    }
}
