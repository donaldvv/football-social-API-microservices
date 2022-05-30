package com.don.usersservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Donald Veizi
 */
@Entity
@Getter @Setter
@Table(name = "football_club")
public class FootballClub extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String logo;

}
