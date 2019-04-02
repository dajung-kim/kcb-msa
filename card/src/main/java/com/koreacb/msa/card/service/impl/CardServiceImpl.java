package com.koreacb.msa.card.service.impl;

import com.koreacb.msa.card.dao.CardDao;
import com.koreacb.msa.card.model.Card;
import com.koreacb.msa.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardDao cardDao;

    @Override
    public Card findByMgtAcctNo(String mgtAcctNo) {
        return cardDao.selectCardByMgtAcctNo(mgtAcctNo);
    }

    @Override
    public List<Card> findByTxAgncCd(String txAgncCd) {
        List<Card> cards = cardDao.selectCardsByTxAgncCd(txAgncCd);
        return cards.stream().map(s -> new Card(s.getMgtAcctNo(), s.getStDt().substring(0, 4) + "년" + s.getStDt().substring(4, 6) + "월" + s.getStDt().substring(6, 8) + "일", s.getEndDt().substring(0, 4) + "년" + s.getEndDt().substring(4, 6) + "월" + s.getEndDt().substring(6, 8) + "일", s.getCardStatCd(), s.getCrdtYn(), s.getTotMltAmt(), s.getTxAgncCd())).collect(Collectors.toList());
    }
}
