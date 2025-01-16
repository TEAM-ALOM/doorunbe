package com.alom.dorundorunbe.global.util;

import com.alom.dorundorunbe.global.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record BasicResponse(
    HttpStatus status,
    String message,
    String detail
) {
  public static BasicResponse of(BusinessException exception) {
    return new BasicResponse(
        exception.getErrorCode().getHttpStatus(),
        exception.getErrorCode().getMessage(),
        exception.getDetail()
    );
  }

  public static BasicResponse of(String message) {
    return new BasicResponse(
        HttpStatus.OK,
        message,
        ""
    );
  }

  public static ResponseEntity<BasicResponse> to(BusinessException exception) {
    return ResponseEntity
        .status(exception.getErrorCode().getHttpStatus())
        .body(BasicResponse.of(exception));
  }
}
