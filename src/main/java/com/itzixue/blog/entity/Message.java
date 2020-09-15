package com.itzixue.blog.entity;

import lombok.Data;

/**
 * @author Mr.Dong
 * @date 2020/5/26 18:56
 **/
@Data
public class Message {

    private Long id;
    private String nickname;
    private String email;
    private String content;
    private Comment parentComment;


}
