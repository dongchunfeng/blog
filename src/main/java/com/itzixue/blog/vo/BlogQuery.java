package com.itzixue.blog.vo;

import lombok.Data;

/**
 * @author Mr.Dong
 * @date 2020/5/22 17:17
 **/
@Data
public class BlogQuery {

    private String title;
    private boolean recommend;
    private Long typeId;

}
