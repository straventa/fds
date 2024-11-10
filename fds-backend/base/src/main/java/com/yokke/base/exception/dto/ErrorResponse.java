package com.yokke.base.exception.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

  private Integer httpStatus;
  private String exception;
  private String message;
  private List<FieldError> fieldErrors;

}
