package com.sintad.blog;

import com.sintad.blog.entity.ArticleEntity;
import com.sintad.blog.entity.CommentEntity;
import com.sintad.blog.entity.UserEntity;
import com.sintad.blog.repository.ArticleRepository;
import com.sintad.blog.repository.CommentRepository;
import com.sintad.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public DataLoader(UserRepository userRepository, ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        UserEntity user1 = UserEntity.builder()
                .username("user1")
                .password("$2a$10$L1sKeHIFp7GCGbGBnRBiTeSqL4AE0E4oazf87ZpNaFo.CQsfv4EC6")
                .fullname("Usuario de Prueba 1")
                .build();
        UserEntity user2 = UserEntity.builder()
                .username("user2")
                .password("$2a$10$X7yyje1E8VQ9kg.yQEXI8uqkDCZpi0EmU1pMdHgCn8uRxs1xkDX3a")
                .fullname("Usuario de Prueba 2")
                .build();
        userRepository.saveAll(List.of(user1, user2));

        ArticleEntity article = ArticleEntity.builder()
                .title("Como crear un articulo famoso")
                .content("Lorem ipsum dolor sit amet consectetur adipiscing, elit faucibus metus parturient nulla laoreet ridiculus, facilisi magna quam ad at. Porttitor ad venenatis at dui facilisi varius vitae orci est, penatibus a nisi nulla facilisis taciti mauris quam litora faucibus, leo volutpat diam cursus porta inceptos integer mattis. Ridiculus turpis non pretium faucibus arcu fermentum a, aliquam vivamus sodales egestas nunc aptent, rutrum pellentesque ornare cursus cras lectus. " +
                        "Lorem ipsum dolor sit amet consectetur adipiscing, elit faucibus metus parturient nulla laoreet ridiculus, facilisi magna quam ad at. Porttitor ad venenatis at dui facilisi varius vitae orci est, penatibus a nisi nulla facilisis taciti mauris quam litora faucibus, leo volutpat diam cursus porta inceptos integer mattis. Ridiculus turpis non pretium faucibus arcu fermentum a, aliquam vivamus sodales egestas nunc aptent, rutrum pellentesque ornare cursus cras lectus.")
                .author(user1)
                .views(5)
                .build();
        articleRepository.save(article);

        CommentEntity comment = CommentEntity.builder()
                .content("Eso suena muy guay")
                .article(article)
                .user(user2)
                .build();
        commentRepository.save(comment);
    }

}