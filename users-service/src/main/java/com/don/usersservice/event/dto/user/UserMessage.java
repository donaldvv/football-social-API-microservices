package com.don.usersservice.event.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Donald Veizi
 */
@Getter @Setter
public class UserMessage {

    private Long userId;

    private String firstName;

    private String lastName;

    private String shortSummary;

    private String profilePhoto;
}