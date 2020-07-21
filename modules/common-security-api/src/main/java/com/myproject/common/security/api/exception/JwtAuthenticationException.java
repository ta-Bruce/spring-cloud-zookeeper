package com.myproject.common.security.api.exception;

import com.myproject.common.exception.api.exception.HttpExceptions;
import feign.FeignException;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    private HttpExceptions.AbstractHttpException wrapped;
    private FeignException feignException;

    public JwtAuthenticationException(HttpExceptions.AbstractHttpException wrapped) {
        super(wrapped.getLocalizedMessage());
        this.wrapped = wrapped;
    }

    public HttpExceptions.AbstractHttpException getWrappedException() {
        return wrapped;
    }

    public JwtAuthenticationException(FeignException feignException) {
        super(feignException.getLocalizedMessage());
        this.feignException = feignException;
    }

    public FeignException getFeignException() {
        return feignException;
    }
}
