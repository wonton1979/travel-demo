package dev.group2.traveldiary.travel_diary_backend.dto;

public class PasswordUpdateRequest {
    private String oldPassword;
    private String newPassword;

    public  PasswordUpdateRequest(String oldPassword,String newPassword){
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
    public String getOldPassword() {
        return oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
