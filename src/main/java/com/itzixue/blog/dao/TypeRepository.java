package com.itzixue.blog.dao;

import com.itzixue.blog.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);

    @Query("select ty from t_type ty")
    List<Type> findTop(Pageable pageable);

}
