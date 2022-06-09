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
@Table(name = "photos")
@Getter @Setter
// this entity will not get updated, so READ_ONLY would be suitable. will throw if we try to UPDATE Photo
// since we are using sequence, the item will be cached right away when INSERT (and obviously for SELECT)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Photo implements Serializable {

    @Id
    @SequenceGenerator(name = "photos_sequence", sequenceName = "photos_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photos_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
