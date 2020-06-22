package com.oyyo.serviceBse.handler;


import com.oyyo.commonUtils.ExceptionUtil;
import com.oyyo.commonUtils.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Resp error(Exception e){
        e.printStackTrace();
        return Resp.error().message("出现未知错误！");
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public Resp error(BaseException e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return Resp.error().code(e.getCode()).message(e.getMsg());
    }
}
