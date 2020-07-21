package com.myproject.common.exception.api.exception;

import org.springframework.http.HttpStatus;

public class HttpExceptions {

    public static class BadRequestException extends AbstractHttpException {

        public BadRequestException(String errorCode, Object... params) {
            super(HttpStatus.BAD_REQUEST,errorCode, params);
        }
    }

    public static class UnauthorizedException extends AbstractHttpException {

        public UnauthorizedException(String errorCode, Object... params) {
            super(HttpStatus.UNAUTHORIZED, errorCode, params);
        }
    }

    public static class ForbiddenException extends AbstractHttpException {

        public ForbiddenException(String errorCode, Object... params) {
            super(HttpStatus.FORBIDDEN,errorCode, params);
        }
    }

    public static class ResourceNotFoundException extends AbstractHttpException {

        public ResourceNotFoundException(String errorCode, Object... params) {
            super(HttpStatus.NOT_FOUND, errorCode, params);
        }
    }

    public static class InternalServerException extends AbstractHttpException {

        public InternalServerException(String errorCode, Object... params) {
            super(HttpStatus.INTERNAL_SERVER_ERROR,errorCode, params);
        }
    }


    public static abstract class AbstractHttpException extends RuntimeException {
        private HttpStatus httpStatus;
        private String errorCode;
        private Object[] params;

        public AbstractHttpException(HttpStatus status, String errorCode, Object... params){
            this.httpStatus = status;
            this.errorCode = errorCode;
            this.params = params;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public Object[] getParams() {
            return params;
        }
    }
}
