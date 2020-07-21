package com.myproject.common.exception.api.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.myproject.common.exception.api.dto.ApiError;
import com.myproject.common.exception.api.dto.ApiValidationError;
import com.myproject.common.exception.api.exception.HttpExceptions;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@RequiredArgsConstructor
public abstract class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApplicationContext context;

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private ResponseEntity<String> buildResponseEntity(int status, String apiError) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(apiError, headers, HttpStatus.valueOf(status));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity onConstraintValidationException(ConstraintViolationException e) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST);
        Collection<ApiValidationError> subErrors = new ArrayList<>();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            subErrors.add(new ApiValidationError(violation.getPropertyPath().toString(), Collections.singleton(violation.getMessage())));
        }
        error.setSubErrors(subErrors);
        return buildResponseEntity(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getCause() instanceof JsonParseException)
            return this.handleExceptionInternal((Exception) ex.getCause(), (Object)null, headers, status, request);
        if (ex.getCause() instanceof InvalidFormatException){
            InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
            ApiError error = new ApiError(status);
            Collection<ApiValidationError> subErrors = new ArrayList<>();
            for(JsonMappingException.Reference path: invalidFormatException.getPath()){
                subErrors.add(new ApiValidationError(path.getFieldName(), Collections.singleton("Unsupported format")));
            }
            error.setSubErrors(subErrors);
            return buildResponseEntity(error);
        }
        return this.handleExceptionInternal(ex, (Object)null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST);
        Collection<ApiValidationError> subErrors = new ArrayList<>();
        Map<String, Collection<String>> fieldViolations = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!fieldViolations.containsKey(fieldError.getField()))
                fieldViolations.put(fieldError.getField(), new ArrayList<>());
            fieldViolations.get(fieldError.getField()).add(fieldError.getDefaultMessage());
        }
        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()){
            if (!fieldViolations.containsKey(objectError.getObjectName()))
                fieldViolations.put(objectError.getObjectName(), new ArrayList<>());
            fieldViolations.get(objectError.getObjectName()).add(objectError.getDefaultMessage());
        }
        for(String key : fieldViolations.keySet())
            subErrors.add(new ApiValidationError(key, fieldViolations.get(key)));
        error.setSubErrors(subErrors);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(HttpExceptions.AbstractHttpException.class)
    protected ResponseEntity onHttpException(HttpExceptions.AbstractHttpException ex) {
        String message = context.getMessage(ex.getErrorCode(), ex.getParams(), Locale.getDefault());
        ApiError apiError = new ApiError(ex.getHttpStatus(), message, ex);
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler({FeignException.class})
    protected ResponseEntity onFeignException(FeignException ex){

        return buildResponseEntity(ex.status(), ex.contentUTF8());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity onException(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    ResponseEntity onMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Throwable e = ex;
        while(!(e instanceof HttpExceptions.AbstractHttpException))
            e = e.getCause();
        if (!Objects.isNull(e))
            return onHttpException((HttpExceptions.AbstractHttpException) e);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex);
        return buildResponseEntity(apiError);
    }
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }
        return buildResponseEntity(new ApiError(status, ex.getMessage(), ex));
    }
}