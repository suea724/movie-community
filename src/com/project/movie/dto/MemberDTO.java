package com.project.movie.dto;

import lombok.Data;

@Data
public class MemberDTO {

    private String id;
    private String name;
    private String nickname;
    private String password;
    private String tel;
    private String picture;
    private String joindate;
    private int grade;
    private int postCnt;
    private int commentCnt;
}
