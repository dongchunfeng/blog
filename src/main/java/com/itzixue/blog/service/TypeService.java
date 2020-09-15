package com.itzixue.blog.service;

import com.itzixue.blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type addType(Type type);

    void delType(Long id);

    Type updateType(Long id,Type type);

    Type getTypeById(Long id);

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);

    List<Type> findAll();

    List<Type> listTypeTop(Integer size);

}
