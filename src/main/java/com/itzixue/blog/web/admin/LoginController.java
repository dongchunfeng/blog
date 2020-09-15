package com.itzixue.blog.web.admin;

import com.itzixue.blog.entity.User;
import com.itzixue.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Mr.Dong
 * @date 2020/5/21 13:20
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String index() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            httpSession.setAttribute("user", user);
            return "admin/index";
        }
        redirectAttributes.addFlashAttribute("message", "用户名和密码错误");
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }


}
