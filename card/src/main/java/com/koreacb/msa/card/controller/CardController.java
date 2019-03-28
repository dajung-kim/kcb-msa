package com.koreacb.msa.card.controller;

import com.koreacb.msa.card.model.Card;
import com.koreacb.msa.card.service.impl.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardController {

    @Autowired
    private CardServiceImpl cardServiceImpl;

    @GetMapping("/cards")
    public List<Card> getCardsByTxAgncCd(@RequestParam("type") String txAgncCd) {
        return cardServiceImpl.findByTxAgncCd(txAgncCd);
    }

    @GetMapping("/cards/{MGT_ACCT_NO}")
    public Card getCardByMgtAcctNo(@PathVariable("MGT_ACCT_NO") String mgt_acct_no) {
        return cardServiceImpl.findByMgtAcctNo(mgt_acct_no);
    }


}
