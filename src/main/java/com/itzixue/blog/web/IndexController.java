package com.itzixue.blog.web;

import com.itzixue.blog.entity.Blog;
import com.itzixue.blog.entity.User;
import com.itzixue.blog.exception.NotFoundException;
import com.itzixue.blog.service.BlogService;
import com.itzixue.blog.service.TagService;
import com.itzixue.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/20 16:43
 **/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Value("${types.size}")
    private Integer typesSize;
    @Value("${tags.size}")
    private Integer tagsSize;
    @Value("${recommend.size}")
    private Integer recommendSize;

    @GetMapping("/")
    public String index(@PageableDefault(sort ={"views"},size = 8,direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(typesSize));
        model.addAttribute("tags",tagService.listTagTop(tagsSize));
        model.addAttribute("recommendBlogs",blogService.listHotBlog(recommendSize));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(sort ={"views"},size = 8,direction = Sort.Direction.DESC)
                                     Pageable pageable,@RequestParam String query,Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blogs/{id}")
    public String blogs(@PathVariable Long id, Model model,HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        Blog blog = blogService.getAndConvert(id);
        model.addAttribute("blog",blog);
        model.addAttribute("username",user!=null);
        return "blog";
    }


    @PostMapping("/blogs/views")
    @ResponseBody
    public Integer incrViews(@RequestParam Long blogId ,HttpServletRequest request){
        blogService.updateViews(blogId);
        Blog blog = blogService.getBlogById(blogId);
        return blog.getViews();
    }

    @GetMapping("/footer/newBlogs")
    public String newBlogs(Model model){
        List<Blog> blogs = blogService.listTopBlog(3);
        model.addAttribute("newBlogs",blogs);
        return "_fragments :: newBlogsList";
    }

}
