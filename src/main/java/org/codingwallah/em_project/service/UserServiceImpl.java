package org.codingwallah.em_project.service;

import org.codingwallah.em_project.entity.UserEntity;
import org.codingwallah.em_project.repository.UserRepository;
import org.codingwallah.em_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Used to encrypt passwords

    @Override
    public UserEntity registerUser(String username, String password, String role) {
        // Check if the username is already taken
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username is already taken!");
        }

        // Create new user
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encrypt password
        user.setRole(role);

        // Save to database
        return userRepository.save(user);
    }

 @Override
public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
}

}
