package com.don.postsservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Donald Veizi
 */
public class FeedPost implements Serializable {

    @Id
    @SequenceGenerator(name = "feed_post_sequence", sequenceName = "feed_post_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feed_post_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    // TODO: @ManyToOne !!! (bcs 1 Post will be referenced by many FeedPost bcs the Post will be created as a FeedPost for all the connections of the person who made the post)
/*    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")*/
    private Post post;

    @Column(name = "liked", nullable = false, columnDefinition = "BOOLEAN")
    private boolean isLikedByUser;

    @Column(name="time_created", nullable = false)
    private LocalDateTime timeCreated;


}
