package com.ConnectSphere.Backend.service;

import com.ConnectSphere.Backend.Repository.PostRepository; // Changed to PostRepository
import com.ConnectSphere.Backend.exception.PostException; // Using PostException
import com.ConnectSphere.Backend.exception.UserException;
import com.ConnectSphere.Backend.model.Post;
import com.ConnectSphere.Backend.model.User;
import com.ConnectSphere.Backend.request.PostReplyRequest; // Using PostReplyRequest
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // Added for LocalDateTime.now()
import java.util.List;
import java.util.Optional; // Added for Optional in findById

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository; // Changed to postRepository

    // @Autowired // Assuming you'll need UserRepository here for some methods
    // private User Repository userRepository;


    @Override
    public Post createPost(Post req, User user) throws UserException {
        Post post = new Post(); // Changed from Twit twit = new Twit();
        post.setContent(req.getContent());
        post.setCreatedAt(LocalDateTime.now()); // Using LocalDateTime.now()
        post.setImage(req.getImage());
        post.setVideo(req.getVideo()); // Assuming you have a video field in Post model
        post.setUser(user);
        post.setReply(false); // isReply
        post.setPost(true); // isPost (was isTwit)

        return postRepository.save(post); // Changed to postRepository.save(post)
    }

    @Override
    public List<Post> findAllPosts() { // Changed method name
        return postRepository.findAllByIsPostTrueOrderByCreatedAtDesc(); // Changed method call
    }

    @Override
    public Post repost(Long postId, User user) throws UserException, PostException {
        // Find the original post
        Post post = findById(postId); // Changed from Twit twit = findById(twitId);

        // Check if the user has already reposted this post
        // Since we removed 'retwitUser' and are focusing on comments,
        // this specific repost logic needs adaptation.
        // If you still have a 'repost' concept:
        // Option 1: Add a ManyToMany 'repostedBy' list on Post and check/add/remove user.
        // Option 2: Create a separate Repost entity.
        // Based on the screenshot logic:
        // This part would ideally add the user to a 'reposters' list on the post
        // or create a new 'repost' type of Post.

        // Placeholder logic if you re-add a 'repostedByUsers' list to Post:
        // if (post.getRepostedByUsers().contains(user)) {
        //     post.getRepostedByUsers().remove(user);
        // } else {
        //     post.getRepostedByUsers().add(user);
        // }
        // return postRepository.save(post);

        // For now, returning the post as per the screenshot structure,
        // but this method's actual re-post logic needs to align with your Post model's structure.
        return postRepository.save(post); // Saving the post (potentially with updated repost info)
    }


    @Override
    public Post findById(Long postId) throws PostException { // Changed from twitId
        Optional<Post> opt = postRepository.findById(postId); // Changed to postRepository.findById

        if (opt.isEmpty()) { // Check if Optional contains a value
            throw new PostException("Post not found with id: " + postId); // Changed exception
        }
        return opt.get(); // Get the Post object from Optional
    }

    @Override
    public void deletePostById(Long postId, Long userId) throws PostException, UserException { // Changed from twitId
        Post post = findById(postId); // Changed from Twit twit = findById(twitId);

        // Check if the user trying to delete is the owner of the post
        if (!userId.equals(post.getUser().getId())) { // Corrected: equals method
            throw new UserException("You can't delete another user's post"); // Changed exception
        }

        postRepository.deleteById(postId); // Changed to postRepository.deleteById
    }

    @Override
    public Post removeFromRepost(Long postId, User user) throws PostException, UserException { // Changed from twitId
        Post post = findById(postId); // Changed from twitId
        // This method's logic would be removing a user from a repost list.
        // Similar to `repost` method, depends on how reposts are tracked.
        // If you had a 'repostedByUsers' list, it would be:
        // post.getRepostedByUsers().remove(user);
        // return postRepository.save(post);
        return post; // Placeholder return
    }

    @Override
    public Post createdReply(PostReplyRequest req, User user) throws PostException { // Changed from TwitReplyReques
        Post replyFor = findById(req.getPostId()); // Changed from req.getTwitId()

        Post reply = new Post(); // Changed from Twit reply = new Twit();
        reply.setContent(req.getContent());
        reply.setCreatedAt(LocalDateTime.now());
        reply.setImage(req.getImage()); // Assuming image field in PostReplyRequest
        reply.setUser(user);
        reply.setReply(true); // This is a reply
        reply.setPost(false); // This is not a main post (it's a reply)
        reply.setReplyFor(replyFor); // Set the parent post for this reply

        Post savedReply = postRepository.save(reply); // Save the reply

        // Add the new reply to the parent post's comments list
        replyFor.getComments().add(savedReply); // Use getComments()
        postRepository.save(replyFor); // Save the updated parent post

        return replyFor; // Return the updated parent post
    }

    @Override
    public List<Post> getUserPosts(User user) { // Changed method name
        // Original: return twitRepository.findByRetwitUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAtDesc(user, user.getId());
        // This specific query needs to be adapted or removed if 'retwitUser' is not in Post.
        // It currently relies on a specific derived query method signature not compatible
        // with the `Post` model changes for 'comments'.
        // For now, returning posts by user directly.
        return postRepository.findByUserOrderByCreatedAtDesc(user); // Changed to findByUserOrderByCreatedAtDesc
    }

    @Override
    public List<Post> findByLikesContainsUser(User user) { // Changed method name
        // Original: return twitRepository.findByLikesUser_id(user.getId());
        return postRepository.findByLikesUser_Id(user.getId()); // Corrected method call
    }
}