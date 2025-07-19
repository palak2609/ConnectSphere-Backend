package com.ConnectSphere.Backend.controller;

import com.ConnectSphere.Backend.Repository.UserRepository;
import com.ConnectSphere.Backend.config.JwtProvider;
import com.ConnectSphere.Backend.config.model.User; // Corrected to com.ConnectSphere.Backend.model.User
import com.ConnectSphere.Backend.exception.UserException;
import com.ConnectSphere.Backend.response.AuthResponse;
import com.ConnectSphere.Backend.service.CustomUserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsServiceImplementation customUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws UserException {

        String email= user.getEmail();
        String password=user.getPassword();
        String fullName= user.getFullName();
        String birtDate= user.getBirthDate(); // Typo: birtDate should be birthDate

        User isEmailExist=userRepository.findByEmail(email);

        if(isEmailExist!=null){
            throw new UserException("Email already used with another account");
        }

        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setPassword(passwordEncoder.encode(password)); // Encode password before saving
        createdUser.setBirthDate(birtDate);
        // createdUser.setVerification(new verification()); // This line is commented out

        User savedUser=userRepository.save(createdUser);

        // Authenticate the user immediately after signup
        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtProvider.generateToken(authentication);

        AuthResponse res=new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>signin(@RequestBody User user){
        String username=user.getEmail();
        String password=user.getPassword();

        // Authenticate the user
        Authentication authentication=authenticate(username,password);

        // --- FIX STARTS HERE ---
        // Generate token AFTER authentication
        String token = jwtProvider.generateToken(authentication);

        // Create AuthResponse and return
        AuthResponse res=new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
        // --- FIX ENDS HERE ---
    }

    private Authentication authenticate(String username,String password){
        // Load user details by username (email)
        UserDetails userDetails=customUserDetails.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid username...");
        }
        // Compare raw password with encoded password
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password...");
        }

        // Return authenticated token
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}