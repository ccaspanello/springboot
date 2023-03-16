package com.att.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ValidationExceptionHandler
{
  /**
   * Triggered by @Valid annotation when JSR-380 annotations capture validation issues
   *
   * @param e
   * @return List<ValidationError>
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseEntity onMethodArgumentNotValidException(MethodArgumentNotValidException e)
  {
    List<ValidationError> result = new ArrayList<>();
    for (FieldError error : e.getBindingResult().getFieldErrors())
    {
      result.add(new ValidationError(error.getField(), error.getDefaultMessage()));
    }
    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }

  /**
   * Trigger by any custom ValidationException
   * @param e
   * @return List<ValidationError>
   */
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseEntity onValidationException(ValidationException e)
  {
    return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
  }
}