package com.ConnectSphere.Backend.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data // Lombok to generate getters, setters, equals, hashCode, and toString
public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private String image; // Profile image URL
    private String location;
    private String website;
    private String birthDate;
    private String mobile;
    private String backgroundImage; // Background/cover image URL
    private String bio;
    private boolean req_user; // Unclear purpose, but from screenshot
    private boolean login_with_google; // Indicates if user logs in via Google

    private List<UserDto> followers = new ArrayList<>(); // List of users following this user (as DTOs)
    private List<UserDto> following = new ArrayList<>(); // List of users this user is following (as DTOs)

    private boolean followed; // Indicates if the current authenticated user is following this user
//    private boolean isVerified;
}