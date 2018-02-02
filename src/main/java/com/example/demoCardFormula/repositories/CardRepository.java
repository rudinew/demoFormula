package com.example.demoCardFormula.repositories;



import com.example.demoCardFormula.domain.Card;
import com.example.demoCardFormula.domain.Document;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Document findByDocument(Document document);

    List<Card> findAll();



}
