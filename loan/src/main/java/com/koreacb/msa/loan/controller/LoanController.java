package com.koreacb.msa.loan.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @GetMapping("/hello")
    @HystrixCommand(fallbackMethod = "fallback")
    public String loanGreeting(@RequestParam(required = false) final String name) {
        if (name == null) {
            throw new RuntimeException("Error occurred!");
        }
        return name;
    }

    private String fallback(final String name) {
        logger.debug("Entered fallback()");
        return "fallback " + name;
    }
}
