package com.ConnectSphere.Backend.service; // Adjust package name

import com.ConnectSphere.Backend.Repository.UserRepository;
import com.ConnectSphere.Backend.config.JwtProvider;
import com.ConnectSphere.Backend.exception.UserException;
import com.ConnectSphere.Backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // Added for Optional in findUserById

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider; // Autowire JwtProvider

    @Override
    public User findUserById(Long userId) throws UserException {
        // Original: User user=userRepository.findById(userId).orElseThrow(()->new UserException("User not found with id"+userId));
        Optional<User> opt = userRepository.findById(userId); // Use Optional for safety

        if (opt.isEmpty()){ // Check if user is present
            throw new UserException("User not found with id: " + userId); // Clearer error message
        }
        return opt.get(); // Get the User object
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        // Method implementation from screenshot
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException("User not found with email: " + email);
        }
        return user; // Original screenshot had 'return user;;' - removed extra semicolon
    }

    @Override
    public User updateUser(Long userId, User req) throws UserException { // Renamed parameter to 'req' to avoid conflict
        // Method implementation from screenshots
        User user = findUserById(userId); // Use findUserById to get the user to update

        if(req.getFullName() != null) {
            user.setFullName(req.getFullName());
        }

        if(req.getImage() != null) {
            user.setImage(req.getImage());
        }

        if(req.getBackgroundImage() != null) {
            user.setBackgroundImage(req.getBackgroundImage());
        }

        if(req.getBirthDate() != null) { // Added this check as per screenshot
            user.setBirthDate(req.getBirthDate());
        }

        if(req.getLocation() != null) { // Added this check as per screenshot
            user.setLocation(req.getLocation());
        }

        if(req.getBio() != null) { // Added this check as per screenshot
            user.setBio(req.getBio());
        }

        if(req.getWebsite() != null) { // Added this check as per screenshot
            user.setWebsite(req.getWebsite());
        }

        return userRepository.save(user); // Save the updated user
    }

    @Override
    public User followUser(Long userId, User user) throws UserException { // User 'user' is the one performing the follow
        // Method implementation from screenshot
        User followToUser = findUserById(userId); // The user to be followed

        if(user.getFollowings().contains(followToUser) && followToUser.getFollowers().contains(user)) {
            user.getFollowings().remove(followToUser);
            followToUser.getFollowers().remove(user);
        } else {
            user.getFollowings().add(followToUser);
            followToUser.getFollowers().add(user);
        }

        userRepository.save(followToUser); // Save the user being followed
        userRepository.save(user); // Save the user performing the follow

        return followToUser; // Return the user that was followed/unfollowed
    }

    @Override
    public List<User> searchUser(String query) {
        // Method implementation from screenshot
        return userRepository.searchUser(query); // Call the custom search method from UserRepository
    }
}