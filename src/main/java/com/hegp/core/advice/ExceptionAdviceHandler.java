package com.hegp.core.advice;

import com.hegp.core.domain.Response;
import com.hegp.core.exceptions.BussinessException;
import com.hegp.core.exceptions.ParamsMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.Serializable;

//@ControllerAdvice
@RestControllerAdvice
public class ExceptionAdviceHandler implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage(), e);
        return Response.build(500, e.getMessage());
    }

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleResourceAccessException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage(), e);
        return Response.build(500,"callback failed");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage(), e);
        return Response.build(400, e.getMessage());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public Response handleServletRequestBindingException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage(), e);
        return Response.build(400, e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public Response handleIOException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage(), e);
        return Response.build(400, e.getMessage());
    }

    @ExceptionHandler(ParamsMissingException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleParameterMissingException(HttpServletRequest request, HttpServletResponse response, ParamsMissingException e) {
        logger.error(e.getMessage(), e);
        return Response.build(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleBindException(HttpServletRequest request, HttpServletResponse response, BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        logger.error(e.getMessage(), e);
        return Response.error(500, bindingResult.getFieldErrors().get(0).getDefaultMessage());
    }

    /**
     * ConstraintViolationException 捕获的异常是
     * import org.springframework.validation.annotation.Validated;
     * import org.springframework.web.bind.annotation.*;
     * import javax.validation.constraints.NotEmpty;
     *
     * @Validated
     * @RestController
     * public class TestController {
     *
     *     @GetMapping("/test") // ConstraintViolationException是@Validated和@NotEmpty共同作用的异常
     *     public void test(@NotEmpty(message = "param1不允许为空") @RequestParam String param1) {
     *     }
     * }
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleConstraintViolationException(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        return Response.error(500, e.getConstraintViolations().iterator().next().getMessageTemplate());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleMissingServletRequestParameterException(HttpServletRequest request, HttpServletResponse response, MissingServletRequestParameterException e) {
        logger.error(e.getMessage(), e);
        return Response.error(500, e.getParameterName()+"不允许为空");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleMethodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
//        StringBuffer sb = new StringBuffer();
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            sb.append(fieldError.getDefaultMessage());
//            break;
//        }
        logger.error(e.getMessage(), e);
        return Response.error(500, bindingResult.getFieldErrors().get(0).getDefaultMessage());
    }

    // 有时候，直接在exception中获取code作为响应状态码比较好，用@ResponseStatus注解返回状态码不灵活
    // Response.error(500, e.getMessage());
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleIllegalArgumentException(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException e) {
        logger.error(e.getMessage(), e);
        return Response.error(500, e.getMessage());
    }

    @ExceptionHandler(BussinessException.class)
//    @ResponseBody
    public Response handleBussinessException(HttpServletRequest request, HttpServletResponse response, BussinessException e) {
        logger.error(e.getMessage(), e);
        response.setStatus(e.getCode());
        return Response.error(500, e.getMessage());
    }

//    @ExceptionHandler(HystrixTimeoutException.class)
//    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
//    public Response handleTimeOutException(Exception e) {
//        logger.error(e.getMessage(), e);
//        return Response.build( 408,"failed to connect to remote server");
//    }
}