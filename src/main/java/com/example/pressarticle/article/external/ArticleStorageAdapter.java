package com.example.pressarticle.article.external;

import com.example.pressarticle.article.domain.model.Article;
import com.example.pressarticle.article.domain.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ArticleStorageAdapter implements ArticleRepository {

//    public ArticleStorageAdapter(ArticleMapper mapper) {
//
//    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Article> findArticleByDescribe(String text, String titleText) {
        return null;
    }

    @Override
    public Article save(Article entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
