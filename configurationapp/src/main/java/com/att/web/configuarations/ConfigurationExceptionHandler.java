package com.att.web.configuarations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ConfigurationExceptionHandler
{
  @ExceptionHandler(ConfigurationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseEntity onMethodArgumentNotValidException(ConfigurationException e)
  {
    return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}