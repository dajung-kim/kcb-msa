package com.koreacb.msa.card.exception;

public class CardNotFoundException extends RuntimeException {

    CardNotFoundException(Long id) {
        super("Could not find card " + id);
    }
}
