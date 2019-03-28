package com.koreacb.msa.card.service.impl;

import com.koreacb.msa.card.dao.CardDao;
import com.koreacb.msa.card.model.Card;
import com.koreacb.msa.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("cardServiceImpl")
public class CardServiceImpl implements CardService {

    @Autowired
    private CardDao cardDao;

    @Override
    public Card findByMgtAcctNo(String mgtAcctNo) {
        return cardDao.selectCardByMgtAcctNo(mgtAcctNo);
    }

    @Override
    public List<Card> findByTxAgncCd(String txAgncCd) {
        List<Card> cards = cardDao.selectCardByTxAgncCd(txAgncCd);
        return cards.stream().map(s -> new Card(s.getMgt_acct_no(), s.getSt_dt().substring(0, 4) + "년" + s.getSt_dt().substring(4, 6) + "월" + s.getSt_dt().substring(6, 8) + "일", s.getEnd_dt().substring(0, 4) + "년" + s.getEnd_dt().substring(4, 6) + "월" + s.getEnd_dt().substring(6, 8) + "일", (s.getCard_stat_cd() == "01") ? "정상" : (s.getCard_stat_cd() == "03") ? "종료" : "에러", s.getCrdt_yn(), s.getTot_mlt_amt(), s.getTx_agnc_cd())).collect(Collectors.toList());
        //return stream;


        //return cardListMapper.selectCardByTxAgncCd(txAgncCd);
    }
}
