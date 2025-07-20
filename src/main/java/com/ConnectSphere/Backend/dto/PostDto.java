package com.ConnectSphere.Backend.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data // Lombok  to generate getters, setters, equals, hashCode, and toString
public class PostDto {

    private Long id;
    private String content;
    private String image;
    private String video;

    private UserDto user; // Assuming you have a UserDto class
    private LocalDateTime createdAt;
    private int totalLikes;
    private int totalReplies;
    private int totalReposts; // Changed: was totalRetweets (for consistency with 'repost')

    private boolean isLiked;
    private boolean isReposted; // Changed: was isRetwit; indicates if current user has reposted this

    private List<Long> repostedByUserIds; // Changed: was retwitUsersId; list of user IDs who reposted this post
    private List<PostDto> replyPosts; // Changed: was replyTwits; list of replies (which are also Posts)
}