package com.itzixue.blog.dao;

import com.itzixue.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Mr.Dong
 * @date 2020/5/22 11:47
 **/
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select tb from t_blog tb where tb.recommend=true")
    List<Blog> findTop(Pageable pageable);

    @Query("select tb from t_blog tb")
    List<Blog> findHot(Pageable pageable);

    @Query("select tb from t_blog  tb where tb.title like ?1 or tb.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Modifying
    @Query("update t_blog t set t.views=t.views+1 where t.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format',t.updateTime,'%Y') as year from t_blog t group by function('date_format',t.updateTime,'%Y') order by function('date_format',t.updateTime,'%Y') desc")
    List<String> findGroupsYear();

    @Query("select b from t_blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findBlogByYear(String year);

}
