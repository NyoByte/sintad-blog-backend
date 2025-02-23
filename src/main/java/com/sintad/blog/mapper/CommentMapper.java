package com.sintad.blog.mapper;

import com.sintad.blog.dto.CommentDto;
import com.sintad.blog.entity.ArticleEntity;
import com.sintad.blog.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentDto toDto(CommentEntity entity) {
        if (entity == null) {
            return null;
        }
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setUser(userMapper.toDto(entity.getUser()));
        dto.setArticleId(entity.getArticle().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public List<CommentDto> toDto(List<CommentEntity> entities) {
        if (entities.isEmpty()) {
            return null;
        }
        List<CommentDto> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            dtos.add(toDto(entity));
        });
        return dtos;
    }

    public CommentEntity toEntity(CommentDto dto) {
        if (dto == null) {
            return null;
        }
        CommentEntity entity = new CommentEntity();
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());
        entity.setUser(userMapper.toEntity(dto.getUser()));
        entity.setArticle(new ArticleEntity(dto.getArticleId()));
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

}