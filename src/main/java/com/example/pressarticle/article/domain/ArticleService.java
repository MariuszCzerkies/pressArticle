package com.example.pressarticle.article.domain;

import com.example.pressarticle.article.domain.model.Article;
import com.example.pressarticle.article.repository.JpaArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticleService {

    private JpaArticleRepository jpaArticleRepository;

    public ArticleService(JpaArticleRepository jpaArticleRepository) {
        this.jpaArticleRepository = jpaArticleRepository;
    }

    public List<Article> sortAllArticleTransfer(
            int pageNumber,
            int pageSize,
            String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Page<Article> quotations = jpaArticleRepository.findAll(pageable);
        if (quotations.hasContent()) {
            return quotations.getContent();
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Article> articleIdTransfer(long id) {
        return jpaArticleRepository.findById(id);
    }

    public List<Article> articleDescribeTransfer(String text, String titleText) {
        return jpaArticleRepository.findArticleByDescribe(text, titleText);
    }

    public Article addArticleTransfer(Article article) {
        return jpaArticleRepository.save(article);
    }

    public Article updateArticleTransfer(
            Long id,
            Article toUpdate
    ) {
        toUpdate.setId(id);
        return jpaArticleRepository.save(toUpdate);
    }

    public void deleteArticleTransfer(long id) {
        jpaArticleRepository.deleteById(id);
    }
}