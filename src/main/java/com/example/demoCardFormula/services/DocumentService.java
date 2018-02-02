package com.example.demoCardFormula.services;

import com.example.demoCardFormula.domain.Card;
import com.example.demoCardFormula.domain.Document;
import org.joda.time.LocalDateTime;

import java.util.List;


/**
 *
 */
public interface DocumentService {


    Document getDocumentByName(String cName);
    Document getDocumentByOne(Long cId);


    List<Document> getDocumentAll();
    void saveDocument(Document document);
    void saveAndFlushDocument(Document document);
    Document addDocument(Document document);

    Document addDocumentAndCards(Document document);
    Document addDocumentWithDefaults();
    Document changeDocument(Document document);
    void changeCard(Document document, Card card);
    Document changeDocumentAndCards(Document document);
    void deleteDocument(Long cId);
    void deleteCard(Long cId);
    void deleteCardsFromDocument(Document document);
    Card getCardByOne(Long cId);



    /*
    List<Document> findAll();

    https://github.com/khoubyari/spring-boot-rest-example/blob/master/src/main/java/com/khoubyari/example/service/HotelService.java
     //http://goo.gl/7fxvVf
    public Page<Hotel> getAllHotels(Integer page, Integer size) {
        Page pageOfHotels = hotelRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("Khoubyari.HotelService.getAll.largePayload");
        }
        return pageOfHotels;
    }
    */
}
