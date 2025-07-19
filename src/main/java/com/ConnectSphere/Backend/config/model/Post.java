package com.ConnectSphere.Backend.config.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn; // For @JoinColumn
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime; // For createdAt timestamp (common for posts)


@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne // Original: @ManyToOne for user
//    @JoinColumn(name = "user_id") // Foreign key to User table
    private User user;

    private String content; // Original: private String content;
    private String image; // Added for image URL in posts
    private String video;

    // Original: @OneToMany(mappedBy = "twit", cascade = CascadeType.ALL)
    // Original: private List<Like> likes = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL) // Mapped by "post" now
    private List<Like> likes = new ArrayList<>();


    // --- CHANGE MADE HERE: Instead of replyTwits, this is for comments on this post ---
    // Original: @OneToMany
    // Original: private List<Twit> replyTwits = new ArrayList<>();
    @OneToMany
//    (mappedBy = "replyFor", cascade = CascadeType.ALL) // Mapped by "replyFor" in the Comment entity
    private List<Post> comments = new ArrayList<>(); // Changed name to 'comments' and type to 'Post' itself (as replies are also posts)
    // --- END CHANGE ---


    // --- CHANGE MADE HERE: Removed retweet-related field and added comment-related fields ---
    // Original: @ManyToMany
    // Original: private List<User> retwitUser = new ArrayList<>();
    // This field is removed as we are focusing on comments, not retweets
    // You might add a 'repostUser' if you want to track who reposted this specific post,
    // but it's not directly part of the comment functionality.
    // --- END CHANGE ---


    // Original: @ManyToOne
    // Original: private Twit replyFor;
    @ManyToOne // This post is a reply to another post
//    @JoinColumn(name = "reply_for_post_id") // Foreign key to the post this is a reply to
    private Post replyFor; // This field links a reply to its parent post


    // Original: private boolean isReply;
    private boolean isReply; // Indicates if this post is a reply

    // Original: private boolean isTwit;
    private boolean isPost; // Indicates if this is a main post (not a reply)

    // Common fields for posts
    private LocalDateTime createdAt; // Timestamp for when the post was created

    // You might add other fields like:
    // private int numberOfRetweets;
    // private int numberOfLikes;
    // private int numberOfComments;
}