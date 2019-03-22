package com.koreacb.msa.loan.controller;

import com.koreacb.msa.loan.model.Loan;
import com.koreacb.msa.loan.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return Mono.just(loanRepository.getOne(2L));
    }

    @GetMapping("/flux")
    public Flux<Loan> flux() {
        return Flux.fromIterable(loanRepository.findAll());
    }
}
