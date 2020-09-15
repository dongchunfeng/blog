package com.itzixue.blog.dao;

import com.itzixue.blog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> , JpaSpecificationExecutor<Tag> {

    Tag findByName(String name);

    @Query("select t from t_tag t")
    List<Tag> findTop(Pageable pageable);

    List<Tag> findAllByIdIn(List<Long> ids);

}
