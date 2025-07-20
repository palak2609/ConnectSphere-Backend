package com.ConnectSphere.Backend.service;


import com.ConnectSphere.Backend.exception.PostException;
import com.ConnectSphere.Backend.exception.UserException;
import com.ConnectSphere.Backend.model.Like;
import com.ConnectSphere.Backend.model.User;

import java.util.List;

public interface LikeService {

    public Like likePost(Long postId, User user) throws UserException, PostException;

    public List<Like>getAllLikes(Long postId) throws PostException;
}
