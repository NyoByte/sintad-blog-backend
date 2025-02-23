package com.sintad.blog.mapper;

import com.sintad.blog.dto.ArticleDto;
import com.sintad.blog.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleMapper {

    private final UserMapper userMapper;
    private final CommentMapper commentMapper;

    public ArticleDto toDto(ArticleEntity entity) {
        if (entity == null) {
            return null;
        }
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setAuthor(userMapper.toDto(entity.getAuthor()));
        dto.setViews(entity.getViews());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setComments(commentMapper.toDto(entity.getComments()));
        return dto;
    }

    public List<ArticleDto> toDto(List<ArticleEntity> entities) {
        if (entities.isEmpty()) {
            return null;
        }
        List<ArticleDto> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            dtos.add(toDto(entity));
        });
        return dtos;
    }

    public ArticleEntity toEntity(ArticleDto dto) {
        if (dto == null) {
            return null;
        }
        ArticleEntity entity = new ArticleEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setAuthor(userMapper.toEntity(dto.getAuthor()));
        entity.setViews(dto.getViews());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setComments(new ArrayList<>());
        return entity;
    }

}