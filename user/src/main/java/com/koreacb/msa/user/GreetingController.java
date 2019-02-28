package com.koreacb.msa.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class GreetingController {

    private final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping(value = "/greet/{name}")
    public Map<String, String> hi(@PathVariable("name") String name){
        logger.debug("here!!!!!!!!!!!!!!!!!!");
        return Collections.singletonMap("greeting", "Hello, " + name + "!");
    }
}
