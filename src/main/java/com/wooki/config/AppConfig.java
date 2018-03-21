package com.wooki.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by weijieliao on 2017/12/16.
 */
@Component
@ConfigurationProperties( "application" )
@Data
public class AppConfig {

    private String aliAccessKeyId ;
    private String accessKeySecret ;

    // oss
    private String endPoint;
    private String bucketName;
    private String picturePrefix;

}
