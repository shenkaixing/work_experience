package com.wudong.diy.spirng.mvc.globalhandler;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.processing.FilerException;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * controller异常统一拦截处理
 *
 */
@ControllerAdvice
@Slf4j
public class ControllerHandler {

    /**
     * 全局统一异常错误
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResultVO handleAll(Exception ex, WebRequest request) {
        log.error("异常信息:",ex);
        ErrorEnum error = ErrorEnum.SYSTEM_BUSY;
        if (ex instanceof IllegalArgumentException) {
            return ResultVO.error(ex.getMessage());
        }else if (ex instanceof FilerException) {
            return ResultVO.error(ex.getMessage());
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            error = ErrorEnum.SYSTEM_BUSY;
        }  else if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            StringBuffer stringBuffer = new StringBuffer();
            allErrors.stream().findFirst().ifPresent(errorr -> {
                stringBuffer.append(errorr.getDefaultMessage());
            });
            return ResultVO.error(stringBuffer.toString());
        }
        return ResultVO.error(error);
    }

    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultVO httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(">>>错误请求方法>>", ex.getMessage());
        String methods = "";
        //支持的请求方式
        String[] supportedMethods = ex.getSupportedMethods();
        for (String method : supportedMethods) {
            methods += method;
        }
        ResultVO result = ResultVO.error("Request method " + ex.getMethod() + "  not supported !" +
                " supported method : " + methods + "!", 100);
        return result;
    }

    @ResponseBody
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResultVO httpHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        log.error(">>>错误MediaType>>,{}", ex.getMessage());
        //支持的请求方式
        List<MediaType> supportedMediaTypes = ex.getSupportedMediaTypes();
        List<String> objects = Lists.newArrayList();
        for (MediaType supportedMediaType : supportedMediaTypes) {
            String type = supportedMediaType.getType();
            objects.add(type);
        }

        ResultVO result = ResultVO.error("ERROR: MediaType is not supported. only supported MediaTypes:" + StringUtils.join(objects, ","), 100);
        return result;
    }

    @ResponseBody
    @ExceptionHandler(value = ResourceAccessException.class)
    public ResultVO httpUnknownHostException(ResourceAccessException ex) {
        log.error(">>>域名请求异常>>,{}", ex.getMessage());

        ResultVO result = ResultVO.error("ERROR: UnknownHostException:", 100);
        return result;
    }

}