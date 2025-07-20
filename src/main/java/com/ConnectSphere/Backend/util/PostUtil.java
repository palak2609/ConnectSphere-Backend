package com.ConnectSphere.Backend.util;


import com.ConnectSphere.Backend.model.Like;
import com.ConnectSphere.Backend.model.Post;
import com.ConnectSphere.Backend.model.User;

public class PostUtil {

    // Checks if the requesting user has liked the given post
    public static boolean isLikedByReqUser(User reqUser, Post post) { // Changed method name and parameter types
        if (reqUser == null || post.getLikes() == null) {
            return false;
        }
        for (Like like : post.getLikes()) { // Assuming Post has getLikes() method returning List<Like>
            if (like.getUser() != null && like.getUser().getId().equals(reqUser.getId())) {
                return true;
            }
        }
        return false;
    }

    // Checks if the requesting user has reposted the given post
    // This assumes your Post entity has a 'getRepostedByUsers()' method
//    public static boolean isRepostedByReqUser(User reqUser, Post post) {
//        if (reqUser == null || post.getRepostedByUsers() == null) {
//            return false;
//        }
//        for (User user : post.getRepostedByUsers()) { // Assuming Post has getRepostedByUsers() returning List<User>
//            if (user.getId().equals(reqUser.getId())) {
//                return true;
//            }
//        }
//        return false;
//    }

    // You might add other utility methods here, e.g.:
    // public static boolean isBookmarkedByReqUser(User reqUser, Post post) { ... }
}