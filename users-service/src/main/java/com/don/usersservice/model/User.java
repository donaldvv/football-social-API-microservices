package com.don.usersservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Donald Veizi
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, length = 55, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)  // cascade mbase sduhet fare, Tek ajo e Seanca 11 e kam EAGER
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    @Column
    private String picture;

    // no cascade starategy here. will not need .PERSIST or .MERGE in this case bcs we dont need to update or save parent with children together.
    // when deleting user, we handle the deletion of PostExt without cascade.REMOVE (innefficient bcs deletes each child with seperate statement)
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<UserClub> footballClubs;

    @Column(nullable = false)
    private String address;

    @Column(name = "short_summary")
    private String shortSummary;

    @Column(length = 400)
    private String summary;

    @Column(name="date_joined", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateJoined;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(nullable = false)
    private String gender;

/*  TODO: determine if we need this reference to the users posts. most likely not. if we want to show in profile the total number of posts that user has,
     maybe we can add a counter as field and increment or delete it through an event when posts are created or deleted in posts-ws
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<PostExt> postsExt = new ArrayList<>();
*/

    public void addRole(Role role) {
        roles.add(role);
    }

}