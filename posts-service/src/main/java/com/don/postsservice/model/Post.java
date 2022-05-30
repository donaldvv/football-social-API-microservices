package com.don.postsservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Donald Veizi
 */
@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "posts")
public class Post extends BaseEntity {

    // the table that holds the id of the User that made the Post, as well as some of the users data (the ones usually shown in posts).
    // Used the wording 'ext' for External, to differentiate the
    // data that is not in this microservice (or database, but for simplicity I use 1 DB, even though each microservice,
    // uses its own set of tables). However, since it would be inefficient to call users microservice to retrieve user data when
    // building a post response, we also store some crucial user data in this ms as well. This would mean we have a significant amount of redundant data,
    // as a tradeoff to making blocking calls to another microservice.
    @ManyToOne
    @JoinColumn(name = "user_ext", referencedColumnName = "id", nullable = false)
    private UserExt userExt;

    @Column(nullable = false)
    @Lob
    private String description;

    // since the life-cycle of the photos is bound by the parent(Post), Cascade.ALL & orphanRemoval are suitable
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "post")
    private List<Photo> photos;

    @Column(nullable = false)
    private Integer likeCount;

    @Column(nullable = false)
    private Integer commentCount;

    @Column(name="time_created", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeCreated;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "post")
    private List<Like> likes;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "post")
    private List<Comment> comments;

}
