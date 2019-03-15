package com.koreacb.msa.loan.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
@Slf4j
public class LoanController {

    @GetMapping("/hello")
    @HystrixCommand(fallbackMethod = "fallback")
    public String loanGreeting(@RequestParam(required = false) final String name) {
        log.debug("/hello");
        if (name == null) {
            throw new IllegalStateException("Error occurred!");
        }
        return name;
    }

    private String fallback(final String name) {
        log.debug("Entered fallback()");
        return "fallback " + name;
    }
}
