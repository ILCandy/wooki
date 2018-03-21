package com.wooki.system.tbl.report.controller;


import com.wooki.system.amme.info.service.AmmeterDayInfoService;
import com.wooki.system.analy.device.DeviceOperateAnaly;
import com.wooki.system.analy.devicelist.DeviceListAnaly;
import com.wooki.system.analy.report.DeviceReportAnaly;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.report.dto.ReportDto;
import com.wooki.system.tbl.report.service.TblReportService;
import com.wooki.task.AmmeterDaySchedule;
import com.wooki.task.AmmeterHourSchedule;
import com.wooki.task.AmmeterMonthSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
@RestController
@RequestMapping("/report")
public class TblReportController {

    private static final Logger logger = LoggerFactory.getLogger(TblReportController.class);

    @Autowired
    TblReportService reportService;

    @Autowired
    DeviceListAnaly deviceListAnaly;

    @Autowired
    DeviceOperateAnaly deviceOperateAnaly;

    @Autowired
    DeviceReportAnaly deviceReportAnaly;

    @Autowired
    AmmeterDayInfoService ammeterDayInfoService;

    /**
     * 接收云端上报的消息
     */
    @RequestMapping("/c")
    public String receiveReport(ReportDto reportDto)throws Exception{
        System.out.println("receive the report");
        System.out.println(reportDto);

        String []dataArr = reportDto.getData().split(" ");
        //  如果是设备列表类型 ，分析设备列表
        if(dataArr[Constant.RECEIVE_TYPE_INDEX].equals(Constant.LIST_TYPE)) {
            deviceListAnaly.analy(dataArr, reportDto);
        }else if (dataArr[Constant.RECEIVE_TYPE_INDEX].equals(Constant.OPERATE_DEVICE_TYPE)) {
            // 会返回029，暂时不做处理

        }else if (dataArr[Constant.RECEIVE_TYPE_INDEX].equals(Constant.DEVICE_OPERATE_TYPE)) {
            // 获取指定设备的开关状态
            deviceOperateAnaly.analy(dataArr,reportDto);
        }else if(dataArr[Constant.RECEIVE_TYPE_INDEX].equals(Constant.DEVICE_REPORT_TYPE)){
            // 设备主动上报
            deviceReportAnaly.analy(dataArr, reportDto);
        }
        return "OK";
    }

    @Autowired
    AmmeterMonthSchedule ammeterMonthSchedule;

    @Autowired
    AmmeterHourSchedule ammeterHourSchedule;

    @Autowired
    AmmeterDaySchedule ammeterDaySchedule;


    @GetMapping("t0")
    public Object t0() throws Exception{
        ammeterHourSchedule.everyOneHour();
        return "ok0";
    }
    
    @GetMapping("t")
    public Object t()throws Exception{
//        ammeterHourSchedule.everyOneHour();
//        ammeterMonthSchedule.everyMonth();
//        ammeterHourSchedule.everyOneHour();
        ammeterDaySchedule.dayCalcu();
        return "ok";
    }

    @GetMapping("t2")
    public Object t2()throws Exception{
        ammeterMonthSchedule.everyMonth();
        return "ok2";
    }

}
