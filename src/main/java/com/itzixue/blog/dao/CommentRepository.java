package com.itzixue.blog.dao;

import com.itzixue.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/25 11:03
 **/
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentIsNull(Long blogId, Sort sort);

    List<Comment> findAllByParentCommentIsNull();

    Page<Comment> findByBlogIdAndParentCommentIsNull(Long blogId, Pageable pageable);

    Page<Comment> findAllByParentCommentIsNullAndBlogIsNull(Pageable pageable);

    Comment findCommentByParentCommentId(Long parentCommentId);

    Page<Comment> findAllByBlogIdIsNull(Pageable pageable);

    @Modifying
    @Query("update t_comment t set t.parentComment.id = null where t.id = ?1")
    int updateParentCommentIdIsNull(Long commentId);


}
