package com.myproject.authorization.service.exception.handler;

import com.myproject.common.exception.api.handler.RestExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthorizationExceptionHandler extends RestExceptionHandler {

    @Autowired
    public AuthorizationExceptionHandler(ApplicationContext context) {
        super(context);
    }
}
