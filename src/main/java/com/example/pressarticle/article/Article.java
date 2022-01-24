package com.example.pressarticle.article;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "article")
//@NamedQuery(name = "Hobbit.findUsingNameQuery", query = "select h from Hobbit h where h.firstName = :firstName and h.lastName = :lastName" )
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String describe;
    private LocalDate dataPublication;
    private String nameMagazine;
    private String nameAuthor;
    private Timestamp dataSaveDocument;

}
