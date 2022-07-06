package com.don.postsservice.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Donald Veizi
 */
public class Feed implements Serializable {

    @Id
    @SequenceGenerator(name = "feeds_sequence", sequenceName = "feeds_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feeds_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // uses same PK for both tables, meaning we will not have a FK column. allows us to use UniDirectional mapping, while also having the ability to get Feed from
    // a UserExt object (it will not have a feed field, but they have same ID so we can find feed using it)
    @JoinColumn(name = "id")
    private UserExt user;

    // the number of FeedPosts for this Feed. the idea is that if it gets bigger than a certain value, we can delete the older FeedPosts
    @Column(nullable = false, columnDefinition = "INT DEFAULT '0'")
    private Integer numberOfPosts;

    // more stuff if needed
}
