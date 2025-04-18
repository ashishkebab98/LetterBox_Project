package com.letterbox.customer.customer_profile_service.service;

import com.letterbox.customer.customer_profile_service.model.User;
import com.letterbox.customer.customer_profile_service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Will be autowired later

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists"); // Replace with a custom exception
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists"); // Replace with a custom exception
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    if (updatedUser.getUsername() != null && !updatedUser.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(updatedUser.getUsername())) {
                        throw new RuntimeException("Username already exists"); // Replace with custom exception
                    }
                    if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(updatedUser.getEmail())) {
                        throw new RuntimeException("Email already exists"); // Replace with custom exception
                    }
                    user.setUsername(updatedUser.getUsername() != null ? updatedUser.getUsername() : user.getUsername());
                    user.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : user.getEmail());
                    if (updatedUser.getPassword() != null) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)); // Replace with custom exception
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}