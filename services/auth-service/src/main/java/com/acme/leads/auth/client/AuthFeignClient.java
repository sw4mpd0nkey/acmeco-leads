package com.acme.leads.auth.client;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.acme.leads.auth.dto.UserDetailsDTO;


@FeignClient(name = "auth-service", contextId = "authFeignClient")
public interface AuthFeignClient {
    @GetMapping("/users/username/{username}")
    UserDetailsDTO getUser(@PathVariable String username);
}