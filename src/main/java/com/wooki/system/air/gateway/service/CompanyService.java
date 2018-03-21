package com.wooki.system.air.gateway.service;

import com.wooki.system.air.gateway.repository.TblCompanyRepository;
import com.wooki.system.tbl.company.entity.TblCompany;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.system.air.gateway.service
 * @date 2018/3/21
 */
@Service
public class CompanyService {

    @Resource
    private TblCompanyRepository companyRepository;

    public TblCompany getOne(Integer id){
        return companyRepository.findOne(id);
    };

}
