package com.koreacb.msa.edge;

import com.koreacb.msa.edge.client.GreetingsClient;
import com.koreacb.msa.edge.client.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class FeignClientApiGateway {
    private final Logger logger = LoggerFactory.getLogger(FeignClientApiGateway.class);

    private final GreetingsClient greetingsClient;
    private final UserClient userClient;

    @Autowired
    FeignClientApiGateway(final GreetingsClient greetingsClient, final UserClient userClient) {
        this.greetingsClient = greetingsClient;
        this.userClient = userClient;
    }

    @GetMapping("/{name}")
    public Map<String, String> feign(@PathVariable String name) {
        logger.debug("Name: {}", name);
        return this.greetingsClient.greet(name);
    }

    @GetMapping("/users/{id}")
    public String getUserInfo(@PathVariable String id) {
        logger.debug("Id: {}", id);
        return this.userClient.getUserInfo(id);
    }
}
