package com.ConnectSphere.Backend.controller;

import com.ConnectSphere.Backend.dto.PostDto;
import com.ConnectSphere.Backend.dto.mapper.PostDtoMapper;
import com.ConnectSphere.Backend.exception.PostException;
import com.ConnectSphere.Backend.exception.UserException;
import com.ConnectSphere.Backend.model.Post;
import com.ConnectSphere.Backend.model.User;
import com.ConnectSphere.Backend.request.PostReplyRequest;
import com.ConnectSphere.Backend.response.ApiResponse;
import com.ConnectSphere.Backend.service.PostService;
import com.ConnectSphere.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    // --- Method: createPost ---
    @PostMapping("/")
    public ResponseEntity<PostDto> createPost(@RequestBody Post req,
                                              @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createPost(req, user);
        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    // --- Method: replyPost ---
    @PostMapping("/reply")
    public ResponseEntity<PostDto> replyPost(@RequestBody PostReplyRequest req,
                                             @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createdReply(req, user);
        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    // --- Method: repost ---
    @PutMapping("/{postId}/repost")
    public ResponseEntity<PostDto> repost(@PathVariable("postId") Long postId,
                                          @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.repost(postId, user);
        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    // --- Method: deletePost ---
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Long postId,
                                                  @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        postService.deletePostById(postId, user.getId());
        ApiResponse res = new ApiResponse("Post deleted successfully", true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // --- NEW METHOD: getAllPosts ---
    // (Referenced by image_0de0b9.jpg)
    @GetMapping("/") // Endpoint to get all posts (home feed)
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt); // Get authenticated user
        List<Post> posts = postService.findAllPosts(); // Get all main posts
        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user); // Map to DTOs
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    // --- NEW METHOD: getUsersAllPosts ---
    // (Referenced by image_0dddb4.png)
    @GetMapping("/user/{userId}") // Endpoint to get all posts by a specific user
    public ResponseEntity<List<PostDto>> getUsersAllPosts(@PathVariable("userId") Long userId,
                                                          @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt); // Get authenticated user
        User targetUser = userService.findUserById(userId); // Get the user whose posts are requested
        List<Post> posts = postService.getUserPosts(targetUser); // Get posts by target user
        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user); // Map to DTOs
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    // --- NEW METHOD: findPostsByLikesContainsUser ---
    // (Referenced by image_0ddd1d.jpg)
    @GetMapping("/user/{userId}/likes") // Endpoint to get posts liked by a specific user
    public ResponseEntity<List<PostDto>> findPostsByLikesContainsUser(@PathVariable("userId") Long userId,
                                                                      @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt); // Get authenticated user
        User targetUser = userService.findUserById(userId); // Get the user whose likes are requested
        List<Post> posts = postService.findByLikesContainsUser(targetUser); // Find posts liked by target user
        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user); // Map to DTOs
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
}