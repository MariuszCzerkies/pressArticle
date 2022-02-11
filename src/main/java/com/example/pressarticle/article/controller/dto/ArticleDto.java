package com.example.pressarticle.article.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ArticleDto {
    private Long id;
    private String describeText;
    private String titleText;
    private LocalDate dataPublication;
    private String nameMagazine;
    private String nameAuthor;
    private Instant dataSaveDocument;

}
