package com.wooki.system.tbl.company.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wooki.system.tbl.company.entity.TblCompanyWork;
import com.wooki.system.tbl.company.mapper.TblCompanyWorkMapper;
import com.wooki.system.tbl.company.service.TblCompanyWorkService;
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
 * @since 2018-01-13
 */
@Service
public class TblCompanyWorkServiceImpl extends ServiceImpl<TblCompanyWorkMapper, TblCompanyWork> implements TblCompanyWorkService {

    @Autowired
    TblCompanyWorkMapper companyWorkMapper;

    @Override
    @Transactional
    public boolean updateCompanyWork(TblCompanyWork companyWork, List<TblCompanyWork> workList) {
        // 删除原有办公信息
        companyWorkMapper.delete(new EntityWrapper<>(companyWork));
        // 新增办公信息
        insertBatch(workList);
        return true;
    }
}
