package com.don.postsservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Donald Veizi
 */
@Getter @Setter
public class PostCreateRequest {

    @Size(min = 1, max = 1300)
    private String description;

    @NotBlank
    private Long userId;

    @Email
    @NotBlank
    private String userEmail;
}
