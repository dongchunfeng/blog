package com.itzixue.blog.dao;

import com.itzixue.blog.entity.Comment;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest extends TestCase {

    @Autowired
    private CommentRepository commentRepository;

    //@Test
    public void test1(){
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        List<Comment> id = commentRepository.findByBlogIdAndParentCommentIsNull(17L, sort);
        for (Comment comment: id) {
            System.out.println(comment.getNickname());
        }
        //assertEquals(1,id.size());
    }

    //@Test
    public void test2(){
        Pageable pageable = PageRequest.of(0,10,Sort.Direction.DESC,"createTime");
        Page<Comment> comments = commentRepository.findAllByBlogIdIsNull(pageable);
        for (Comment c: comments) {
            System.out.println(c.getContent());
        }
    }

}