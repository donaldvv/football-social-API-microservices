package com.don.postsservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Donald Veizi
 */
@Entity
@Table(name = "comments")
@Getter @Setter
// NONSTRICT_READ_WRITE -> inconsistencies between the cache and actual DB data can happend whenever we UPDATE entity. So if entity gets rarely updated & we dont mind small
// inconsistencies, this strategy is suitable. (in this case only the comment text might get updated, which will propably happen very rarely)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    @Id
    @SequenceGenerator(name = "comments_sequence", sequenceName = "comments_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    @Lob
    private String text;

    @Column(name="time_created", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeCreated;

    @ManyToOne
    @JoinColumn(name = "user_ext", referencedColumnName = "id", nullable = false)
    private UserExt user;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

}
