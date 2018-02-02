package com.example.demoCardFormula.controllers;


import com.example.demoCardFormula.domain.Document;
import com.example.demoCardFormula.services.DocumentService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller документу На приймання ТМЦ
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/docs")

public class DocReceptionController  {
    @Autowired
    DocumentService documentService;



    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Document> getAllDocument(HttpServletRequest request, HttpServletResponse response) {
        List<Document> list = new ArrayList<>();
        Iterable<Document> documents = this.documentService.getDocumentAll();
        //System.out.println("Document.class.getClassLoader = " + Document.class.getClassLoader());
        documents.forEach(list::add);
        return list;
    }



    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
     public @ResponseBody
    Document getDocument(//@ApiParam(value = "The ID of the Document.", required = true)
                         @PathVariable("id") Long id,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        Document document = this.documentService.getDocumentByOne(id);
        //TODO: delete rows
      /*  System.out.println("from GET");
        for(ObjectDoc item: document.getObjectDocs()){
            //System.out.println(item.getId() + "  " +item.getAgeOnDate(document.getDtConfirm().toLocalDate()));
            System.out.println(item.getId() + "  " +item.getAgeOnDtConfirm());
        }*/
        return document;
    }


    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public Document createDocument(HttpServletRequest request, HttpServletResponse response) {

        return documentService.addDocumentWithDefaults();
    }


     @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Document updateDocument(//@ApiParam(value = "The ID of the existing Document resource.", required = true)
                                   @PathVariable("id") Long id,
                                   @RequestBody Document document,
                                   HttpServletRequest request, HttpServletResponse response) {
        Document documentEdit = this.documentService.getDocumentByOne(document.getId());


            //TODO: delete rows
            documentEdit = documentService.changeDocumentAndCards(document);


            return documentEdit;


    }

    /**
     * /api/docreceptions/{id}
     * DELETE видалення запису
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocument(//@ApiParam(value = "The ID of the existing Document resource.", required = true)
                               @PathVariable("id") Long id, HttpServletRequest request,
                               HttpServletResponse response) {

        this.documentService.deleteDocument(id);
    }

    /**
     * /api/docreceptions/{id}/det/{detId}
     * DELETE видалення запису
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/{id}/det/{detId}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteObjectDoc(//@ApiParam(value = "The ID of the existing ObjectDoc resource.", required = true)
                               @PathVariable("id") Long id,
                               @PathVariable("id") Long detId,
                               HttpServletRequest request,
                               HttpServletResponse response) {
       this.documentService.deleteCard(detId);
    }

    
}
