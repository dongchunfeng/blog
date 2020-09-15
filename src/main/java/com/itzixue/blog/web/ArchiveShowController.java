package com.itzixue.blog.web;

import com.itzixue.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mr.Dong
 * @date 2020/5/26 14:39
 **/
@Controller
@RequestMapping("/archives")
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String archives(Model model){
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }


}
