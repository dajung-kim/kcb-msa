package com.koreacb.msa.card;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardController {

    private final CardRepository repository;

    CardController(CardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cards")
    List<Card> all() {
       return repository.findAll();
    }

    @GetMapping("/cards/{usrSeqno}")
    List<Card> getCardsbyUsrSeqno(@PathVariable("usrSeqno") Long usrSeqno) {
        return repository.findByUsrSeqno(usrSeqno);
    }
}
