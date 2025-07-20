package com.ConnectSphere.Backend.model;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name="likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "user_id") // Foreign key to User table
    private User user; // The user who liked

    @ManyToOne
//    @JoinColumn(name = "post_id") // Foreign key to Post table (changed from twit_id)
    private Post post; // The post that was liked (changed from Twit twit)

    // You might also add a field for liking comments if applicable
    // @ManyToOne
    // @JoinColumn(name = "comment_id")
    // private Comment comment; // if you have a separate Comment entity

    private LocalDateTime createdAt; // Timestamp for when the like was created
}