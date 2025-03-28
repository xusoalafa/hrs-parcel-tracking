package com.hrs.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  /*
   * TODO: Used to handle global exception, so we can control for error message, code, etc.
   *  - Just apply a simple text for example
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<String> handleMethodArgumentNotValidException(Exception e) {
    log.error("MethodArgumentNotValidException: ", e);

    return ResponseEntity.badRequest().body("Argument Not Valid: " + e.getMessage());
  }

  @ExceptionHandler(value = Exception.class)
  ResponseEntity<String> handleUnexpectedException(Exception e) {
    log.error("UnexpectedException: ", e);
    return ResponseEntity.badRequest().body("Unexpected Exception: " + e.getMessage());
  }
}
