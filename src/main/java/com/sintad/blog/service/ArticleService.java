package com.sintad.blog.service;

import com.sintad.blog.dto.ArticleDto;

import java.util.List;

public interface ArticleService {

    ArticleDto findById(Long id);

    List<ArticleDto> findAll();

}