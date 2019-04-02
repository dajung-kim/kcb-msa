package com.koreacb.msa.card.dao;

import com.koreacb.msa.card.model.Card;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardDao {
    private final SqlSession sqlSession;

    public CardDao(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    public Card selectCardByMgtAcctNo(String mgtAcctNo){
        return this.sqlSession.selectOne("selectCardByMgtAcctNo", mgtAcctNo);
    }

    public List<Card> selectCardsByTxAgncCd(String txAgncCd){
        return this.sqlSession.selectList("selectCardsByTxAgncCd", txAgncCd);
    }
}
