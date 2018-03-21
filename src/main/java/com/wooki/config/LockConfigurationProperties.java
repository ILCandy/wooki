package com.wooki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/28
 * Time : 下午3:28
 */
@Component
public class LockConfigurationProperties {

    // 门禁的  token，暂时固定
    private static final String lockAccesToken = "b372c792621d576179f2163ab194489edf5b8966cb2977aL";

    public static String getLockAccesToken() {
        return lockAccesToken;
    }

    @Value("${lock-platform.url}")
    private String baseUrl;
    @Value("${lock-platform.lock-list}")
    private String lockListUrl;
    @Value("${lock-platform.temp-pwd}")
    private String tempPwdUrl;
    @Value("${lock-platform.method.get}")
    private String methodGet;
    @Value("${lock-platform.method.add}")
    private String methodAdd;
    @Value("${lock-platform.method.delete}")
    private String methodDelete;
    @Value("${lock-platform.encode}")
    private String encode;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getLockListUrl() {
        return lockListUrl;
    }

    public void setLockListUrl(String lockListUrl) {
        this.lockListUrl = lockListUrl;
    }

    public String getTempPwdUrl() {
        return tempPwdUrl;
    }

    public void setTempPwdUrl(String tempPwdUrl) {
        this.tempPwdUrl = tempPwdUrl;
    }

    public String getMethodGet() {
        return methodGet;
    }

    public void setMethodGet(String methodGet) {
        this.methodGet = methodGet;
    }

    public String getMethodAdd() {
        return methodAdd;
    }

    public void setMethodAdd(String methodAdd) {
        this.methodAdd = methodAdd;
    }

    public String getMethodDelete() {
        return methodDelete;
    }

    public void setMethodDelete(String methodDelete) {
        this.methodDelete = methodDelete;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }
}
