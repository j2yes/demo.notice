package com.demo.notice.global.exception;

import com.demo.notice.global.util.response.ResponseCode;

public class ResourceNotFoundException extends BaseException {

  public enum ResourceNotFoundExceptionCode implements ResponseCode {
    NOTICE_NOT_FOUND("RNF-001", "notice not found");

    private final String code;
    private final String message;

    ResourceNotFoundExceptionCode(String code, String message) {
      this.code = code;
      this.message = message;
    }

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }
  }

  public ResourceNotFoundException(ResourceNotFoundExceptionCode code) {
    super(code);
  }
}
