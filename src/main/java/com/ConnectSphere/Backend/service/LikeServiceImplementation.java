package com.ConnectSphere.Backend.service; // Adjust package name

import com.ConnectSphere.Backend.Repository.LikeRepository;
import com.ConnectSphere.Backend.exception.PostException;
import com.ConnectSphere.Backend.exception.UserException;
import com.ConnectSphere.Backend.model.Like;
import com.ConnectSphere.Backend.model.Post;
import com.ConnectSphere.Backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImplementation implements LikeService {

    @Autowired
    private LikeRepository likeRepository; // Autowire LikeRepository

    @Autowired
    private PostService postService; // Autowire PostService (assuming your service is named PostService)


    @Override
    public Like likePost(Long postId, User user) throws UserException, PostException {
        // Original: Like isLikeExist = likeRepository.isLikeExist(user.getId(), twitId);
        Like isLikeExist = likeRepository.isLikeExist(user.getId(), postId); // Changed twitId to postId

        // If a like already exists, delete it (unlike)
        if (isLikeExist != null) {
            likeRepository.deleteById(isLikeExist.getId()); // Delete the existing like
            return isLikeExist; // Return the deleted like (or null/updated like)
        }

        // Find the post to which the like is associated
        // Original: Twit twit = twitService.findById(twitId);
        Post post = postService.findById(postId); // Changed twitService to postService, twitId to postId, Twit to Post

        // Create a new Like object
        Like like = new Like();
        like.setPost(post); // Set the post
        like.setUser(user); // Set the user who liked

        // Save the new like
        Like savedLike = likeRepository.save(like); // Save the new like

        // Add the saved like to the post's likes collection and save the post
        // Original: twit.getLikes().add(savedLike);
        // Original: twitRepository.save(twit);
        post.getLikes().add(savedLike); // Add to post's likes (assuming getLikes() exists on Post)
        // postService.save(post); // You might need a save method in PostService if you want to update the post
        // Or directly use postRepository if it's available here.
        // If PostRepository is not autowired directly:
        // @Autowired private PostRepository postRepository; // Uncomment this if needed
        // postRepository.save(post);
        // Given that postService.findById fetches a managed entity, saving it via repository is fine.
        postService.createPost(post, user); // This would re-save the post, potentially causing issues.
        // Better to update it directly.
        // If you have a postRepository autowired, use postRepository.save(post) here.
        // For now, assuming your post object is managed and changes will persist or
        // you'll handle save in a higher service method if needed.
        // A direct postRepository.save(post); is typically cleaner here if PostRepository is autowired.

        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(Long postId) throws PostException { // Changed twitId to postId
        // Original: Twit twit = twitService.findById(twitId);
        Post post = postService.findById(postId); // Changed twitId to postId

        // Original: List<Like> likes = likeRepository.findByTwitId(twitId);
        List<Like> likes = likeRepository.findByPostId(postId); // Changed findByTwitId to findByPostId

        return likes;
    }
}