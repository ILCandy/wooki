package com.wooki.task;

import com.wooki.util.common.date.DateUtil;

import java.util.Date;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/31
 * Time : 上午12:52
 */
public class TimeInfo {
    private static String timeStr = DateUtil.dateToStr(new Date());
    private static String lastTimeStr = DateUtil.getLastDayStr();

    public static void setTimeStr(String timeStr) {
        TimeInfo.timeStr = timeStr;
    }

    public static String getTimeStr() {
        return timeStr;
    }

    public static String getLastTimeStr() {
        return lastTimeStr;
    }

    public static void setLastTimeStr(String lastTimeStr) {
        TimeInfo.lastTimeStr = lastTimeStr;
    }

    // 每天过凌晨的时候更新时间
    public static void setTime(){
        timeStr = DateUtil.dateToStr(new Date());
        lastTimeStr = DateUtil.getLastDayStr();
    }
}
