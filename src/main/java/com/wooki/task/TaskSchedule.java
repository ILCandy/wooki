package com.wooki.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.service.TblDeviceTaskService;
import com.wooki.system.tbl.task.service.TblTaskService;
import com.wooki.system.thread.DeviceSendThread;
import com.wooki.util.common.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/11
 * Time : 下午4:51
 * 定时器，10秒查一次
 */
@Component
public class TaskSchedule {

    @Autowired
    TblTaskService taskService;

    @Autowired
    TblDeviceTaskService deviceTaskService;

    private Calendar calendar;
    private Long lastTime;


    // 一天换一个日期 ,这里只是用来记录日期
    @Scheduled(cron = "0 30 0 * * ?")
    public void oneDay(){
        this.calendar = Calendar.getInstance();
    }

    /**
     * 每十秒出发一次定时器查询
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void every10Second()throws Exception{
        Integer day = setTime();
        Long currentTime = currentTime();
        try {
            // 频率执行的
            EntityWrapper taskEw = new EntityWrapper();
            taskEw.eq("enable", Constant.ENABLE_TRUE).
                    eq("frequency", Constant.FREQUENCY_TRUE).
                    between("time", lastTime, currentTime);
            taskEw.setSqlSelect("id");
            // 在这段时间内执行的频率任务 id 提取出来
            List<Long> taskList = taskService.selectList(taskEw);
            List ids = getTaskId(taskList);


            // 频率执行的
            if (ids != null && ids.size() > 0) {
                // list 合并起来查找
//            taskList.addAll(onceList);

                // 要执行的设备提取出来
                EntityWrapper deviceTaskEW = new EntityWrapper();
                deviceTaskEW.setSqlSelect("gateway_id", "data").eq("day", day).in("task_id", ids);
                List<TblDeviceTask> deviceTaskList = deviceTaskService.selectList(deviceTaskEW);
                System.out.println("发送定时任务:"+deviceTaskList);
                if (deviceTaskList != null && deviceTaskList.size() > 0) {
                    // 执行操作
//                    GatewayClient.deviceListSendMsg(deviceTaskList);
                    new DeviceSendThread(deviceTaskList).start();
                }

                // 要更新 device-task 的id
//            List<Long> deviceTaskIds = new ArrayList<>();
//            for (int i = 0; i < deviceTaskList.size(); i++) {
//                TblDeviceTask deviceTask = deviceTaskList.get(i);
//                // 为空的时候，是单次
//                if (deviceTask.getDay() == null)
//                    deviceTaskIds.add(deviceTask.getId());
//            }
//
//            if(deviceTaskIds.size()>0) {
//                // 吧单次设备的定时更新为不启动
//                deviceTaskService.updateEnableBatch(deviceTaskIds, Constant.ENABLE_FALSE);
//            }
            }

            // 单次执行的
            EntityWrapper oneEW = new EntityWrapper();
            oneEW.eq("enable", Constant.ENABLE_TRUE)
                    .eq("frequency", Constant.FREQUENCY_FALSE)
                    .between("time", lastTime, currentTime);
            oneEW.setSqlSelect("id");
            // 在这段时间内执行的单次任务 id 提取出来
            List onceList = taskService.selectList(oneEW);
            List onceIds = getTaskId(onceList);

            // 如果有单次执行的
            if (onceIds != null && onceIds.size() > 0) {

                // 要执行的设备提取出来
                EntityWrapper deviceTaskEW = new EntityWrapper();
                deviceTaskEW.setSqlSelect("gateway_id", "data").in("task_id", onceIds);
                List<TblDeviceTask> deviceTaskList = deviceTaskService.selectList(deviceTaskEW);

                if (deviceTaskList != null && deviceTaskList.size() > 0) {
                    // 执行操作
//                    GatewayClient.deviceListSendMsg(deviceTaskList);
                    new DeviceSendThread(deviceTaskList).start();
                }
                // 将单次的设为不可用

                // 执行条件，要更新的任务id
                EntityWrapper updateEnableEW = new EntityWrapper();
                updateEnableEW.in("task_id", onceIds);

                // 更新状态
                TblDeviceTask deviceTask = new TblDeviceTask();
                deviceTask.setEnable(Constant.ENABLE_FALSE);

                // 把单次定时的任务更新不启动
                taskService.updateEnableBatch(onceIds, Constant.ENABLE_FALSE);
                // 把单次执行的定时－设备 更新为不启动
                deviceTaskService.update(deviceTask, updateEnableEW);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lastTime = currentTime;
        }
    }

    // 设置前10秒 ，后10秒
    private Integer setTime(){
        if (calendar == null)
            oneDay();
        Integer day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    // 当天已过的时间戳
    private Long currentTime(){
        Long currentTime = DateUtil.nowDayTime();
        if (lastTime == null)
            lastTime = currentTime - Constant.tenSec;
        return currentTime;
    }

    private List getTaskId(List list)throws NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        if(list==null||list.size()<=0)
            return null;
        List ids = new ArrayList();
        Method getId = list.get(0).getClass().getDeclaredMethod("getId");
        for(Object o:list){
            ids.add((Long)getId.invoke(o));
        }
        return ids;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

}
