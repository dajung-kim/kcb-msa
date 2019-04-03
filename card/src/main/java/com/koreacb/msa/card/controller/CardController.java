package com.koreacb.msa.card.controller;

import com.koreacb.msa.card.core.Result;
import com.koreacb.msa.card.core.ResultGenerator;
import com.koreacb.msa.card.model.Card;
import com.koreacb.msa.card.service.impl.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class CardController {

    @Autowired
    private CardServiceImpl cardServiceImpl;
    @Value("${kcb.config.env}")
    private String env;
    @Value("${kcb.config.module}")
    private String module;

    @GetMapping("/cards")
    public List<Card> getCardsByTxAgncCd(@RequestParam("type") String txAgncCd) {
        return cardServiceImpl.findByTxAgncCd(txAgncCd);
    }

    @GetMapping("/cards/{MGT_ACCT_NO}")
    public Card getCardByMgtAcctNo(@PathVariable("MGT_ACCT_NO") String mgt_acct_no) {
        return cardServiceImpl.findByMgtAcctNo(mgt_acct_no);
    }

    @GetMapping("/result/cards")
    public String getResultByTxAgncCd(@RequestParam("type") String txAgncCd) {
        return ResultGenerator.genSuccessResult(cardServiceImpl.findByTxAgncCd(txAgncCd)).toString();
    }

    @GetMapping("/config")
    public Map<String, String> getConfig(){
        Map<String, String> keyword = new HashMap<>();
        keyword.put("env",env);
        keyword.put("module",module);
        return keyword;
    }

}
