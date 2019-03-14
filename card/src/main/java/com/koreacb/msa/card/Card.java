package com.koreacb.msa.card;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Card {

    private @Id @GeneratedValue Long id;

    private long usrSeqno;
    private String name;
    private String type;
    private long limitAmount;
    private long useAmount;
    private String openDate;

    Card(long usrSeqno, String name, String type, long limitAmount, long useAmount, String openDate) {
        this.usrSeqno = usrSeqno;
        this.name = name;
        this.type = type;
        this.limitAmount = limitAmount;
        this.useAmount = useAmount;
        this.openDate = openDate;
    }
}
