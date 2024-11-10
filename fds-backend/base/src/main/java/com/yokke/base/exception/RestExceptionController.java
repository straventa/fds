package com.yokke.base.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Validation failed"); // Custom message
        body.put("path", request.getDescription(false).replace("uri=", ""));

        // Adding the validation errors
        body.put("errors", ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    Map<String, Object> errorDetails = new HashMap<>();
                    if (error instanceof FieldError fieldError) {
                        errorDetails.put("field", fieldError.getField());
                        errorDetails.put("rejectedValue", fieldError.getRejectedValue());
                    }
                    errorDetails.put("defaultMessage", error.getDefaultMessage());
                    errorDetails.put("code", error.getCode());
                    return errorDetails;
                }).toList());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleNotFound(final NotFoundException exception) {
//        final ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND.value());
//        errorResponse.setException(exception.getClass().getSimpleName());
//        errorResponse.setMessage(exception.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
//            final MethodArgumentNotValidException exception) {
//        final BindingResult bindingResult = exception.getBindingResult();
//        final List<FieldError> fieldErrors = bindingResult.getFieldErrors()
//                .stream()
//                .map(error -> {
//                    System.out.printf("error=> " + error.toString());
//                    final FieldError fieldError = new FieldError();
//                    fieldError.setErrorCode(error.getCode());
//                    fieldError.setField(error.getField());
//                    fieldError.setMessage(error.getDefaultMessage());
//                    return fieldError;
//                })
//                .toList();
//        final ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
//        errorResponse.setMessage("Please validate your request");
//        errorResponse.setException(exception.getClass().getSimpleName());
//        errorResponse.setFieldErrors(fieldErrors);
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<ErrorResponse> handleResponseStatus(
//            final ResponseStatusException exception) {
//        final ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setHttpStatus(exception.getStatusCode().value());
//        errorResponse.setException(exception.getClass().getSimpleName());
//        errorResponse.setMessage(exception.getMessage());
//        return new ResponseEntity<>(errorResponse, exception.getStatusCode());
//    }

//  @ExceptionHandler(Throwable.class)
//  @ApiResponse(responseCode = "4xx/5xx", description = "Error")
//  public ResponseEntity<ErrorResponse> handleThrowable(final Throwable exception) {
//    exception.printStackTrace();
//    final ErrorResponse errorResponse = new ErrorResponse();
//    errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//    errorResponse.setException(exception.getClass().getSimpleName());
//    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//  }

//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException exception) {
//        final ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setHttpStatus(exception.getStatus().value());
//        errorResponse.setException(exception.getClass().getSimpleName());
//        errorResponse.setMessage(exception.getMessage());
//        return new ResponseEntity<>(errorResponse, exception.getStatus());
//    }

}
