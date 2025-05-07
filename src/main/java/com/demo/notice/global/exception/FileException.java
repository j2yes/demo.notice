package com.demo.notice.global.exception;

import com.demo.notice.global.util.response.ResponseCode;

public class FileException extends BaseException {

  public enum FileExceptionExceptionCode implements ResponseCode {
    FILE_STORE_FAILED("FLE-001", "file store failed"),
    FILE_LOAD_FAILED("FLE-002", "file load failed"),
    FILE_DELETE_FAILED("FLE-003", "file delete failed");

    private final String code;
    private final String message;

    FileExceptionExceptionCode(String code, String message) {
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

  public FileException(FileExceptionExceptionCode code) {
    super(code);
  }
}
