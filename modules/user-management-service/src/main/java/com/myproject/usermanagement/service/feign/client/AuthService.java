package com.myproject.usermanagement.service.feign.client;

import com.myproject.authorization.api.service.AuthorizationService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("authorizationService")
public interface AuthService extends AuthorizationService {
}
