package com.don.usersservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Donald Veizi
 */
@Entity
@Getter @Setter
@Table(name = "user_club")
public class UserClub {

    @Id
    @SequenceGenerator(name = "user_club_sequence", sequenceName = "user_club_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_club_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "football_club_id", referencedColumnName = "id", nullable = false)
    private FootballClub footballClub;

    @Column(name = "current_club", nullable=false, columnDefinition = "BOOLEAN") //will create column of type tinyint(1)
    // @ColumnDefault("false") TODO: check if i need this
    private boolean isCurrentClub;

    @Column(nullable = false)
    private LocalDate joinedDate;

    @Column(nullable = false)
    private LocalDate leftDate;

}
