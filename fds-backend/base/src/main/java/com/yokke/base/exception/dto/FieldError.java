package com.yokke.base.exception.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FieldError {

  private String field;
  private String errorCode;
  private String message;

}
