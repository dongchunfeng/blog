package com.itzixue.blog.web.admin;

import com.itzixue.blog.entity.Blog;
import com.itzixue.blog.entity.Type;
import com.itzixue.blog.entity.User;
import com.itzixue.blog.service.BlogService;
import com.itzixue.blog.service.TagService;
import com.itzixue.blog.service.TypeService;
import com.itzixue.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/21 18:10
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    private static final String INPUT = "admin/blogs-input";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                               Pageable pageable, BlogQuery blog, Model model) {
        List<Type> typeList = typeService.findAll();
        model.addAttribute("types", typeList);
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                 Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        //thymeleaf 替换id为blogList的表单
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTagAndType(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    private void setTagAndType(Model model) {
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.findAll());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTagAndType(model);
        Blog blog = blogService.getBlogById(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @DeleteMapping("/blogs/{id}/delete")
    public String delBlog(@PathVariable Long id, RedirectAttributes redirectAttributes){
        blogService.delBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }


    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession httpSession) {

        blog.setUser((User) httpSession.getAttribute("user"));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog blog1;
        if(blog.getId()==null){
            blog1 = blogService.addBlog(blog);
        }else{
            blog1 = blogService.updateBlog(blog.getId(),blog);
        }
        if (blog1 == null) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "操作成功");
        }

        return REDIRECT_LIST;
    }


}
