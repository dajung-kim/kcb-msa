package com.koreacb.msa.card.service;

import com.koreacb.msa.card.model.Card;

import java.util.List;

public interface CardService {

    Card findByMgtAcctNo(String mgtAcctNo);
    List<Card> findByTxAgncCd(String txAgncCd);
}
