package com.shoplive.videostoragedemo.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<String> sizeLimitExceed(Exception e) {
    final var exceptionMessage = e.getMessage();
    log.info(exceptionMessage);
    return ResponseEntity.badRequest()
                         .body(exceptionMessage);
  }

}
