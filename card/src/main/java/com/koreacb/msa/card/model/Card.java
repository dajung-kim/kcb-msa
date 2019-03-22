package com.koreacb.msa.card.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    private @Id String mgt_acct_no;
    private String st_dt;
    private String end_dt;
    private String card_stat_cd;
    private String crdt_yn;
    private long tot_mlt_amt;
    private String tx_agnc_cd;
}
