package dev.group2.traveldiary.travel_diary_backend.dto;


import dev.group2.traveldiary.travel_diary_backend.model.User;

public class PublicUserDTO {
    private String username;
    private Boolean isPrivate;
    private String profilePicUrl;
    private String bio;
    private Long userId;


    public PublicUserDTO(User user) {
        this.username =  user.getUsername();
        this.isPrivate = user.getIsPrivate();
        this.profilePicUrl = user.getProfilePicUrl();
        this.bio = user.getBio();
        this.userId = user.getUserId();
    }


    public String getUsername() {
        return username;
    }
    public Boolean getIsPrivate() {
        return isPrivate;
    }
    public String getProfilePicUrl() {
        return profilePicUrl;
    }
    public String getBio() {
        return bio;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}