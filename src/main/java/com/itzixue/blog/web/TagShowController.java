package com.itzixue.blog.web;

import com.itzixue.blog.entity.Blog;
import com.itzixue.blog.entity.Tag;
import com.itzixue.blog.entity.Type;
import com.itzixue.blog.service.BlogService;
import com.itzixue.blog.service.TagService;
import com.itzixue.blog.service.TypeService;
import com.itzixue.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/26 0:06
 **/
@Controller
@RequestMapping("/tags")
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/{id}")
    public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(10000);
        if(id ==-1){
            id = tags.get(0).getId();
        }
        Page<Blog> blogs = blogService.listBlog(id,pageable);
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogs);
        model.addAttribute("activeTypeId",id);
        return "tags";
    }

}
