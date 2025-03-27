package org.codingwallah.em_project.service;

import org.codingwallah.em_project.entity.UserEntity;

public interface UserService {
    UserEntity registerUser(String username, String password, String role);
    UserEntity findByUsername(String username);
}
