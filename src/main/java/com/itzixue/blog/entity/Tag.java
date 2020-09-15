package com.itzixue.blog.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "t_tag")
@Data
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "标签名称不能为空")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

}
