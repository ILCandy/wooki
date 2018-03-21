package com.wooki.system.amme.info.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.amme.info.entity.AmmeterHourInfo;
import com.wooki.system.amme.info.mapper.AmmeterHourInfoMapper;
import com.wooki.system.amme.info.service.AmmeterHourInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2018-01-27
 */
@Service
public class AmmeterHourInfoServiceImpl extends ServiceImpl<AmmeterHourInfoMapper, AmmeterHourInfo> implements AmmeterHourInfoService {

    @Autowired
    AmmeterHourInfoMapper ammeterHourInfoMapper;

    @Override
    public List selectMonthSum(EntityWrapper entityWrapper) {
        return ammeterHourInfoMapper.selectMonthSum(entityWrapper);
    }

    @Override
    public List selectDaySum(EntityWrapper entityWrapper) {
        return ammeterHourInfoMapper.selectDaySum(entityWrapper);
    }
}
