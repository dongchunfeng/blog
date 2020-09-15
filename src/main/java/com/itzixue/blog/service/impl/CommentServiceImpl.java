package com.itzixue.blog.service.impl;

import com.itzixue.blog.dao.CommentRepository;
import com.itzixue.blog.entity.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itzixue.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mr.Dong
 * @date 2020/5/25 10:57
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentIsNull(blogId, sort);
        return eachComment(comments);
    }

    @Override
    public Page<Comment> pageCommentByBlogId(Long blogId,Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return commentRepository.findByBlogIdAndParentCommentIsNull(blogId,pageable);
    }

    @Override
    public Page<Comment> pageCommentByAll(Pageable pageable) {
        Page<Comment> page = commentRepository.findAllByParentCommentIsNullAndBlogIsNull(pageable);
        List<Comment> commentList = eachComment(page.getContent());
        return new PageImpl<>(commentList,pageable,page.getTotalElements());
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            //子级
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            //父级
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public Comment listByParentCommentId(Long parentCommentId) {
        return commentRepository.findCommentByParentCommentId(parentCommentId);
    }

    @Override
    public List<Comment> listComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comment listByCommentId(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Override
    public Page<Comment> listComment(Pageable pageable) {
        return commentRepository.findAllByBlogIdIsNull(pageable);
    }

    @Override
    public void deleteCommentByCommentId(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    @Override
    public int updateParentCommentIdIsNull(Long commentId) {
        return commentRepository.updateParentCommentIdIsNull(commentId);
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }

}
