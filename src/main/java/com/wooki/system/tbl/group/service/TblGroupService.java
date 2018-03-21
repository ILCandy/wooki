package com.wooki.system.tbl.group.service;

import com.wooki.system.tbl.group.entity.TblGroup;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-01-08
 */
public interface TblGroupService extends IService<TblGroup> {
    public boolean deleteGroup(TblGroup group);
}
