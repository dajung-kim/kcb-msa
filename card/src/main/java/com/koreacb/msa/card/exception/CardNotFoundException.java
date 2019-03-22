package com.koreacb.msa.card;

public class CardNotFoundException extends RuntimeException {

    CardNotFoundException(Long id) {
        super("Could not find card " + id);
    }
}
