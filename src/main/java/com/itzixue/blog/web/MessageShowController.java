package com.itzixue.blog.web;

import com.itzixue.blog.entity.Comment;
import com.itzixue.blog.entity.Message;
import com.itzixue.blog.entity.User;
import com.itzixue.blog.service.CommentService;
import com.itzixue.blog.util.CookieUtil;
import com.itzixue.blog.util.MyBeanUtils;
import com.itzixue.blog.util.SendEmail;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/26 16:05
 **/
@Controller
@RequestMapping("/messages")
@Slf4j
public class MessageShowController {

    @Autowired
    private CommentService commentService;
    @Value("${avatar.img}")
    private String avatar;

    @GetMapping
    public String ms(@PageableDefault(sort = {"createTime"}, size = 5, direction = Sort.Direction.DESC)
                             Pageable pageable, Model model,HttpSession session) {
        User user = (User) session.getAttribute("user");
        Page<Comment> comments = commentService.pageCommentByAll(pageable);
        model.addAttribute("pages", comments);
        model.addAttribute("username",user!=null);
        return "message";
    }

    @RequestMapping("/pages")
    public String message(@PageableDefault(sort = {"createTime"}, size = 10, direction = Sort.Direction.DESC)
                                  Pageable pageable, Model model, @ModelAttribute("message") String message, HttpSession session) {
        Page<Comment> comments = commentService.pageCommentByAll(pageable);
        model.addAttribute("pages", comments);
        model.addAttribute("message", message);
        MyBeanUtils.isAdminComment(session, model);
        return "message :: commentList";
    }


    @PostMapping
    public String post(Message message, HttpSession httpSession, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(message, comment);
        User user = isUser(httpSession, comment);
        //发送邮件
        if (message.getParentComment().getId() != -1) {
            Long id = message.getParentComment().getId();
            Comment comment1 = commentService.listByCommentId(id);
            if (comment1.getEmail() != null && comment1.getEmail().matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
                new SendEmail(message.getContent(), comment1).start();
            } else {
                log.error("发送邮箱验证错误或者为空");
                redirectAttributes.addFlashAttribute("message", "发送邮箱验证错误或者为空");
                throw new RuntimeException();
            }
        }
        commentService.saveComment(comment);
        //点击评论后将当前用户存入cookie中
        CookieUtil.set(response, "comment_email", comment.getEmail(), 60 * 60 * 24 * 365);
        CookieUtil.set(response, "comment_name", comment.getNickname(), 60 * 60 * 24 * 365);
        return "redirect:/messages/pages";
    }

    @PostMapping("/deleteCom")
    public String deleteCom(@RequestParam Long commentId, HttpSession session, RedirectAttributes attributes) {
        if (!StringUtils.isEmpty(commentId)) {
            commentService.deleteCommentByCommentId(commentId);
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/messages/pages";
    }

    public User isUser(HttpSession httpSession, Comment comment) {
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        return user;
    }
}
