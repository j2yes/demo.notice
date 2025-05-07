package com.demo.notice.presentation.advice;

import com.demo.notice.global.exception.BaseException;
import com.demo.notice.global.util.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = BaseException.class)
  public Response<Void> exceptionHandler(BaseException e) {
    log.debug("exception occurred : [{}]", e.getMessage(), e);
    return Response.fail(e);
  }

  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = Exception.class)
  public Response<Void> errorHandler(Exception e) {
    log.error("error at : [{}]", e.getClass().getName(), e);
    return Response.fail();
  }
}
