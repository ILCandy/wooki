package com.wooki.system.amme.expand.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.amme.device.mapper.AmmeterDeviceMapper;
import com.wooki.system.amme.device.service.AmmeterDeviceService;
import com.wooki.system.amme.expand.entity.DeviceExpand;
import com.wooki.system.amme.expand.mapper.DeviceExpandMapper;
import com.wooki.system.amme.expand.service.DeviceExpandService;
import com.wooki.system.amme.info.entity.AmmeterCurrentMonth;
import com.wooki.system.amme.info.entity.AmmeterHourInfo;
import com.wooki.system.amme.info.service.AmmeterCurrentMonthService;
import com.wooki.system.amme.info.service.AmmeterHourInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2018-01-25
 */
@Service
public class DeviceExpandServiceImpl extends ServiceImpl<DeviceExpandMapper, DeviceExpand> implements DeviceExpandService {

    @Autowired
    AmmeterDeviceService deviceService;
    @Autowired
    AmmeterDeviceMapper deviceMapper;
    @Autowired
    DeviceExpandMapper deviceExpandMapper;
    @Autowired
    AmmeterHourInfoService hourInfoService;
    @Autowired
    AmmeterCurrentMonthService currentMonthService;

    @Override
    @Transactional
    public boolean updateAmmeterInfo(List<AmmeterDevice> deviceList, List<DeviceExpand> expandList, List<AmmeterHourInfo> resultList) {

        // 电表列表
        if(deviceList!=null&&deviceList.size()>0) {
            System.out.println("in updateAmmeterInfo deviceList ===== ");
            System.out.println(deviceList);

            AmmeterDevice ammeterDevice;
            AmmeterCurrentMonth ammeterCurrentMonth;

            List addDeviceList = new ArrayList();
            List addCurrentMonthList = new ArrayList();

            AmmeterDevice updateDevice = new AmmeterDevice();

            System.out.println(" 每小时更新===============");
            System.out.println(deviceList);
            System.out.println(" 每小时更新===============");
            // 电表插入
            for(int i = 0;i<deviceList.size();i++){
                ammeterDevice = deviceList.get(i);
                // 如果电表不存在
//                if (StringUtils.isEmpty(deviceMapper.checkUuid(ammeterDevice.getUuid()))) {
                if (deviceMapper.checkUuid(ammeterDevice.getUuid())<1) {
                    //  要添加的电表
                    addDeviceList.add(ammeterDevice);
                    // 新加入的电表需要添加当前月的信息
                    addCurrentMonthList.add(new AmmeterCurrentMonth(ammeterDevice));
                }
                else {
                    updateDevice.setUuid(ammeterDevice.getUuid());
//                    deviceMapper.updateByUuid(ammeterDevice);
                    deviceMapper.update(ammeterDevice,new EntityWrapper<>(updateDevice));
//                    updateCurrentMonthList.add(new AmmeterCurrentMonth(ammeterDevice));
                }
            }
            // 添加电表
            if(addDeviceList.size()>0)
                deviceService.insertBatch(addDeviceList);
            // 添加电表当前月的信息
            if(addCurrentMonthList.size()>0)
                currentMonthService.insertBatch(addCurrentMonthList);
//            if(updateCurrentMonthList.size()>0)
//                currentMonthService.updateAmmeterCurrentMonth(updateCurrentMonthList);
        }

        // 将差值插进 hour_info （每小时）表里
        // resultList
        if(resultList!=null&&resultList.size()>0) {
            System.out.println("in updateAmmeterInfo resultList ===== ");
            System.out.println(resultList);
            hourInfoService.insertBatch(resultList);
        }

        // 将现有的覆盖原来的旧数据
        if(expandList!=null&&expandList.size()>0) {
            System.out.println("in updateAmmeterInfo expandList ===== ");
            System.out.println(expandList);
            DeviceExpand deviceExpand;

            for(int i = 0;i<expandList.size();i++){
                deviceExpand = expandList.get(i);
                if (StringUtils.isEmpty(deviceExpandMapper.selectByUuid(deviceExpand.getUuid())))
                    deviceExpandMapper.insert(deviceExpand);
                else
                    deviceExpandMapper.updateByUuid(deviceExpand);
            }
//            deviceExpandMapper.replaceBatchByUuid(expandList);
            currentMonthService.updateAmmeterCurrentMonth(expandList);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateByUuidBatch(List<DeviceExpand> list) {
        for(DeviceExpand deviceExpand:list) {
            System.out.println(deviceExpand);
            deviceExpandMapper.updateByUuid(deviceExpand);
        }
//        deviceExpandMapper.updateByUuidBatch(list);
        return true;
    }

}
