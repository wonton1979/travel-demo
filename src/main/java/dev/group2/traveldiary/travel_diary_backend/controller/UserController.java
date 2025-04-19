package dev.group2.traveldiary.travel_diary_backend.controller;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import dev.group2.traveldiary.travel_diary_backend.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        List<User> users = userService.findAll();
        List<UserDTO> UserDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO dtoUser = new UserDTO(user.getUsername());
            UserDTOS.add(dtoUser);
        }
        return UserDTOS;
    }

    @GetMapping("/user_id/{userId}")
    public ResponseEntity<UserDTO> fetchUserByUserId(@PathVariable Long userId) {
        System.out.println(userId);
        User user = userService.getUserByUserId(userId);
        UserDTO userDTO = new UserDTO(user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
       User savedUser = userService.getUserByUsername(username);
       UserDTO savedUserDTO = new UserDTO(savedUser.getUsername());
       return ResponseEntity.ok(savedUserDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
       User created = userService.createUser(user);
       UserDTO createdUserDTO = new UserDTO(created.getUsername());
       return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @PatchMapping("/username/{username}")
    public ResponseEntity<UserDTO> modifyUser(@PathVariable String username, @RequestBody User user) {
        User modifiedUser = userService.updateUser(username, user);
        UserDTO modifiedUserDTO = new UserDTO(modifiedUser.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(modifiedUserDTO);
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        Map<String, Boolean> response = Map.of("deleted", true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
