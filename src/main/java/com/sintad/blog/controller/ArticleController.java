package com.sintad.blog.controller;

import com.sintad.blog.dto.ArticleDto;
import com.sintad.blog.service.ArticleService;
import com.sintad.blog.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ApiResponse<List<ArticleDto>> getAllArticles() {
        List<ArticleDto> response = articleService.findAll();
        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<ArticleDto> getArticleById(@PathVariable Long id) {
        ArticleDto response = articleService.findById(id);
        return ApiResponse.success(response);
    }

}