package com.itzixue.blog.service;

import com.itzixue.blog.entity.Blog;
import com.itzixue.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.Dong
 * @date 2020/5/22 11:46
 **/
public interface BlogService {

    Blog addBlog(Blog blog);

    void delBlog(Long id);

    Blog updateBlog(Long id, Blog blog);

    int updateViews(Long id);

    Blog getBlogById(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Long countBlog();

    Map<String,List<Blog>> archiveBlog();

    List<Blog> listTopBlog(Integer size);

    List<Blog> listHotBlog(Integer size);

    Page<Blog> listBlog(String query, Pageable pageable);

    Blog getAndConvert(Long id);

}
