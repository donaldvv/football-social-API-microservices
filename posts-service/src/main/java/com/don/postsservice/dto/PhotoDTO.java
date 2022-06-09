package com.don.postsservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Donald Veizi
 */
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoDTO {

    private Long id;

    private String name;

    private byte[] photoData;
}
