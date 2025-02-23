package com.sintad.blog.service.impl;

import com.sintad.blog.dto.ArticleDto;
import com.sintad.blog.entity.ArticleEntity;
import com.sintad.blog.mapper.ArticleMapper;
import com.sintad.blog.repository.ArticleRepository;
import com.sintad.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleDto findById(Long id) {
        ArticleEntity entity = articleRepository.findById(id).orElse(null);
        return articleMapper.toDto(entity);
    }

    @Override
    public List<ArticleDto> findAll() {
        List<ArticleEntity> listEntity = articleRepository.findAll();
        return articleMapper.toDto(listEntity);
    }

}