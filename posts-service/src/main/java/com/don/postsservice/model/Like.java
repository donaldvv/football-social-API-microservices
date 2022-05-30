package com.don.postsservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Donald Veizi
 */
@Entity
@Table(name = "likes")
@Getter @Setter
public class Like extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_ext", nullable = false)
    private UserExt userExt;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
