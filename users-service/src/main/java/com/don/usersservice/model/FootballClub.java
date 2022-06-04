package com.don.usersservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Donald Veizi
 */
@Entity
@Getter @Setter
@Table(name = "football_clubs")
public class FootballClub {

    @Id
    @SequenceGenerator(name = "football_clubs_sequence", sequenceName = "football_clubs_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "football_clubs_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String logo;
}
