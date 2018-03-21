package com.wooki.system.amme.info.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wooki.system.amme.info.entity.AmmeterCurrentMonth;
import com.wooki.system.amme.info.mapper.AmmeterCurrentMonthMapper;
import com.wooki.system.amme.info.service.AmmeterCurrentMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2018-02-02
 */
@Service
public class AmmeterCurrentMonthServiceImpl extends ServiceImpl<AmmeterCurrentMonthMapper, AmmeterCurrentMonth> implements AmmeterCurrentMonthService {

    @Autowired
    AmmeterCurrentMonthMapper currentMonthMapper;

    /**
     * 根据每小时获取的电表信息，刷新数据
     * @param list
     * @return
     */
    @Override
    @Transactional
    public boolean updateAmmeterCurrentMonth(List list) {

//        currentMonthMapper.updateBatchByUuid(list);
        for(int i = 0;i<list.size();i++)
            currentMonthMapper.updateByUuid(list.get(i));
        return true;
    }

    @Override
    public Double currentMonthInfo(List uuids) {
        return currentMonthMapper.currentMonthInfo(uuids);
    }

    @Override
    public Integer setZero() {
        return currentMonthMapper.setZero();
    }

    @Override
    public List selectEveryDevice(List uuids){
        return currentMonthMapper.selectEveryDevice(uuids);
    }

}
