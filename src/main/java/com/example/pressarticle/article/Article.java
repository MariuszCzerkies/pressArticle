package com.example.pressarticle.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String describe;
    private LocalDate dataPublication;
    private String nameMagazine;
    private String nameAuthor;
    private Timestamp dataSaveDocument;

    public Article(Long id) {
        this.id = id;
    }
}
