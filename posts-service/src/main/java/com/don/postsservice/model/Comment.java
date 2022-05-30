package com.don.postsservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Donald Veizi
 */
@Entity
@Table(name = "comments")
@Getter @Setter
public class Comment extends BaseEntity {

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
