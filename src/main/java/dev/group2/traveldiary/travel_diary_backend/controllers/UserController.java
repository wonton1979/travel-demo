package dev.group2.traveldiary.travel_diary_backend.controllers;
import dev.group2.traveldiary.travel_diary_backend.Itinerary;
import dev.group2.traveldiary.travel_diary_backend.User;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import dev.group2.traveldiary.travel_diary_backend.dto.userDTO;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{username}/itinerary")
    public List<Itinerary> getDiaries(@PathVariable String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()) {
            return userOpt.get().getDiaries();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
       User savedUser = userRepository.save(user);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<userDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()) {
            userDTO userInfo = new userDTO(userOpt.get().getUsername());
            return ResponseEntity.ok(userInfo);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }
}
