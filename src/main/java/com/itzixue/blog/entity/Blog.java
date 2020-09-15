package com.itzixue.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/20 21:52
 **/
@Entity(name = "t_blog")
@Data
public class Blog {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation = false;
    private boolean shareStatement = false;
    private boolean commentTabled = false;
    private boolean published = false;
    private boolean recommend = false;
    private boolean sticky = false;//是否置顶
    private boolean hideContent = false;
    private String hideContents;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    private String description;


    //维护
    @ManyToOne
    private Type type;

    @Transient
    String tagIds;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public void init() {
        this.tagIds = tagToIds(this.getTags());
    }

    //将List转换为1，2，3
    public String tagToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer s = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    s.append(",");
                } else {
                    flag = true;
                }
                s.append(tag.getId());
            }
            return s.toString();
        } else {
            return tagIds;
        }
    }

}
