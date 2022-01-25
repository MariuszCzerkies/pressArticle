package com.example.pressarticle.article;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {

        List<Article> articleList = List.of(
                new Article(7L, "Programing Language Java", LocalDate.of(2021,1,23), "ProgramingFuture", "Paul Martin", new Timestamp(2007, 11, 23, 0, 0, 0, 0)),
                new Article(1L, "Programing Language C++", LocalDate.of(2020,4,27), "ProgramingFuture", "Paul Martin", new Timestamp(2007, 11, 23, 0, 0, 0, 0)),
                new Article(2L, "Programing Language C#", LocalDate.of(2023,5,17), "ProgramingFuture", "Paul Martin", new Timestamp(2007, 11, 23, 0, 0, 0, 0)),
                new Article(3L, "Programing Language Python", LocalDate.of(2024,7,11), "ProgramingFuture", "Paul Martin", new Timestamp(2007, 11, 23, 0, 0, 0, 0)));

       List<Article> sortedArticle = articleList.stream()
                .sorted(Comparator.comparing(Article::getDataPublication))
                .collect(Collectors.toList());

        sortedArticle.forEach(System.out::println);



//        List<Article> articleList = List.of(
//                new Article(1L, "NewWorld", LocalDate.of(1022,9,17), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
//                new Article(2L, "Machines", LocalDate.of(2015,10,11), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
//                new Article(3L, "Robots", LocalDate.of(2014,8,21), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
//                new Article(4L, "Electrical", LocalDate.of(2012,7,8), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
//                new Article(5L, "NewWorld", LocalDate.of(2010,9,17), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
//                new Article(6L, "NewWorld", LocalDate.of(2011,9,17), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));
//


    }
}
