package com.example.pressarticle.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

//    @Query("select a from Article a order by a.dataPublication desc")
//    List<Article> findArticleByQuery();

    List<Article> findArticleById(Long id);

    //@Query("select a from Article a where a.describeText like ?1")
   // @Query(nativeQuery = true, value = "SELECT * FROM article WHERE describe_Text = :text OR title_Text = :titleText LIKE %?1%")
    @Query(nativeQuery = true, value = "SELECT * FROM article WHERE describe_Text LIKE %?1% OR title_Text LIKE %?2%")
    List<Article> findArticleByDescribe(String text, String titleText);
}
