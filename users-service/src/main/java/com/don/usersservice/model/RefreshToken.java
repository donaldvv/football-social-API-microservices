package com.don.usersservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author Donald Veizi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "refresh_tokens")
public class RefreshToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private Instant expiration;

    @Column(unique = true, nullable = false)
    private String token;

    public RefreshToken(User user, String token, Instant expiration) {
        this.user = user;
        this.expiration = expiration;
        this.token = token;
    }
}
