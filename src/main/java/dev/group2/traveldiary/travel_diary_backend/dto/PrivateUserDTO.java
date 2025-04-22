package dev.group2.traveldiary.travel_diary_backend.dto;

public class PrivateUserDTO {
    private String username;
    public Long userId;

    public PrivateUserDTO(String username, Long userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
