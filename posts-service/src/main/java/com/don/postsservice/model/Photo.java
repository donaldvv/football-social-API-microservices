package com.don.postsservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Donald Veizi
 */
@Entity
@Table(name = "photos")
@Getter @Setter
public class Photo {

    @Id
    @SequenceGenerator(name = "photos_sequence", sequenceName = "photos_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photos_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
