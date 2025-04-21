package dev.group2.traveldiary.travel_diary_backend.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.group2.traveldiary.travel_diary_backend.model.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizedUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isPrivate;
    private String profilePicUrl;
    private String bio;
    private Long userId;
    private Boolean success;
    private String message;

    public AuthorizedUserDTO() {
        this.success = false;
        this.message = "Invalid username or password";
    }

    public AuthorizedUserDTO(User user) {
        this.username =  user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.isPrivate = user.getIsPrivate();
        this.profilePicUrl = user.getProfilePicUrl();
        this.bio = user.getBio();
        this.userId = user.getUserId();
        this.success = true;
        this.message = "Login successful";
    }

    public String getUsername() {
        return username;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
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
    public Boolean getSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
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
    public void setEmail(String email) {
        this.email = email;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
