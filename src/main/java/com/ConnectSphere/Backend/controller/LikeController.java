package com.ConnectSphere.Backend.controller;

import com.ConnectSphere.Backend.dto.LikeDto;
import com.ConnectSphere.Backend.dto.mapper.LikeDtoMapper;
import com.ConnectSphere.Backend.exception.PostException;
import com.ConnectSphere.Backend.exception.UserException;
import com.ConnectSphere.Backend.model.Like;
import com.ConnectSphere.Backend.model.User;
import com.ConnectSphere.Backend.service.LikeService;
import com.ConnectSphere.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api") // Common to have a base /api path for all controllers
public class LikeController {

    @Autowired
    private UserService userService; // Autowire UserService

    @Autowired
    private LikeService likeService; // Autowire LikeService

    // --- Method from screenshot: likeTwit -> likePost ---
    // (Referenced by image_0d6c5d.jpg)
    @PostMapping("/posts/{postId}/likes") // Changed path variable and endpoint name
    public ResponseEntity<LikeDto> likePost(@PathVariable("postId") Long postId, // Changed twitId to postId
                                            @RequestHeader("Authorization") String jwt)
            throws UserException, PostException { // Changed TwitException to PostException
        User user = userService.findUserProfileByJwt(jwt); // Get authenticated user

        // Call service to like/unlike the post
        // Original: Like like = likeService.likeTwit(twitId, user);
        Like like = likeService.likePost(postId, user); // Changed method name and parameters

        // Map the result to LikeDto
        LikeDto likeDto = LikeDtoMapper.toLikeDto(like, user); // Pass user for mapping context

        // Original screenshot had 'return null;' initially, but then returns likeDto
        return new ResponseEntity<>(likeDto, HttpStatus.CREATED); // Return LikeDto with CREATED status
    }

    // --- Method from screenshot: getAllLikes ---
    // (Referenced by image_0d68f8.jpg)
    // Note: Endpoint changed from PostMapping to GetMapping as it fetches data
    @GetMapping("/posts/{postId}/likes") // Changed path variable and endpoint name (GET for fetching)
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable("postId") Long postId, // Changed twitId to postId
                                                     @RequestHeader("Authorization") String jwt)
            throws UserException, PostException { // Changed TwitException to PostException
        User user = userService.findUserProfileByJwt(jwt); // Get authenticated user

        // Call service to get all likes for the post
        // Original: List<Like> like = likeService.getAllLikes(twitId); // Name 'like' for List is confusing
        List<Like> likes = likeService.getAllLikes(postId); // Changed twitId to postId, and variable name to 'likes'

        // Map the list of Like entities to a list of LikeDto
        // Original: List<LikeDto> likeDto = LikeDtoMapper.toLikeDtos(like, user);
        List<LikeDto> likeDtos = LikeDtoMapper.toLikeDtos(likes, user); // Pass list of Likes and user for mapping context

        // Original screenshot had HttpStatus.CREATED, but GET requests typically return HttpStatus.OK
        return new ResponseEntity<>(likeDtos, HttpStatus.OK); // Return List<LikeDto> with OK status
    }
}