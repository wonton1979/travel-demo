package dev.group2.traveldiary.travel_diary_backend.dto;

public class userDTO {
    private String username;

    public userDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
