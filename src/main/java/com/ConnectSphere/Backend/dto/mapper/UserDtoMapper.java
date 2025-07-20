package com.ConnectSphere.Backend.dto.mapper; // Keep your package name

import com.ConnectSphere.Backend.dto.UserDto;
import com.ConnectSphere.Backend.model.User;
import com.ConnectSphere.Backend.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDtoMapper {

    // Method to convert a single User entity to UserDto
    // THIS METHOD NOW ACCEPTS TWO PARAMETERS: User (the target user) and User (the requesting user)
    public static UserDto toUserDto(User user, User reqUser) { // CORRECTED: Added User reqUser parameter
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setImage(user.getImage());
        userDto.setBackgroundImage(user.getBackgroundImage());
        userDto.setBio(user.getBio());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setLogin_with_google(user.isLogin_with_google());
        userDto.setLocation(user.getLocation());
        userDto.setWebsite(user.getWebsite());
        userDto.setMobile(user.getMobile());
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user)); // CORRECTED: Use UserUtil to set req_user

        // CORRECTED: Set 'followed' status using UserUtil
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));

        // CORRECTED: Set verification status if the Verification object exists
//        userDto.setVerified(false); // Default to false
//        Optional.ofNullable(user.getVerification())
//                .ifPresent(verification -> userDto.setVerified(verification.isStatus()));


        // Recursively convert followers and following lists for nested DTOs (simplified)
        if (user.getFollowers() != null) {
            userDto.setFollowers(toUserDtosForNested(user.getFollowers()));
        } else {
            userDto.setFollowers(new ArrayList<>());
        }

        if (user.getFollowings() != null) {
            userDto.setFollowing(toUserDtosForNested(user.getFollowings()));
        } else {
            userDto.setFollowing(new ArrayList<>());
        }

        return userDto;
    }

    // Method to convert a list of User entities to a list of UserDto (for main lists like search results)
    // THIS METHOD NOW ACCEPTS TWO PARAMETERS: List<User> (the target list) and User (the requesting user)
    public static List<UserDto> toUserDtos(List<User> users, User reqUser) { // CORRECTED: Added User reqUser parameter
        List<UserDto> userDtos = new ArrayList<>();
        if (users != null) {
            for (User user : users) {
                // CORRECTED: Call the two-parameter toUserDto method
                userDtos.add(toUserDto(user, reqUser));
            }
        }
        return userDtos;
    }

    // Helper method for NESTED lists (followers/following) to avoid infinite recursion
    // This maps only essential fields for nested users (not full recursive DTOs)
    public static List<UserDto> toUserDtosForNested(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        if (users != null) {
            for (User user : users) {
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setFullName(user.getFullName());
                userDto.setEmail(user.getEmail()); // Ensure email is mapped for nested DTOs
                userDto.setImage(user.getImage());
//                // userDto.setUserName(user.getUserName()); // Assuming userName is available if you use it in UserDto
//                userDto.setVerified(false); // Default for nested
//                Optional.ofNullable(user.getVerification())
//                        .ifPresent(verification -> userDto.setVerified(verification.isStatus()));
//                // Do NOT map followers/following recursively here
                userDtos.add(userDto);
            }
        }
        return userDtos;
    }
}