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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Donald Veizi
 */
@Entity
@Getter @Setter
@Table(name = "posts")
public class Post {

    @Id
    @SequenceGenerator(name = "posts_sequence", sequenceName = "posts_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    // the table that holds the id of the User that made the Post, as well as some of the users data (the ones usually shown in posts).
    // Used the wording 'ext' for External, to differentiate the
    // data that is not in this microservice (or database, but for simplicity I use 1 DB, even though each microservice,
    // uses its own set of tables). However, since it would be inefficient to call users microservice to retrieve user data when
    // building a post response, we also store some crucial user data in this ms as well. This would mean we have a significant amount of redundant data,
    // as a tradeoff to making blocking calls to another microservice.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ext", referencedColumnName = "id", nullable = false)
    private UserExt userExt;

    @Column(nullable = false)
    @Lob
    private String description;

    // we use MERGE, so that when user creates a Post, we can save both post and children (photos) together.
    // We can also update them together (MERGE allows us, while PERSIST only allows saveing together)
    @OneToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY,
            orphanRemoval = true, // useful when updating post, can remove photo
            mappedBy = "post")
    private List<Photo> photos = new LinkedList<>();

    @Column(nullable = false, columnDefinition = "INT DEFAULT '0'")
    private Integer likeCount;

    @Column(nullable = false, columnDefinition = "INT DEFAULT '0'")
    private Integer commentCount;

    @Column(name="time_created", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeCreated;

    @OneToMany(fetch = FetchType.LAZY,
            orphanRemoval = true, // TODO: maybe wont be useful, if we wont do any removal of comments from the post directly
            mappedBy = "post")
    private List<Like> likes;

    @OneToMany(fetch = FetchType.LAZY,
            orphanRemoval = true, // TODO: maybe wont be useful, if we wont do any removal of comments from the post directly
            mappedBy = "post")
    private List<Comment> comments;


    public Post addPhoto(Photo photo) {
        photos.add(photo);
        photo.setPost(this); // important, bcs we are using .MERGE and this way we can save/update parent with its children together
        return this;
    }

    // we can use orhpanRemoval attribute to remove a child
    public Post removePhoto(Photo photo) {
        photos.remove(photo);
        photo.setPost(null);
        return this;
    }
}
