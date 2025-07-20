package com.ConnectSphere.Backend.request;


import lombok.Data; // For getters and setters

import java.time.LocalDateTime;

@Data
public class PostReplyRequest {
    private String content; // The text content of the reply
    private Long postId;    // The ID of the post being replied to (parent post)
    private LocalDateTime createdAt;
    private String image;   // Optional: URL of an image for the reply


    // You might add other fields like:
    // private String video;
    // private Long userId; // The ID of the user replying (often inferred from auth token)
}