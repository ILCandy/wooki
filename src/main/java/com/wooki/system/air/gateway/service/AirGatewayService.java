package com.wooki.system.air.gateway.service;

import com.wooki.system.air.gateway.entity.AirGateway;
import com.wooki.system.air.gateway.repository.AirGatewayRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.air.gateway.service
 * @date 2018/3/19
 */
@Service
public class AirGatewayService {
    @Resource
    private AirGatewayRepository gatewayRepository;

    @Transactional
    public void save(AirGateway gateway){
        gatewayRepository.save(gateway);
    }

    public Iterable<AirGateway> getAll(){
        return gatewayRepository.findAll();
    }

    public AirGateway findByUid(String uid){
        return gatewayRepository.findByUid(uid);
    }
}
