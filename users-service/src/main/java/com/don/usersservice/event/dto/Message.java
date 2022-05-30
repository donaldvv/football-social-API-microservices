package com.don.usersservice.event.dto;

import com.don.usersservice.model.enums.EAction;
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
