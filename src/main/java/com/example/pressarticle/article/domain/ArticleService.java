package com.example.pressarticle.article.domain;

import com.example.pressarticle.article.domain.model.Article;
import com.example.pressarticle.article.domain.repository.ArticleRepository;
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

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> sortAllArticleTransfer(
            int pageNumber,
            int pageSize,
            String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Page<Article> quotations = articleRepository.findAll(pageable);
        if (quotations.hasContent()) {
            return quotations.getContent();
        } else {
            return Collections.emptyList();
        }
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public List<Article> findArticlesByDescriptionOrTitle(String description, String title) {
        return articleRepository.findArticleByDescriptionOrTitle(description, title);
    }

    public Article addArticleTransfer(Article article) {
        return articleRepository.save(article);
    }

    public Article updateArticleTransfer(
            Long id,
            Article toUpdate
    ) {
        toUpdate.setId(id);
        return articleRepository.save(toUpdate);
    }

    public void deleteArticleTransfer(long id) {
        articleRepository.deleteById(id);
    }
}