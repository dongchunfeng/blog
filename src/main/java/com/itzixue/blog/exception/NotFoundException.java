package com.itzixue.blog.exception;

import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * @author Mr.Dong
 * @date 2020/5/20 17:27
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2975279458215511587L;

    public NotFoundException() {

    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
