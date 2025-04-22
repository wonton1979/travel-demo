package dev.group2.traveldiary.travel_diary_backend.service;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ItineraryService itineraryService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ItineraryService itineraryService, PasswordEncoder passwordEncoder) {
        this.itineraryService = itineraryService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public boolean comparePasswords(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
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
            if(user.getFirstName() != null) {
                existingUser.setFirstName(user.getFirstName());
            }
            if(user.getLastName() != null) {
                existingUser.setLastName(user.getLastName());
            }
            if(user.getBio() != null) {
                existingUser.setBio(user.getBio());
            }
            if(user.getProfilePicUrl() != null) {
                existingUser.setProfilePicUrl(user.getProfilePicUrl());
            }
            if(user.getPassword() != null) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if(user.getIsPrivate() != null) {
                if(user.getIsPrivate()) {
                    existingUser.setIsPrivate(true);
                    itineraryService.getItinerariesByUserName(username).forEach(itinerary -> {
                        itinerary.setIsPrivate(true);
                    });
                }
                else {
                    existingUser.setIsPrivate(false);
                }
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
