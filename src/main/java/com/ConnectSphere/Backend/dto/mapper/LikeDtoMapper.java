package com.ConnectSphere.Backend.dto.mapper; // Keep your package name

import com.ConnectSphere.Backend.dto.LikeDto;
import com.ConnectSphere.Backend.dto.PostDto;
import com.ConnectSphere.Backend.dto.UserDto;
import com.ConnectSphere.Backend.model.Like;
import com.ConnectSphere.Backend.model.User;

import java.util.ArrayList;
import java.util.List;


public class LikeDtoMapper {

    // Method to convert a single Like entity to LikeDto
    // It takes the 'Like' entity itself and the 'reqUser' (requesting/authenticated user)
    public static LikeDto toLikeDto(Like like, User reqUser) {
        // FIX: Pass 'reqUser' to UserDtoMapper.toUserDto
        UserDto userDto = UserDtoMapper.toUserDto(like.getUser(), reqUser); // Corrected call

        // Changed from twit to post, and TwitDtoMapper to PostDtoMapper
        PostDto postDto = PostDtoMapper.toPostDto(like.getPost(), reqUser); // This call was already correct

        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setPost(postDto); // Set the mapped PostDto
        likeDto.setUser(userDto); // Set the mapped UserDto
        // You might add other fields from Like entity, like createdAt, if they exist in LikeDto
        // likeDto.setCreatedAt(like.getCreatedAt());

        return likeDto;
    }

    // Method to convert a list of Like entities to a list of LikeDto
    public static List<LikeDto> toLikeDtos(List<Like> likes, User reqUser) {
        List<LikeDto> likeDtos = new ArrayList<>();
        if (likes != null) {
            for (Like like : likes) {
                // FIX: Pass 'reqUser' to toLikeDto when converting a list
                likeDtos.add(toLikeDto(like, reqUser)); // Call single conversion method for each like
            }
        }
        return likeDtos;
    }
}