package com.koreacb.msa.edge.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "user")
public interface GreetingsClient {

    @GetMapping(value = "/greet/{name}")
    Map<String, String> greet(@PathVariable("name") String name);
}
