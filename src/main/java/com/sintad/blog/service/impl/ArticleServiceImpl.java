package com.sintad.blog.service.impl;

import com.sintad.blog.dto.ArticleDto;
import com.sintad.blog.entity.ArticleEntity;
import com.sintad.blog.mapper.ArticleMapper;
import com.sintad.blog.repository.ArticleRepository;
import com.sintad.blog.service.ArticleService;
import com.sintad.blog.util.JwtUtil;
import com.sintad.blog.util.PagedResponse;
import com.sintad.blog.util.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final JwtUtil jwtUtil;

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

    @Override
    public List<ArticleDto> findMyArticles(String tokenWithBearer) {
        String token = tokenWithBearer.substring(7);
        String username = jwtUtil.extractUsername(token);
        List<ArticleEntity> myArticles = articleRepository.findByAuthorUsername(username);
        return articleMapper.toDto(myArticles);
    }

    @Override
    public ArticleDto create(ArticleDto articleDto) {
        ArticleEntity entity = articleMapper.toEntity(articleDto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setViews(0);
        ArticleEntity savedEntity = articleRepository.save(entity);
        return articleMapper.toDto(savedEntity);
    }

    @Override
    public ArticleDto update(Long id, ArticleDto articleDto) {
        ArticleEntity existingEntity = articleRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Article not found", HttpStatus.BAD_REQUEST));
        existingEntity.setTitle(articleDto.getTitle());
        existingEntity.setContent(articleDto.getContent());
        ArticleEntity updatedEntity = articleRepository.save(existingEntity);
        return articleMapper.toDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ValidationException("Article not found", HttpStatus.BAD_REQUEST);
        }
        articleRepository.deleteById(id);
    }

}