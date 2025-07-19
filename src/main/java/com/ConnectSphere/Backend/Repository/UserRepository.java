package com.ConnectSphere.Backend.Repository;


import com.ConnectSphere.Backend.config.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>{

    public User findByEmail(String email);

    // This is a custom JPQL query for searching users by fullName or email
    @Query("SELECT DISTINCT u FROM User u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%")
    public List<User> searchUser(@Param("query") String query);
}