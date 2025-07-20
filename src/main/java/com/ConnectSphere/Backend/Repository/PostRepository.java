package com.ConnectSphere.Backend.Repository;


import com.ConnectSphere.Backend.model.Like;
import com.ConnectSphere.Backend.model.Post;
import com.ConnectSphere.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    // Finds all main posts, ordered by creation date descending
    List<Post> findAllByIsPostTrueOrderByCreatedAtDesc();

    // Finds posts containing a specific like, ordered by creation date descending
    List<Post> findByLikesContainingOrderByCreatedAtDesc(Like like);

    // Finds posts liked by a specific user ID, ordered by creation date descending
    @Query("SELECT p FROM Post p JOIN p.likes l WHERE l.user.id = :userId ORDER BY p.createdAt DESC")
    List<Post> findByLikesUser_Id(@Param("userId") Long userId);

    // Finds all posts by a specific user, ordered by creation date descending
    List<Post> findByUserOrderByCreatedAtDesc(User user);

    // Finds all posts that are replies to a specific parent post
    List<Post> findByReplyForOrderByCreatedAtDesc(Post replyFor);

    // Finds all posts that are replies by a specific user
    List<Post> findByIsReplyTrueAndUserOrderByCreatedAtDesc(User user);
}