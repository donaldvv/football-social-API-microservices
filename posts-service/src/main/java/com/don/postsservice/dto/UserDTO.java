package com.don.postsservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Donald Veizi
 */
@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private long userId;
    private String firstName;
    private String lastName;
    private byte[] profilePhotoData;
    private String shortSummary;
}
