package com.project.movie.dto;

import lombok.Data;

@Data
public class MyCommentsDTO {
    private String seq;
    private String title;
    private String content;
    private String type;
    private String regdate;
}
