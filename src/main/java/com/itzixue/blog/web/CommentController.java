package com.itzixue.blog.web;

import com.itzixue.blog.entity.Comment;
import com.itzixue.blog.entity.Message;
import com.itzixue.blog.entity.User;
import com.itzixue.blog.exception.NotFoundException;
import com.itzixue.blog.service.BlogService;
import com.itzixue.blog.service.CommentService;
import com.itzixue.blog.util.CookieUtil;
import com.itzixue.blog.util.MyBeanUtils;
import com.itzixue.blog.util.SendEmail;
import com.itzixue.blog.util.SendEmailBlog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/25 10:51
 **/
@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${avatar.img}")
    private String avatar;

    @GetMapping("/{blogId}")
    public String comments(@PathVariable Long blogId, Model model,HttpSession session) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        MyBeanUtils.isAdminComment(session, model);
        return "blog :: commentList";
    }



    public void isUser(HttpSession httpSession, Comment comment) {
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
    }

    @PostMapping("/deleteCom")
    public String deleteCom(@RequestParam Long commentId,@RequestParam Long blogId, HttpSession session, RedirectAttributes attributes) {
        if (!StringUtils.isEmpty(commentId)) {
            commentService.deleteCommentByCommentId(commentId);
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/comments/" + blogId;
    }


    @PostMapping
    public String post(Comment comment, HttpSession httpSession, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        if (comment != null && comment.getEmail()!=null) {
            Long blogId = comment.getBlog().getId();
            comment.setBlog(blogService.getBlogById(blogId));
            isUser(httpSession,comment);
            if(comment.getParentComment()==null){
                return "redirect:/comments/" + comment.getBlog().getId();
            }
            //发送邮件
            if (comment.getParentComment().getId() != -1) {
                Long id = comment.getParentComment().getId();
                Comment comment1 = commentService.listByCommentId(id);
                if (comment1.getEmail() != null && comment1.getEmail().matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {

                        new SendEmailBlog(comment.getContent(),comment1).start();

                } else {
                    redirectAttributes.addFlashAttribute("message", "发送邮箱验证错误或者为空");
                    throw new RuntimeException();
                }
            }
            commentService.saveComment(comment);
            //点击评论后将当前用户存入cookie中
            CookieUtil.set(response,"comment_email",comment.getEmail(),   60 * 60 * 24 * 365);
            CookieUtil.set(response,"comment_name",comment.getNickname(),   60 * 60 * 24 * 365);
        } else {
            throw new NotFoundException("评论找不到");
        }
        return "redirect:/comments/" + comment.getBlog().getId();
    }

}
