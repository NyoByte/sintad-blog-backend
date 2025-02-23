package com.sintad.blog.controller;

import com.sintad.blog.dto.CommentDto;
import com.sintad.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class CommentWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final CommentService commentService;

    @MessageMapping("/comment.add")
    public void sendComment(CommentDto comment) {
        // Enviar el comentario a todos los suscriptores de la cola /topic/comments
        comment.setCreatedAt(LocalDateTime.now());
        CommentDto response = commentService.addComment(comment);
        messagingTemplate.convertAndSend("/topic/comments", response);
    }

}