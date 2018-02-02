package com.example.demoCardFormula.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="document")
public class Document extends BaseEntity{
    private static final long serialVersionUID = 1L;

    private String name; //№ документу

    @Column(name = "contragent_name")
    private String contragentName;

    /*
    kk = Hours in 1-24 format
    hh= hours in 1-12 format
    KK= hours in 0-11 format
    HH= hours in 0-23 format
*/
    //дата затвердження
    @Column(name = "dt_confirm")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    //@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd.MM.yyyy HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime dtConfirm ;

    private String description;

    //CARD
    @OneToMany(mappedBy="document" , fetch = FetchType.EAGER)
   // @JsonIgnore
    //when private then in indexOLD.html - Property or field 'beeTaxes' cannot be found on object of type 'com.bee.domain.BeePerson' - maybe not public?
    private List<Card> cards = new ArrayList<Card>();

    public  List<Card> getCards() {
        if (this.cards == null) {
            this.cards = new ArrayList<Card>();
        }
        return this.cards;
    }

    public void setCard(Card card) {
        getCards().add(card);
        card.setDocument(this);
    }

    public int getNrOfCards() {
        return getCards().size();
    }


    public Document() {
        this.dtConfirm = new LocalDateTime();

    }

    public Document(String name, String contragentName, LocalDateTime dtConfirm, String description) {
        this.name = name;
        this.contragentName = contragentName;
        this.dtConfirm = dtConfirm;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDtConfirm() {
        return dtConfirm;
    }

    public void setDtConfirm(LocalDateTime dtConfirm) {
        this.dtConfirm = dtConfirm;
    }

    public String getContragentName() {
        return contragentName;
    }

    public void setContragentName(String contragentName) {
        this.contragentName = contragentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}


