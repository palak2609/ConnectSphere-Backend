package com.ConnectSphere.Backend.Repository;

import com.ConnectSphere.Backend.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface LikeRepository extends JpaRepository<Like, Long> {

    // Query to check if a specific user has liked a specific post
    // Replaced 'twit.id' with 'post.id' and 'twitId' with 'postId'
    @Query("SELECT l FROM Like l WHERE l.user.id=:userId AND l.post.id=:postId")
    public Like isLikeExist(@Param("userId") Long userId, @Param("postId") Long postId);

    // Query to find all likes for a specific post
    // Replaced 'twit.id' with 'post.id' and 'twitId' with 'postId'
    @Query("SELECT l FROM Like l WHERE l.post.id=:postId")
    public List<Like> findByPostId(@Param("postId") Long postId);


}