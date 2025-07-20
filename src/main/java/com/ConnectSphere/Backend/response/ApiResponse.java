package com.ConnectSphere.Backend.response;


import lombok.AllArgsConstructor; // For constructor with all fields
import lombok.Data; // For getters and setters
import lombok.NoArgsConstructor; // For no-arg constructor

@Data // Lombok annotation for boilerplate code
@NoArgsConstructor // Lombok annotation for no-argument constructor
@AllArgsConstructor // Lombok annotation for constructor with all fields
public class ApiResponse {
    private String message;
    private boolean status;
}