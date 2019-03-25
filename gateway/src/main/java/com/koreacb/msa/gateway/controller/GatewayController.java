package com.koreacb.msa.gateway.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

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
    public Mono<String> monoString(@RequestParam final MultiValueMap<String, String> map) {
        log.debug("Entered /monoString");
        Mono<String> stringMono = webClient.get().uri(uriBuilder -> uriBuilder.path("/reactor/monoString").queryParams(map).build()).retrieve().bodyToMono(String.class);
        String block = stringMono.block();
        log.debug("Result: {}", stringMono);
        stringMono.subscribe(System.out::println);

        return Mono.just(block);
    }

    @GetMapping("/monoString2")
    public String monoString2() {
        log.debug("Entered /monoString2");
        Mono<String> mono1 = webClient.get().uri(uriBuilder -> uriBuilder.path("/reactor/monoString").queryParam("id", 1).build()).retrieve().bodyToMono(String.class);
        Mono<String> mono2 = webClient.get().uri(uriBuilder -> uriBuilder.path("/reactor/monoString").queryParam("id", 2).build()).retrieve().bodyToMono(String.class);
        Mono<String> mono3 = webClient.get().uri(uriBuilder -> uriBuilder.path("/reactor/monoString").queryParam("id", 3).build()).retrieve().bodyToMono(String.class);
//        Mono<Void> when = Mono.when(mono1, mono2, mono3);
//        when.block();


        mono1.subscribe(System.out::println);
        mono2.subscribe(System.out::println);
        mono3.subscribe(System.out::println);


        return null;
    }
}
