package com.wooki.system.amme.info.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wooki.system.amme.info.entity.AmmeterMonthInfo;
import com.wooki.system.amme.info.mapper.AmmeterMonthInfoMapper;
import com.wooki.system.amme.info.service.AmmeterMonthInfoService;
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
public class AmmeterMonthInfoServiceImpl extends ServiceImpl<AmmeterMonthInfoMapper, AmmeterMonthInfo> implements AmmeterMonthInfoService {

    @Autowired
    AmmeterMonthInfoMapper ammeterMonthInfoMapper;


    @Override
    public List selectEveryMonthInfo(List uuids, Integer limit) {
        return ammeterMonthInfoMapper.selectEveryMonthInfo(uuids,limit);
    }
}
