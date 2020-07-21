package com.myproject.common.security.api.exception.handler;

import com.myproject.common.exception.api.exception.HttpExceptions;
import com.myproject.common.exception.api.handler.RestExceptionHandler;
import com.myproject.common.security.api.exception.JwtAuthenticationException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;


public abstract class SecuredRestExceptionHandler extends RestExceptionHandler {


    public SecuredRestExceptionHandler(ApplicationContext context) {
        super(context);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
        ResponseEntity onJwtAuthenticationException(JwtAuthenticationException ex) {
        if (!Objects.isNull(ex.getWrappedException()))
            return onHttpException(ex.getWrappedException());
        else if (!Objects.isNull(ex.getFeignException()))
            return onFeignException(ex.getFeignException());
        return onException(ex);
    }

    @ExceptionHandler({AccessDeniedException.class})
    ResponseEntity onAccessDeniedException(AccessDeniedException ex) {
        return onHttpException(new HttpExceptions.ForbiddenException("authorization.access.denied"));
    }
    @ExceptionHandler({InsufficientAuthenticationException.class})
    ResponseEntity onInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return onHttpException(new HttpExceptions.ForbiddenException("authorization.access.denied"));
    }
}
