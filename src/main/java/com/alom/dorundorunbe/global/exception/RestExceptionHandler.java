package com.alom.dorundorunbe.global.exception;

import com.alom.dorundorunbe.global.util.BasicResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
  private final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<BasicResponse> handleBusinessException(BusinessException exception) {
    LOGGER.info("[ExceptionHandler] Message: {}, Detail: {}", exception.getMessage(), exception.getDetail());

    return BasicResponse.to(exception);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<BasicResponse> handleRuntimeException(RuntimeException exception) {
    LOGGER.error("[ExceptionHandler] Runtime exception occurred: ", exception);

    ErrorCode errorCode = ErrorCode.FAIL_PROCEED;
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    BusinessException businessException = new BusinessException(errorCode, "서버 내부 오류가 발생했습니다.");
    LOGGER.error("[ExceptionHandler] Message: {}, Detail: {}", businessException.getMessage(), exception.getMessage());

    return ResponseEntity
        .status(status)
        .body(BasicResponse.of(businessException));
  }
}