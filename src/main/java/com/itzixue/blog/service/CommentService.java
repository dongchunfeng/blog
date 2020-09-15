package com.itzixue.blog.service;

import com.itzixue.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/25 10:56
 **/
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Page<Comment> pageCommentByBlogId(Long blogId,Integer size);

    Page<Comment> pageCommentByAll(Pageable pageable);

    Comment saveComment(Comment comment);

    Comment listByParentCommentId(Long parentCommentId);

    List<Comment> listComment();

    Comment listByCommentId(Long commentId);

    Page<Comment> listComment(Pageable pageable);

    void deleteCommentByCommentId(Long commentId);

    int updateParentCommentIdIsNull(Long commentId);

}
