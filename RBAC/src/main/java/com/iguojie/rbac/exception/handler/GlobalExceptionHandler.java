package com.iguojie.rbac.exception.handler;

import com.iguojie.rbac.common.entity.JsonResult;
import com.iguojie.rbac.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;


/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    GlobalExceptionHandler() {
    }

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public JsonResult defaultErrorHandler(Throwable e, HttpServletResponse response) throws Exception {

        if (e instanceof BusinessException) {
            return JsonResult.fail(e.getMessage());
        }

        if (e instanceof BindException) {
            List<ObjectError> list = ((BindException) e).getBindingResult().getAllErrors();
            StringBuffer sb = new StringBuffer();
            Iterator<ObjectError> it = list.iterator();

            while (it.hasNext()) {
                ObjectError error = (ObjectError) it.next();
                sb.append(error.getDefaultMessage());
                break;
            }
            return JsonResult.fail(sb.toString());
        }


        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = validException.getBindingResult();
            if (bindingResult == null) {
                logger.error("no bindingResult found ", e);
                return JsonResult.fail("系统异常");
            }
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (allErrors == null || allErrors.size() == 0) {
                logger.error("no error message found ", e);
                return JsonResult.fail("系统异常");
            }
            ObjectError objectError = allErrors.get(0);
            return JsonResult.fail(objectError.getDefaultMessage());
        }

        //请求方法不支持
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return JsonResult.fail(e.getMessage());
        }
        //请求参数不正确
        if (e instanceof MissingServletRequestParameterException) {
            return JsonResult.fail(e.getMessage());
        }

        if (e instanceof ServletRequestBindingException) {
            return JsonResult.fail(e.getMessage());
        }

        if (e instanceof UnsupportedEncodingException) {
            return JsonResult.fail(e.getMessage());
        }

        if (e instanceof MissingServletRequestPartException) {
            return JsonResult.fail(e.getMessage());
        }

        if (e instanceof HttpMessageNotReadableException) {
            return JsonResult.fail(e.getMessage());
        }

        logger.error("system error ", e);
        return JsonResult.fail("系统异常");
    }
}
