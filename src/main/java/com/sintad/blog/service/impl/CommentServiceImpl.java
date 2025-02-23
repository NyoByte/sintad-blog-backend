package com.sintad.blog.service.impl;

import com.sintad.blog.dto.CommentDto;
import com.sintad.blog.entity.CommentEntity;
import com.sintad.blog.mapper.CommentMapper;
import com.sintad.blog.repository.CommentRepository;
import com.sintad.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public CommentDto addComment(CommentDto comment) {
        CommentEntity newComment = commentRepository.save(commentMapper.toEntity(comment));
        CommentDto savedComment = commentMapper.toDto(newComment);
        // Publicar el comentario en el canal WebSocket
        messagingTemplate.convertAndSend("/topic/comments", savedComment);
        return savedComment;
    }

}