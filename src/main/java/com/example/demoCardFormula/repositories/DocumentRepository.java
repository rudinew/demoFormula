package com.example.demoCardFormula.repositories;


import com.example.demoCardFormula.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findByName(String cName);

    Document  findById(Long id);

    List<Document> findAll();


}
