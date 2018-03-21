package com.wooki.function.exception;

import com.alibaba.fastjson.JSON;
import com.wooki.system.base.BaseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/17
 * Time : 下午3:33
 */
@CrossOrigin
@RestControllerAdvice(annotations = {RestController.class,Controller.class})
public class ExceptionHandle {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 自定义异常
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Object processException(CustomException ex, HttpServletRequest request, HttpServletResponse response){
        logger.error(getExceptionDetail(ex));
        return JSON.toJSONString(BaseJson.fail(((CustomException) ex).getCode(),((CustomException) ex).getMsg()));
    }

    /**
     * 普通异常
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object processException(Exception ex, HttpServletRequest request, HttpServletResponse response){
        logger.error(getExceptionDetail(ex));
        return JSON.toJSONString(BaseJson.fail(ex.getMessage()));
    }

    private String getExceptionDetail(Exception e) {
        StringBuffer stringBuffer = new StringBuffer(e.toString() + "\n");
        StackTraceElement[] messages = e.getStackTrace();
        int length = messages.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append("\t"+messages[i].toString()+"\n");
        }
        return stringBuffer.toString();
    }

}
