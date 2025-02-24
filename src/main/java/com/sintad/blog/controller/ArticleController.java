package com.sintad.blog.controller;

import com.sintad.blog.dto.ArticleDto;
import com.sintad.blog.service.ArticleService;
import com.sintad.blog.util.ApiResponse;
import com.sintad.blog.util.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ApiResponse<PagedResponse<ArticleDto>> getAllArticles(@RequestParam(defaultValue = "0") int page) {
        PagedResponse<ArticleDto> response = articleService.findAll(page);
        return ApiResponse.success(response);
    }

    @GetMapping("/popular")
    public ApiResponse<List<ArticleDto>> getPopularArticles() {
        List<ArticleDto> response = articleService.findPopularArticles();
        return ApiResponse.success(response);
    }

    @GetMapping("/owned")
    public ApiResponse<List<ArticleDto>> getMyArticles(@RequestHeader("Authorization") String token) {
        List<ArticleDto> response = articleService.findMyArticles(token);
        return ApiResponse.success(response);
    }

    //CRUD
    @GetMapping("/{id}")
    public ApiResponse<ArticleDto> getArticleById(@PathVariable Long id) {
        ArticleDto response = articleService.findById(id);
        return ApiResponse.success(response);
    }

    @PostMapping
    public ApiResponse<ArticleDto> createArticle(@RequestBody ArticleDto articleDto) {
        ArticleDto response = articleService.create(articleDto);
        return ApiResponse.success(response);
    }

    @PutMapping("/{id}")
    public ApiResponse<ArticleDto> updateArticle(@PathVariable Long id, @RequestBody ArticleDto articleDto) {
        ArticleDto response = articleService.update(id, articleDto);
        return ApiResponse.success(response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteArticle(@PathVariable Long id) {
        articleService.delete(id);
        return ApiResponse.success(null, "Article deleted successfully");
    }

}