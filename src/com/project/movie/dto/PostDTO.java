package com.project.movie.dto;

import lombok.Data;

@Data
public class PostDTO {

    private String seq;
    private String title;
    private String content;
    private String regdate;
    private String readcount;
    private String good;
    private String bad;
    private String type;
    private String id;

    private String nickname;
    private String commentcount;
}
