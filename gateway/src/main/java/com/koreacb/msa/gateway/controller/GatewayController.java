package com.koreacb.msa.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gw")
@Slf4j
public class GatewayController {
    private WebClient webClient = WebClient.create("http://localhost:13000");

    @GetMapping("/mono")
    public String mono() {
        log.debug("Entered /mono");
        Mono<String> stringMono = webClient.get().uri("/reactor/mono").retrieve().bodyToMono(String.class);
        log.debug("Result: {}", stringMono);
        stringMono.subscribe(System.out::println);
        return stringMono.toString();
    }

    @GetMapping("/monoString")
    public String monoString(@RequestParam final MultiValueMap<String, String> map) {
        log.debug("Entered /monoString");
        Mono<String> stringMono = webClient.get().uri(uriBuilder -> uriBuilder.path("/reactor/monoString").queryParams(map).build()).retrieve().bodyToMono(String.class);
        log.debug("Result: {}", stringMono);
        stringMono.subscribe(System.out::println);
        return stringMono.toString();
    }
}
