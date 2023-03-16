package com.att.validation;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException {

  private List<ValidationError> errors = new ArrayList<>();

  public ValidationException(ValidationError error){
    errors.add(error);
  }

}
