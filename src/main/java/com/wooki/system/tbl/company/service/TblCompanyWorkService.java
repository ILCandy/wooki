package com.wooki.system.tbl.company.service;

import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.tbl.company.entity.TblCompanyWork;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-01-13
 */
public interface TblCompanyWorkService extends IService<TblCompanyWork> {
	public boolean updateCompanyWork(TblCompanyWork companyWork,List<TblCompanyWork> workList);
}
