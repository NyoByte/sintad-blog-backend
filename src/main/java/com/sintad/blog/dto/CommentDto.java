package com.sintad.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String content;
    private UserDto user;
    private Long articleId;
    private LocalDateTime createdAt;

}