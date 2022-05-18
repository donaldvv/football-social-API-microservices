package com.don.postsservice.model;

// MOVE IT TO post-service

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // the table that holds the id of the User that made the Post. Used the wording 'ext' for External, to differentiate the
    // data that is not in this microservice (or database, but for simplicity I use 1 DB, even though each microservice,
    // uses its own set of tables)
    @OneToOne
    @JoinColumn(name = "post_user_ext", referencedColumnName = "id", nullable = false)
    private PostUserExt postUserExt;
}
