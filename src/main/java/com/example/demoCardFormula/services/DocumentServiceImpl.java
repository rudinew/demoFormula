package com.example.demoCardFormula.services;


import com.example.demoCardFormula.domain.Card;
import com.example.demoCardFormula.domain.Document;
import com.example.demoCardFormula.repositories.CardRepository;
import com.example.demoCardFormula.repositories.DocumentRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class DocumentServiceImpl implements DocumentService {



    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    CardRepository cardRepository;


    @Override
    public Document getDocumentByName(String cName) {
        return documentRepository.findByName(cName);
    }

    @Override
    public Document getDocumentByOne(Long cId) {
        return documentRepository.findOne(cId);
    }

    @Override
    public Card getCardByOne(Long cId) {
        return cardRepository.findOne(cId);
    }


    /*@Override
    public Page<Document> getDocumentAll(Integer page, Integer size) {
        return documentRepository.findAll(page, size);
    }*/

    @Override
    public List<Document> getDocumentAll() {
        return documentRepository.findAll();
    }


    @Override
    @Transactional
    public void saveDocument(Document document) {
        documentRepository.save(document);
    }

    @Override
    @Transactional
    public void saveAndFlushDocument(Document document) {
        documentRepository.saveAndFlush(document);
    }

    @Override
    @Transactional
    public Document addDocument(Document document) {
        Document documentNew = new Document(document.getName(),
                  document.getContragentName(),
                 document.getDtConfirm(), document.getDescription()  );

        documentRepository.saveAndFlush(documentNew);
        return documentNew;

    }


    @Override
    @Transactional
    public Document addDocumentAndCards(Document document) {

        List<Card> cards = document.getCards();
        ///doctype - pryhid



        Document documentNew = addDocument(document);
        if (!cards.isEmpty()) {
            for (Card card : cards) {
                documentNew.setCard(card);
            }
        }
        documentRepository.saveAndFlush(documentNew);

        return documentNew;
    }
///???
    @Override
    @Transactional
    public Document addDocumentWithDefaults() {
        Document document = new Document();
        document.setName("1");
        document.setContragentName("Contr");
        document.setDescription("Примітка");
        documentRepository.saveAndFlush(document);


        return document;
    }

    @Override
    @Transactional
    public Document changeDocument(Document document) {
        Document documentEdit = documentRepository.findOne(document.getId());



        documentEdit.setContragentName(document.getContragentName());
        documentEdit.setDtConfirm(document.getDtConfirm());
        documentEdit.setDescription(document.getDescription());

        documentRepository.saveAndFlush(documentEdit);
        return documentEdit;
    }

    @Override
    @Transactional
    public void changeCard(Document document, Card card) {

        //IF ID is NULL then isNew==true!!!!
        if (card.isNew()){
            card.setDocument(document);
            //TODO: ВІК
           // objectDoc.setAge(objectDoc.getAge());
            //System.out.println("vik");
            document.setCard(card);
            cardRepository.saveAndFlush(card);

        }
        else{
            Card cardEdit = cardRepository.findOne(card.getId());
            if (cardEdit != null) {
                cardEdit.setDocument(document);
                cardEdit.setName(card.getName());
                cardEdit.setUnit(card.getUnit());
                cardEdit.setQuantity1(card.getQuantity1());
                cardEdit.setBirthDt(card.getBirthDt());
                //TODO: ВІК
               // objectDocEdit.setBirthDt(objectDoc.getAgeOnDtConfirm());

                cardEdit.setDescription(card.getDescription());
                cardRepository.saveAndFlush(cardEdit);



            }

        }



    }

    @Override
    @Transactional
    public Document changeDocumentAndCards(Document document) {
        Document documentEdit = changeDocument(document);


      //  System.out.println("after change doc");
        List<Card> cards = document.getCards();
        //check if the same rows in DB and Client, DELETE difference
        deleteCardsFromDocument(document);
      //  System.out.println("after delete rows");
        //if not empty received from client rows  then change
        if (!cards.isEmpty()) {
            for (Card card : cards) {
                //System.out.println(objectDoc.getMatObject().getName());
                changeCard(documentEdit, card);
            }
        }
         //повернути змінену дет частину

       // documentRepository.flush();
      //  documentEdit = getDocumentByOne(documentEdit.getId());
        //session flush refresh commit


        return documentEdit;
    }

    @Override
    @Transactional
    public void deleteDocument(Long cId) {
        documentRepository.delete(cId);

    }

    @Override
    @Transactional
    public void deleteCard(Long cId) {
       cardRepository.delete(cId);
    }

    @Override
    @Transactional
    //check if the same rows in DB and Client, DELETE difference
    public void deleteCardsFromDocument(Document document) {
        List<Card> cards = document.getCards();

        Document documentInDB = this.documentRepository.findOne(document.getId());
        List<Card> cardsInDB = documentInDB.getCards();
        if (!cardsInDB.isEmpty()) {
            List<Card> cardsDiff = new ArrayList<>();
            //make the same hashcode
            for (Card itemDB : cardsInDB) {
                for (Card itemClient : cards) {
                    if (itemDB.getId().equals(itemClient.getId())) {
                       cardsDiff.add(itemDB);
                    }
                }
            }
            //subtract(Collection a, Collection b)
            //Returns a new Collection containing a - b.
            cardsDiff = (List<Card>) CollectionUtils.subtract(cardsInDB, cardsDiff);

            //if not empty diff then delete rows
            if (!cardsDiff.isEmpty()) {
                for (Card card : cardsDiff) {
                    // System.out.println("del = " + objectDoc.getId());
                    this.cardRepository.delete(card.getId());
                }
            }
        }

    }




}
