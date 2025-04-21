package dev.group2.traveldiary.travel_diary_backend.service;
import dev.group2.traveldiary.travel_diary_backend.dto.UserDTO;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new ContentNotFoundException("User not found");
        }
    }

    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new ContentNotFoundException("User not found");
        }
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String username ,User user) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            User existingUser = userOptional.get();
            if(user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if(user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if(user.getBio() != null) {
                existingUser.setBio(user.getBio());
            }
            if(user.getProfilePicUrl() != null) {
                existingUser.setProfilePicUrl(user.getProfilePicUrl());
            }
            return userRepository.save(existingUser);
        }
        else {
            throw new RuntimeException("user not found");
        }
    }

    public void deleteUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        }
        else {
            throw new ContentNotFoundException("user not found");
        }
    }
}
