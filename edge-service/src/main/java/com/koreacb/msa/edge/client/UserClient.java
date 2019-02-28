package com.koreacb.msa.edge.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "user")
public interface UserClient {
    @GetMapping(value = "/users/{id}")
    String getUserInfo(@PathVariable("id") String id);
}
