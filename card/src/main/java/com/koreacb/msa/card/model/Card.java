package com.koreacb.msa.card.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    private @Id String mgtAcctNo;
    private String stDt;
    private String endDt;
    private String cardStatCd;
    private String crdtYn;
    private long totMltAmt;
    private String txAgncCd;
}
