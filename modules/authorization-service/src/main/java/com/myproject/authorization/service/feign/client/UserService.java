package com.myproject.authorization.service.feign.client;

import com.myproject.usermanagement.api.service.UserManagementService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("userManagementService")
public interface UserService extends UserManagementService {
}
