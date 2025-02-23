package com.sintad.blog.controller;

import com.sintad.blog.dto.CommentDto;
import com.sintad.blog.service.CommentService;
import com.sintad.blog.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        CommentDto response = commentService.addComment(commentDto);
        return ApiResponse.success(response);
    }

}