package com.sintad.blog.service.impl;

import com.sintad.blog.dto.ArticleDto;
import com.sintad.blog.entity.ArticleEntity;
import com.sintad.blog.mapper.ArticleMapper;
import com.sintad.blog.repository.ArticleRepository;
import com.sintad.blog.service.ArticleService;
import com.sintad.blog.util.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PagedResponse<ArticleDto> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdAt"));
        Page<ArticleEntity> pagedResult = articleRepository.findAll(pageable);
        List<ArticleDto> articles = pagedResult.getContent()
                .stream()
                .map(articleMapper::toDto)
                .toList();
        return new PagedResponse<>(articles, pagedResult.getTotalElements());
    }

    @Override
    @Cacheable(value = "popularArticles", key = "'popular'", unless = "#result == null || #result.isEmpty()")
    public List<ArticleDto> findPopularArticles() {
        List<ArticleEntity> listPopularEntity = articleRepository.findByViewsGreaterThanEqualOrderByViewsDesc(5);
        return articleMapper.toDto(listPopularEntity);
    }

}