package com.itzixue.blog.service.impl;

import com.itzixue.blog.dao.BlogRepository;
import com.itzixue.blog.entity.Blog;
import com.itzixue.blog.entity.Tag;
import com.itzixue.blog.entity.Type;
import com.itzixue.blog.exception.NotFoundException;
import com.itzixue.blog.service.BlogService;
import com.itzixue.blog.util.MarkdownUtils;
import com.itzixue.blog.util.MyBeanUtils;
import com.itzixue.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author Mr.Dong
 * @date 2020/5/22 12:04
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog addBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);//阅读量
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public void delBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogRepository.getOne(id);
        if (one == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, one, MyBeanUtils.getNullPropertyNames(blog));
        one.setUpdateTime(new Date());
        return blogRepository.save(one);
    }

    @Transactional
    @Override
    public int updateViews(Long id) {
        return blogRepository.updateViews(id);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Blog, Tag> tags = root.join("tags",JoinType.LEFT);
                return criteriaBuilder.equal(tags.get("id"), tagId);
            }
        },pageable);
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        //先查出博客的所有年份 放入集合中
        List<String> groupsYear = blogRepository.findGroupsYear();
        //根据年份查询博客
        Map<String, List<Blog>> map  = new HashMap<>();
        for (String year: groupsYear) {
            map.put(year,blogRepository.findBlogByYear(year));
        }
        return map;
    }

    @Override
    public List<Blog> listTopBlog(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public List<Blog> listHotBlog(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"views");
        Pageable pageable = PageRequest.of(0,size,sort);
        return  blogRepository.findHot(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Blog getAndConvert(Long id){
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if(!blogOptional.isPresent()){
            throw new NotFoundException("该博客不存在");
        }
        Blog blog = blogOptional.get();
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        return b;
    }
}
