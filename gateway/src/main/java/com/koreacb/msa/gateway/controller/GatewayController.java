package com.koreacb.msa.gateway.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        Mono<String> stringMono = webClient.get().uri(uriBuilder -> uriBuilder.path("/apis").queryParams(map).build()).retrieve().bodyToMono(String.class);
        String block = stringMono.block();
        log.debug("Result: {}", stringMono);
        stringMono.subscribe(System.out::println);

        return Mono.just(block);
    }

    @GetMapping("/monoString2")
    public String monoString2() {
        log.debug("Entered /monoString2");
        val serviceList = retrieveServiceList();
        List<Mono<String>> producers = new ArrayList<>();
        val result = new HashMap<String, String>();

        for (final Service each : serviceList) {
            Mono<String> mono = webClient.get().uri(uriBuilder -> uriBuilder.path(each.endpointUrl).queryParam("id", each.param).build()).retrieve().bodyToMono(String.class);
            producers.add(mono);
            mono.subscribe(response -> result.put(each.endpointUrl, response));
        }
//        Mono<String> mono1 = webClient.get().uri(uriBuilder -> uriBuilder.path("/reactor/monoString").queryParam("id", 1).build()).retrieve().bodyToMono(String.class);
//        System.out.println("1");
//        Mono<String> mono2 = webClient.get().uri(uriBuilder -> uriBuilder.path("/reactor/monoString").queryParam("id", 2).build()).retrieve().bodyToMono(String.class);
//        System.out.println("2");
//        Mono<String> mono3 = webClient.get().uri(uriBuilder -> uriBuilder.path("/reactor/monoString").queryParam("id", 3).build()).retrieve().bodyToMono(String.class);
//        System.out.println("3");


        Mono.when(producers).block();
        log.debug("Result: " + result.toString());
        return null;
    }

    private List<Service> retrieveServiceList() {
        List<Service> services = new ArrayList<>();
        services.add(new Service("/reactor/monoString", "1"));
        services.add(new Service("/reactor/monoString", "2"));
        services.add(new Service("/reactor/monoString", "3"));

        return services;
    }

    @AllArgsConstructor
    private class Service {
        String endpointUrl;
        String param;
    }
}
