package dev.group2.traveldiary.travel_diary_backend.dto;

public class UserDTO {
    private String username;

    public UserDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
