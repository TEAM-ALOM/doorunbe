package com.alom.dorundorunbe.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

  private final ErrorCode errorCode;
  private final String detail;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    this.detail = "";
  }

  public BusinessException(ErrorCode errorCode, String detail) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    this.detail = detail;
  }
}