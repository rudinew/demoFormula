package com.example.demoCardFormula.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * рядки для Документ
 */
@Entity
@Table(name="card")
public class Card extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "document_id")
    private Document document;


    String name;

    private double quantity1;

    String unit;


    //дата народження
    @Column(name = "birth_dt")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
   // @DateTimeFormat(pattern = "dd.MM.yyyy")
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd.MM.yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    //@JsonFormat(shape = JsonFormat.Shape.STRING) //31.01.2018 return Age in DAYS
    @JsonIgnore
    private LocalDate birthDt ;

    private String description;
    /*вік на дату документа Document.dt_confirm */
 //,  insert="false" update="false"
    @Formula("(select IFNULL(DATEDIFF(Document.dt_confirm , birth_dt), 0) from Document where Document.id = document_id )")
    private int ageOnDtConfirm;

    public Card() {
    }

    public Card(Document document, String name, double quantity1, String unit, LocalDate birthDt, String description) {
        this.document = document;
        this.name = name;
        this.quantity1 = quantity1;
        this.unit = unit;
        this.birthDt = birthDt;
        this.description = description;
    }

    //:TODO ВІК
    /*@Transient будет означать, что поле не будет персистентным, т.е. не будет сохраняться в БД.
    И соответственно не будет заполняться значением при получении объекта из БД*/
    @Transient
    public int getAgeOnDtConfirm() {
        return ageOnDtConfirm;
    }


    //Вік на будь-яку дату dateTo
    public int getAgeOnDate(LocalDate dateTo){

        // Get days between the start date and end date.
        int days = Days.daysBetween((this.getBirthDt()==null?dateTo:this.getBirthDt()), dateTo).getDays();
        return days;

    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity1() {
        return quantity1;
    }

    public void setQuantity1(double quantity1) {
        this.quantity1 = quantity1;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getBirthDt() {
        return birthDt;
    }

    public void setBirthDt(LocalDate birthDt) {
        this.birthDt = birthDt;
    }

    public void setBirthDt(int ageOnDtConfirm) {
        if (this.document.getDtConfirm() != null){
            LocalDate dateTo = this.document.getDtConfirm().toLocalDate();
            this.birthDt = dateTo.minusDays(ageOnDtConfirm);
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
