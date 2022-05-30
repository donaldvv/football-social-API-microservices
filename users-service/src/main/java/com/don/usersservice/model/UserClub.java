package com.don.usersservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Donald Veizi
 */
@Entity
@Getter @Setter
@Table(name = "user_club")
public class UserClub extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "football_club_id", referencedColumnName = "id", nullable = false)
    private FootballClub footballClub;

    @Column(name = "current_club", columnDefinition = "BOOLEAN") //will create column of type tinyint(1)
    private Boolean isCurrentClub;

    @Column(nullable = false)
    private LocalDate joinedDate;

    @Column(nullable = false)
    private LocalDate leftDate;

}