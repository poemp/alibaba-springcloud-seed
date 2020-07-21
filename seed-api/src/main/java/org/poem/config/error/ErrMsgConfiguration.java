package org.poem.config.error;

import com.alibaba.fastjson.JSONObject;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.poem.config.CenterContextHolder;
import org.poem.config.exception.ApiCenterProviderException;
import org.poem.config.exception.ApiCenterProviderRouteException;
import org.poem.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

/**
 * 处理下游服务的异常
 *
 * @author sangfor
 */
@ControllerAdvice
@Configuration
public class ErrMsgConfiguration implements ErrorDecoder {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ErrMsgConfiguration.class);

    /**
     * 下游的服务的异常的处理
     * @param s
     * @param response
     * @return
     */
    @Override
    public Exception decode(String s, Response response) {
        Exception exception = null;
        try {
            // 获取原始的返回内容
            String json = Util.toString(response.body().asReader());
            JSONObject jsonObject = JSONObject.parseObject(json);
            logger.error(json);
            int status =  jsonObject.getIntValue("status");
            String trace = jsonObject.getString("trace");
            String message = jsonObject.getString("message");
            if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                exception = new ApiCenterProviderException(message, new Throwable(trace));
            }else if(status == HttpStatus.NOT_FOUND.value()){
                exception = new ApiCenterProviderRouteException(message, new Throwable(trace));
            }else{
                exception = new RuntimeException(json);
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return exception;
    }

    /**
     * 格式化参数
     * @return
     */
    private static String getParam(){
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> param = CenterContextHolder.getParams();
        if (param != null){
            for (String s : param.keySet()) {
                stringBuilder.append("\n\t\t\t").append(String.format("%s",s)).append("--------------").append(JSONObject.toJSONString(param));
            }
        }
        return stringBuilder.toString();
    }
    /**
     * 格式化参数
     * @return
     */
    private static String getHeader(){
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> param = CenterContextHolder.getHeader();
        for (String s : param.keySet()) {
            stringBuilder.append("\n\t\t\t").append(String.format("%s",s)).append("--------------").append(JSONObject.toJSONString(param));
        }
        return stringBuilder.toString();
    }
    /**
     * 500 服务内部错误
     * 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<String> handlerUserNotExistException(Exception ex) {
        String serverPath = CenterContextHolder.getRequestPath();
        String provider = CenterContextHolder.getProviderName();
        String err = String.format("\n\t\tprovider name: %s  \n\t\tserver path : %s \n\t\tparameters: %s ", provider, serverPath,getParam() );
        logger.error(err);
        CommonResult<String> commonResult = new CommonResult<>();
        if (ex instanceof UndeclaredThrowableException){
            UndeclaredThrowableException throwableException = (UndeclaredThrowableException)ex;
            Throwable  throwable = throwableException.getUndeclaredThrowable();
            //下游的服务出现错误
            if(throwable instanceof ApiCenterProviderException){
                ApiCenterProviderException providerException = (ApiCenterProviderException) throwable;
                logger.error(providerException.getMessage(),providerException);
                commonResult.setCode(29);
                commonResult.setData(null);
                commonResult.setMessage("微服务出现异常，请稍后再试! ");
                return commonResult;
            }
            if (throwable instanceof ApiCenterProviderRouteException){
                ApiCenterProviderRouteException providerException = (ApiCenterProviderRouteException) throwable;
                logger.error(providerException.getMessage(),providerException);
                commonResult.setCode(30);
                commonResult.setData(null);
                commonResult.setMessage("联系管理员，微服务路径不正确!");
                return commonResult;
            }
        }
        logger.error(ex.getMessage(),ex);
        commonResult.setCode(28);
        commonResult.setData(null);
        commonResult.setMessage("微服务暂时还不可用，请稍后再试! ");
        return commonResult;
    }
}
