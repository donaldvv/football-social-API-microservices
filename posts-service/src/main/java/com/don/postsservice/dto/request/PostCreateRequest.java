package com.don.postsservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Donald Veizi
 */
@Getter @Setter
public class PostCreateRequest {

    @Size(min = 1, max = 1300)
    private String description;

    @Min(1)
    @NotNull
    private Long userId;

}
