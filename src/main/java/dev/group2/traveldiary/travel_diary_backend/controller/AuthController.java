package dev.group2.traveldiary.travel_diary_backend.controller;

import dev.group2.traveldiary.travel_diary_backend.dto.AuthorizedUserDTO;
import dev.group2.traveldiary.travel_diary_backend.dto.LoginRequestDTO;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","User Registered Successfully"));
    }

    @GetMapping("/check-auth")
    public ResponseEntity<List<Map<String,Object>>> checkAuth(Authentication auth) {
        List<Map<String,Object>> responseRestful = new ArrayList<>();
        if (auth == null || !auth.isAuthenticated()) {
            responseRestful.add(Map.of("isLoggedIn", false));
            responseRestful.add(Map.of("loggedInUsername", ""));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseRestful);
        }
        responseRestful.add(Map.of("isLoggedIn", true));
        responseRestful.add(Map.of("loggedInUsername", auth.getName()));
        return ResponseEntity.ok(responseRestful);
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
}
