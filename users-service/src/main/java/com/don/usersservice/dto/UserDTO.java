package com.don.usersservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Donald Veizi
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    @NotEmpty
    private Collection<String> roles;

    private String firstName;

    private String lastName;

    private String gender;

    private String picture;

    private String address;

    private String summary;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateJoined;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    // Me sh mundsi me mir ti heq posts dhe likes. dhe nqs akseson /users/1/posts -> i coj DTO me userId, postId dhe array me PostDTO
    // njesoj edhe me likes, ose e heq fare si funksionalitet.
/*    private List<Post> posts;

    private List<Like> likes;*/
}