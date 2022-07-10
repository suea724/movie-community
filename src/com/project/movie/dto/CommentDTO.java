package com.project.movie.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private String seq;
    private String content;
    private String regdate;
    private String pseq;
    private String id;

    private String nickname;
}
