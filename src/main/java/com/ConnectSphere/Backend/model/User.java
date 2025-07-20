package com.ConnectSphere.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String location;
    private String website;
    private String birthDate;
    private String email;
    private String password;
    private String mobile;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean req_user;
    private boolean login_with_google;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> post = new ArrayList<>();

    @JsonIgnore // Ensure this JsonIgnore is here
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>(); // New: for posts/comments liked by this user

//    @Embedded // New: for embedded verification details
//    private Verification verification; // New: Verification details

//    @JsonIgnore // Ensure this JsonIgnore is here
//    @ManyToMany // New: for followers
//    private List<User> followers = new ArrayList<>(); // New: List of users following this user
//
//    @JsonIgnore // Ensure this JsonIgnore is here
//    @ManyToMany // New: for followings
//    private List<User> followings = new ArrayList<>(); // New: List of users this user is following
@ManyToMany
@JoinTable(
        name = "user_followers",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id")
)
private List<User> followers = new ArrayList<>();

    @ManyToMany(mappedBy = "followers")
    private List<User> followings = new ArrayList<>();

}