package com.sintad.blog.repository;

import com.sintad.blog.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    @NonNull
    Page<ArticleEntity> findAll(@NonNull Pageable pageable);

    List<ArticleEntity> findByViewsGreaterThanEqualOrderByViewsDesc(int views);

    List<ArticleEntity> findByAuthorUsername(String username);

}