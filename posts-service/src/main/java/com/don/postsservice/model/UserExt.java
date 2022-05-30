package com.don.postsservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Donald Veizi
 */
@Entity
@Getter @Setter
@Table(name = "user_ext")
public class UserExt extends BaseEntity {

    @Column(nullable = false, name = "user_id_ext", unique = true)
    private Long userIdExt;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "short_summary")
    private String shortSummary;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "userExt")
    private List<Post> posts;

    // maybe some auditing stuff would be good here
}
