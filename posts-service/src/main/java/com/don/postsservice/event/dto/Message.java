package com.don.postsservice.event.dto;

import com.don.postsservice.model.enums.EAction;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Donald Veizi
 */
@Getter @Setter
public class Message<T> {

    private T entity;
    private EAction action;
}
