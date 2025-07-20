package com.ConnectSphere.Backend.util;


import com.ConnectSphere.Backend.model.User;


public class UserUtil {

    // Checks if User reqUser is the same as User user2 (by comparing IDs)
    public static final boolean isReqUser(User reqUser, User user2) {
        return reqUser.getId().equals(user2.getId());
    }

    // Checks if User reqUser is following User user2
    // This relies on reqUser's 'followings' list containing user2
    public static final boolean isFollowedByReqUser(User reqUser, User user2) {
        return reqUser.getFollowings().contains(user2);
    }
}