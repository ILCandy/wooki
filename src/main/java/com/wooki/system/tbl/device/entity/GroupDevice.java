package com.wooki.system.tbl.device.entity;

import java.util.List;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/8
 * Time : 下午11:40
 */
public class GroupDevice {

    private List<TblDevice> deviceList;

    public List<TblDevice> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<TblDevice> deviceList) {
        this.deviceList = deviceList;
    }
}
