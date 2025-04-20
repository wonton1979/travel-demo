package dev.group2.traveldiary.travel_diary_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, String> welcome() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to the Travel Diary API!");
        response.put("status", "OK");
        response.put("version", "1.0");
        return response;
    }
}
