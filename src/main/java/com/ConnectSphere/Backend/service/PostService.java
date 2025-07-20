package com.ConnectSphere.Backend.service;


import com.ConnectSphere.Backend.exception.PostException;
import com.ConnectSphere.Backend.exception.UserException;
import com.ConnectSphere.Backend.model.Post;
import com.ConnectSphere.Backend.model.User;
import com.ConnectSphere.Backend.request.PostReplyRequest;

import java.util.List;

public interface PostService {

    // Changed 'Twit' to 'Post' for method return type and parameter
    public Post createPost(Post req, User user) throws UserException;

    // Changed 'Twit' to 'Post' for return type in List
    public List<Post> findAllPosts(); // Changed method name to findAllPosts

    // Changed 'Twit' to 'Post' for parameter type
    // Changed 'twitId' to 'postId'
    // Changed 'TwitException' to 'PostException'
    public Post repost(Long postId, User user) throws UserException, PostException; // Changed method name to repost
    // New: Method from screenshot - find post by ID
    public Post findById(Long postId) throws PostException; // Changed from findById(Long twitId) throws TwitException

    // New: Method from screenshot - delete post by ID
    public void deletePostById(Long postId, Long userId) throws PostException, UserException; // Changed from deleteTwitById

    // New: Method from screenshot - remove from repost
    public Post removeFromRepost(Long postId, User user) throws PostException, UserException; // Changed from removeFroRetwit

    // New: Method from screenshot - create reply
    // Changed 'TwitReplyReques' to 'PostReplyRequest'
    public Post createdReply(PostReplyRequest req, User user) throws PostException; // Changed from createdReply

    // Original: public List<Twit> getUserTwit(User user);
    // Finds all posts created by a specific user
    public List<Post> getUserPosts(User user); // Changed to getUserPosts

    // Original: public List<Twit> findByLikesContainsUser(User user);
    // Finds all posts that a specific user has liked
    // Note: The 'findByLikesContainingOrderByCreatedDesc' in PostRepository is more specific.
    // This one implies finding posts where the 'likes' collection contains a 'Like' object associated with the user.
    public List<Post> findByLikesContainsUser(User user); // Changed to Post


}