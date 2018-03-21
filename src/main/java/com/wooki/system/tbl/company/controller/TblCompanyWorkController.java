package com.wooki.system.tbl.company.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.company.dto.WorkAddDto;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.company.entity.TblCompanyWork;
import com.wooki.system.tbl.company.service.TblCompanyService;
import com.wooki.system.tbl.company.service.TblCompanyWorkService;
import com.wooki.util.common.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2018-01-13
 */
@RestController
@RequestMapping("/company")
public class TblCompanyWorkController {

    @Autowired
    TblCompanyService companyService;

    @Autowired
    TblCompanyWorkService companyWorkService;

    @Autowired
    CompanyInfo companyInfo;

    /**
     * @param request
     * @param workAddDto
     * @return
     */
//    @PostMapping("/addWork")
//    public Object setWork(HttpServletRequest request, WorkAddDto workAddDto){
//        // 公司
//        TblCompany company = companyInfo.getRedisCompany(request);
//
//        List workList = new ArrayList<>();
//        // 公司的办公
//        TblCompanyWork work = new TblCompanyWork();
//        work.setCompanyId(company.getId());
//        work.setAddTime(new Date());
//        work.setWorkTime(workAddDto.getWorkTime());
//        work.setMiddayRest(workAddDto.getMiddayRest());
//        work.setComeOffWork(workAddDto.getComeOffWork());
//        // 默认都是是小周
//        work.setWorkDays(Constant.SMALL_WEEK_DAYS);
//        work.setUseFlag(Constant.ENABLE_TRUE);
//
//        // 如果有大小周
//        if(workAddDto.getHasBigAndSmall().equals(Constant.HAS_BIG_AND_SMALL)){
//            TblCompanyWork secondWork = BeanMapper.map(work,TblCompanyWork.class);
//            // 大周
//            secondWork.setWorkDays(Constant.BIG_WEEK_DAYS);
//            // 如果当前周是大周
//            if(workAddDto.getSmallOrBig().equals(Constant.BIG_WEEK)){
//                secondWork.setUseFlag(Constant.ENABLE_TRUE);
//                work.setUseFlag(Constant.ENABLE_FALSE);
//            }else{
//                // 如果当前是小周
//                work.setUseFlag(Constant.ENABLE_TRUE);
//                secondWork.setUseFlag(Constant.ENABLE_FALSE);
//            }
//            workList.add(secondWork);
//        }
//
//        workList.add(work);
//        companyWorkService.insertBatch(workList);
//        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
//    }

    /**
     * 获取办公信息
     * @param request
     * @return
     */
    @GetMapping("/getWork")
    public Object workInfo(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        TblCompanyWork work = new TblCompanyWork();
        work.setCompanyId(company.getId());
        List list = companyWorkService.selectList(new EntityWrapper<>(work));
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,list));
    }

    /**
     * 添加办公信息
     * @param request
     * @param workAddDto
     * @return
     */
    @PostMapping("/addWork")
    public Object setWork(HttpServletRequest request, @Valid @RequestBody WorkAddDto workAddDto) {

        TblCompany company = companyInfo.getRedisCompany(request);
        // 检查是否有添加过办公时间
        EntityWrapper cwEW = new EntityWrapper();
        cwEW.eq("company_id",company.getId());
        // 已经添加过办公信息
        if(companyWorkService.selectObj(cwEW)!=null)
            return JSON.toJSON(BaseJson.fail(Constant.HAS_COMPANYWORK_INFO));

        List works = getWorks(company,workAddDto);
        companyWorkService.insertBatch(works);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 修改办公时间
     * @param request
     * @param workAddDto
     * @return
     */
    @PostMapping("/updateWork")
    public Object updateWork(HttpServletRequest request, @Valid @RequestBody WorkAddDto workAddDto) {
        TblCompany company = companyInfo.getRedisCompany(request);
        // 要删除的办公信息
        TblCompanyWork companyWork = new TblCompanyWork();
        companyWork.setCompanyId(company.getId());
        List works = getWorks(company,workAddDto);
        companyWorkService.updateCompanyWork(companyWork,works);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }


    private List<TblCompanyWork> getWorks(TblCompany company,WorkAddDto workAddDto){
        List<TblCompanyWork> works = new ArrayList<>();
        // 当前周正在使用
        for(int i = 0 ;i<workAddDto.getWorkDays().size();i++) {
            String days = workAddDto.getWorkDays().get(i);
            TblCompanyWork work = new TblCompanyWork();
            work.setCompanyId(company.getId());
            // 当前周
            work.setWorkDays(DateUtil.getDaysToNumStr(days));
            work.setPeriod(workAddDto.getWorkDays().size()); //上班周期
            work.setWorkTime(workAddDto.getWorkTime());
            work.setMiddayRest(workAddDto.getMiddayRest());
            work.setElectricCharge(workAddDto.getElectricCharge());
//            work.setComeOffWork(DateUtil.getDateByString(workAddDto.getComeOffWork()));
            work.setComeOffWork(workAddDto.getComeOffWork());
            work.setAddTime(new Date());
            // 第一个是当前周，设置为默认使用
            if(i==0)
                work.setUseFlag(Constant.ENABLE_TRUE);
            else
                work.setUseFlag(Constant.ENABLE_FALSE);
            works.add(work);
        }
        return works;
    }

    /**
     * 删除办公信息
     * @param request
     * @return
     */
    @PostMapping("/delWork")
    public Object delWork(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        TblCompanyWork work = new TblCompanyWork();
        work.setCompanyId(company.getId());
        companyWorkService.delete(new EntityWrapper<>(work));
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }


}


