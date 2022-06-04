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
public class Like {

    @Id
    @SequenceGenerator(name = "likes_sequence", sequenceName = "likes_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "likes_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_ext", nullable = false)
    private UserExt userExt;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
