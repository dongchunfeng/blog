package com.itzixue.blog.service.impl;

import com.itzixue.blog.dao.TypeRepository;
import com.itzixue.blog.entity.Type;
import com.itzixue.blog.exception.NotFoundException;
import com.itzixue.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Dong
 * @date 2020/5/21 19:38
 **/
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type addType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public void delType(Long id) {
        typeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Optional<Type> optional = typeRepository.findById(id);
        if(!optional.isPresent()){
            throw new NotFoundException("数据为空");
        }
        Type t = optional.get();
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Override
    public Type getTypeById(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }
}
