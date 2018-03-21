package com.wooki.system.air.gateway.repository;

import com.wooki.system.air.gateway.entity.AirGateway;
import org.springframework.data.repository.CrudRepository;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.air.gateway.repository
 * @date 2018/3/19
 */
public interface AirGatewayRepository extends CrudRepository<AirGateway, Integer> {

    public AirGateway findByUid(String uid);
}
