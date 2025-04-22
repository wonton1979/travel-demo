package dev.group2.traveldiary.travel_diary_backend.controller;

import dev.group2.traveldiary.travel_diary_backend.dto.AuthorizedUserDTO;
import dev.group2.traveldiary.travel_diary_backend.dto.LoginRequestDTO;
import dev.group2.traveldiary.travel_diary_backend.dto.PasswordUpdateRequest;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import dev.group2.traveldiary.travel_diary_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody User user) {
        if(userRepository.existsUserByUsernameOrEmail(user.getUsername(),user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","Username or Email already exists"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","User Registered Successfully"));
    }

    @GetMapping("/check-auth")
    public ResponseEntity<Map<String,Object>> checkAuth(Authentication auth) {
        Map<String,Object> responseRestful = new HashMap<>();
        if (auth == null || !auth.isAuthenticated()) {
            responseRestful = Map.of("isLoggedIn", false,"loggedInUsername", "");
            return ResponseEntity.ok().body(responseRestful);
        }
        responseRestful = Map.of("isLoggedIn", true,"loggedInUsername", auth.getName());
        return ResponseEntity.ok(responseRestful);
    }

    @PatchMapping("/password-update")
    public ResponseEntity<Map<String,String>> updatePassword(@RequestBody PasswordUpdateRequest request,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation. Please login again."));
        }
        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if(!passwordEncoder.matches(request.getOldPassword(), currentUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Old password does not match"));
        }
        User user = new User();
        user.setPassword(request.getNewPassword());
        userService.updateUser(userDetails.getUsername(),user);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Password updated successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthorizedUserDTO> login(@RequestBody LoginRequestDTO request, HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                return ResponseEntity.status(HttpStatus.OK).body(new AuthorizedUserDTO(user));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthorizedUserDTO());
        }
        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("Logged out");
    }
}
