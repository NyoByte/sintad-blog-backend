package com.sintad.blog.service;

import com.sintad.blog.dto.ArticleDto;
import com.sintad.blog.util.PagedResponse;

import java.util.List;

public interface ArticleService {

    ArticleDto findById(Long id);

    PagedResponse<ArticleDto> findAll(int page);

    List<ArticleDto> findPopularArticles();

    List<ArticleDto> findMyArticles(String tokenWithBearer);

    ArticleDto create(ArticleDto articleDto);

    ArticleDto update(Long id, ArticleDto articleDto);

    void delete(Long id);

}