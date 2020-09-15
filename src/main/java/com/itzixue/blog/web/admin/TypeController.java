package com.itzixue.blog.web.admin;

import com.itzixue.blog.entity.Type;
import com.itzixue.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Mr.Dong
 * @date 2020/5/21 19:55
 **/
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 前往分类列表
     *
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 前往分类编辑页面
     *
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getTypeById(id));
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String types(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            bindingResult.rejectValue("name", "nameError", "不能添加重复的分类");
            return "admin/types-input";
        }
        if (bindingResult.hasErrors()) {
            return "admin/types-input";
        }
        Type t = null;
        if (type.getId() != null) {
            t = typeService.updateType(type.getId(), type);
        } else {
            t = typeService.addType(type);
        }

        if (t == null) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        }
        redirectAttributes.addFlashAttribute("message", "操作成功");
        return "redirect:/admin/types";
    }

}
