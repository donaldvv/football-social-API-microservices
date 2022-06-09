package com.don.postsservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Donald Veizi
 */
@Entity
@Table(name = "likes")
@Getter @Setter
// just like Photo, Likes will either be INSERTed or SELECTed, so READ_ONLY is most suitable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Like implements Serializable {

    @Id
    @SequenceGenerator(name = "likes_sequence", sequenceName = "likes_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "likes_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_ext", nullable = false)
    private UserExt user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
