package com.demo.notice.global.exception;

import com.demo.notice.global.util.response.ResponseCode;

public class DomainException extends BaseException {

  public enum DomainExceptionCode implements ResponseCode {
    INVALID_DOMAIN_INPUT("DMN-001", "invalid domain input");

    private final String code;
    private final String message;

    DomainExceptionCode(String code, String message) {
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

  public DomainException(DomainExceptionCode code) {
    super(code);
  }
}
