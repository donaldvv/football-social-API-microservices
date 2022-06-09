package com.don.postsservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author Donald Veizi
 */
@Entity
@Getter @Setter
@Table(name = "user_ext",
    indexes = {@Index(name = "user_ext_id_index", columnList = "user_id_ext", unique = true)}
)
// will rarely be UPDATEd by an Event, and the values that will be updated will only be: first and lastname, summary and photo, which will be updated rarely, and we would not mind small inconsistencies
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserExt implements Serializable {

    @Id
    @SequenceGenerator(name = "user_ext_sequence", sequenceName = "user_ext_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_ext_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false, name = "user_id_ext")
    private Long userIdExt;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "short_summary")
    private String shortSummary;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<Post> posts;

}
