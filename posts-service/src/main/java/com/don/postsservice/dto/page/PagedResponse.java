package com.don.postsservice.dto.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PagedResponse<U> {
    private List<U> content;
    private Integer count;
    private Long totalCount;

    public PagedResponse(final List<U> content, final Integer count, final Long totalCount) {
        this.content = content;
        this.count = count;
        this.totalCount = totalCount;
    }
}
