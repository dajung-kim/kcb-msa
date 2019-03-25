package com.koreacb.msa.loan.controller;

import com.google.gson.Gson;
import com.koreacb.msa.loan.model.Loan;
import com.koreacb.msa.loan.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactor")
@Slf4j
public class ReactorController {

    private final LoanRepository loanRepository;

    @Autowired
    public ReactorController(LoanRepository repository) {
        this.loanRepository = repository;
    }

    @GetMapping("/mono")
    public Mono<Loan> mono() {
        log.debug("Entered mono");
        Loan loan = loanRepository.getOne(2L);
        log.debug("Retrieved result: {}", loan);
        return Mono.just(loan);
    }

    @GetMapping("/monoString")
    public Mono<String> monoString(@RequestParam("id") long id) {
        log.debug("Entered monoString with param: {}", id);
        val loan = loanRepository.findById(id);
        val json = new Gson().toJson(loan.get(), Loan.class);
        log.debug("Retrieved result: {}", json);

        return Mono.just(json);
    }

    @GetMapping("/flux")
    public Flux<Loan> flux() {
        return Flux.fromIterable(loanRepository.findAll());
    }
}
