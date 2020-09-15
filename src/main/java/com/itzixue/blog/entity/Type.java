package com.itzixue.blog.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/20 22:28
 **/
@Entity(name = "t_type")
@Data
public class Type {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    //被维护
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

}
