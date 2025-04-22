package dev.group2.traveldiary.travel_diary_backend.controller;
import dev.group2.traveldiary.travel_diary_backend.dto.AuthorizedUserDTO;
import dev.group2.traveldiary.travel_diary_backend.dto.PublicUserDTO;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import dev.group2.traveldiary.travel_diary_backend.dto.PrivateUserDTO;

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
    public List<PrivateUserDTO> getUsers() {
        List<User> users = userService.findAll();
        List<PrivateUserDTO> privateUserDTOS = new ArrayList<>();
        for (User user : users) {
            PrivateUserDTO dtoUser = new PrivateUserDTO(user.getUsername(),user.getUserId());
            privateUserDTOS.add(dtoUser);
        }
        return privateUserDTOS;
    }

    @GetMapping("/user_id/{userId}")
    public ResponseEntity<Object> fetchUserByUserId(@PathVariable Long userId) {
        User user = userService.getUserByUserId(userId);
        if(user.getIsPrivate() == false){
            PublicUserDTO publicUserDTO = new PublicUserDTO(user);
            return ResponseEntity.status(HttpStatus.OK).body(publicUserDTO);
        }
        PrivateUserDTO privateUserDTO = new PrivateUserDTO(user.getUsername(),user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(privateUserDTO);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
       User savedUser = userService.getUserByUsername(username);
        if(savedUser.getIsPrivate() == false){
            PublicUserDTO publicUserDTO = new PublicUserDTO(savedUser);
            return ResponseEntity.status(HttpStatus.OK).body(publicUserDTO);
        }
       PrivateUserDTO savedPrivateUserDTO = new PrivateUserDTO(savedUser.getUsername(),savedUser.getUserId());
       return ResponseEntity.ok(savedPrivateUserDTO);
    }

/*
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
       User created = userService.createUser(user);
       UserDTO createdUserDTO = new UserDTO(created.getUsername(), created.getUserId());
       return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }
*/


    @PatchMapping
    public ResponseEntity<Object> modifyUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User user) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation. Please login again."));
        }
        User modifiedUser = userService.updateUser(userDetails.getUsername(), user);
        AuthorizedUserDTO modifiedUserDTO = new AuthorizedUserDTO(modifiedUser);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedUserDTO);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteUser(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request, HttpServletResponse response) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation. Please login again."));
        }
        userService.deleteUser(userDetails.getUsername());
        request.getSession().invalidate();
        Cookie clearCookies = new Cookie("JSESSIONID", "");
        clearCookies.setMaxAge(0);
        clearCookies.setPath("/");
        response.addCookie(clearCookies);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User deleted successfully."));
    }
}
