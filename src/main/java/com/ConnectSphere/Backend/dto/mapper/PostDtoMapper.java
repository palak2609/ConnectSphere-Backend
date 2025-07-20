package com.ConnectSphere.Backend.dto.mapper; // Keep your package name

import com.ConnectSphere.Backend.dto.PostDto;
import com.ConnectSphere.Backend.dto.UserDto;
import com.ConnectSphere.Backend.model.Post;
import com.ConnectSphere.Backend.model.User;
import com.ConnectSphere.Backend.util.PostUtil;

import java.util.ArrayList;
import java.util.List;

public class PostDtoMapper {

    // Method to convert a single Post entity to PostDto
    // It takes the 'Post' itself and the 'reqUser' (requesting/authenticated user)
    public static PostDto toPostDto(Post post, User reqUser) {
        UserDto userDto = UserDtoMapper.toUserDto(post.getUser(), reqUser);

        // This boolean indicates if the requesting user has liked this post
        boolean isLiked = PostUtil.isLikedByReqUser(reqUser, post);

        // COMMENTED OUT: Reposted functionality is not being used
        // boolean isReposted = PostUtil.isRepostedByReqUser(reqUser, post);

        // COMMENTED OUT: Reposted functionality is not being used
        // List<Long> repostedByUserIds = new ArrayList<>();
        // if (post.getRepostedByUsers() != null) {
        //     for (User user : post.getRepostedByUsers()) {
        //         repostedByUserIds.add(user.getId());
        //     }
        // }

        // Convert child comments (which are also Posts) to PostDtos
        List<PostDto> replyPosts = new ArrayList<>();
        if (post.getComments() != null) {
            for (Post comment : post.getComments()) {
                // Recursively call toPostDto for replies/comments, passing reqUser
                replyPosts.add(toPostDto(comment, reqUser));
            }
        }

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setImage(post.getImage());
        postDto.setVideo(post.getVideo()); // Assuming video field
        postDto.setUser(userDto);
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setTotalLikes(post.getLikes() != null ? post.getLikes().size() : 0);
        postDto.setTotalReplies(post.getComments() != null ? post.getComments().size() : 0);

        // COMMENTED OUT: Reposted functionality is not being used
        // postDto.setTotalReposts(post.getRepostedByUsers() != null ? post.getRepostedByUsers().size() : 0);

        postDto.setLiked(isLiked);

        // COMMENTED OUT: Reposted functionality is not being used
        // postDto.setReposted(isReposted);
        // postDto.setRepostedByUserIds(repostedByUserIds);

        postDto.setReplyPosts(replyPosts);

        return postDto;
    }

    // Method to convert a list of Post entities to a list of PostDto
    // This is for top-level posts on a feed, not for nested replies
    public static List<PostDto> toPostDtos(List<Post> posts, User reqUser) {
        List<PostDto> postDtos = new ArrayList<>();
        if (posts != null) {
            for (Post post : posts) {
                postDtos.add(toPostDto(post, reqUser)); // Call single conversion method
            }
        }
        return postDtos;
    }
}