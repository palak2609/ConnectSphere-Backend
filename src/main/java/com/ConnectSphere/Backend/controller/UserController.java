package com.ConnectSphere.Backend.controller; // Ensure this package matches your project structure

import com.ConnectSphere.Backend.dto.UserDto;
import com.ConnectSphere.Backend.dto.mapper.UserDtoMapper;
import com.ConnectSphere.Backend.exception.UserException;
import com.ConnectSphere.Backend.model.User;
import com.ConnectSphere.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users") // Standard base path for user-related endpoints
public class UserController {

    @Autowired
    private UserService userService; // Autowires UserService

    // Method: getUserProfile - Get the profile of the currently authenticated user
    // (Referenced by earlier screenshots for profile header)
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt)
            throws UserException {
        User user = userService.findUserProfileByJwt(jwt); // Find the authenticated user from JWT
        // Corrected call: Pass 'user' as both target user and requesting user, since it's their own profile
        UserDto userDto = UserDtoMapper.toUserDto(user, user);
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    // Method: getUserById - Get profile of a user by their ID
    // (Referenced by earlier screenshots for getting user by ID)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId,
                                               @RequestHeader("Authorization") String jwt)
            throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt); // The authenticated user (making the request)
        User user = userService.findUserById(userId); // The target user whose profile is being requested

        // Corrected call: Pass both 'user' (target) and 'reqUser' (authenticated) to the mapper
        UserDto userDto = UserDtoMapper.toUserDto(user, reqUser);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    // Method: updateUser - Update the profile of the authenticated user
    // (Referenced by earlier screenshots for updating user)
    @PutMapping("/update") // Common endpoint for updating user's own profile
    public ResponseEntity<UserDto> updateUser(@RequestBody User req, // User object from request body with updates
                                              @RequestHeader("Authorization") String jwt)
            throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt); // The authenticated user (target of update)

        // Call service to update the user using the authenticated user's ID
        User updatedUser = userService.updateUser(reqUser.getId(), req);

        // Corrected call: Pass 'reqUser' to the mapper for contextual flags
        UserDto userDto = UserDtoMapper.toUserDto(updatedUser, reqUser);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    // Method: followUser - Follow or unfollow a user
    // (Referenced by earlier screenshots for follow/unfollow logic)
    @PutMapping("/{userId}/follow") // Endpoint to follow/unfollow a user by ID
    public ResponseEntity<UserDto> followUser(@PathVariable("userId") Long userId, // ID of user to follow/unfollow
                                              @RequestHeader("Authorization") String jwt)
            throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt); // The user performing the follow/unfollow action

        // Call service to perform follow/unfollow. The service returns the updated target user.
        User updatedUser = userService.followUser(userId, reqUser);

        // Corrected call: Pass 'reqUser' to the mapper for contextual flags (isFollowed, isReqUser)
        UserDto userDto = UserDtoMapper.toUserDto(updatedUser, reqUser);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    // Method: searchUser - Search for users
    // (Referenced by earlier screenshots for user search)
    @GetMapping("/search") // Endpoint for searching users by query parameter
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String query, // Search query from request parameter
                                                    @RequestHeader("Authorization") String jwt)
            throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt); // The authenticated user (for contextual flags)
        List<User> users = userService.searchUser(query); // Search users via service layer

        // Corrected call: Pass 'reqUser' to UserDtoMapper.toUserDtos as it now expects it
        List<UserDto> userDtos = UserDtoMapper.toUserDtos(users, reqUser);

        return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);
    }
}