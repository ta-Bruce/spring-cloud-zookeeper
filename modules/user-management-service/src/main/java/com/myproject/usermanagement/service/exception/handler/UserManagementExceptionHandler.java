package com.myproject.usermanagement.service.exception.handler;

import com.myproject.common.exception.api.handler.RestExceptionHandler;
import com.myproject.common.security.api.exception.handler.SecuredRestExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserManagementExceptionHandler extends SecuredRestExceptionHandler {

    @Autowired
    public UserManagementExceptionHandler(ApplicationContext context) {
        super(context);
    }
}
