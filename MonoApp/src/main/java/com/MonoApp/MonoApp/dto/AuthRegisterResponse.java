package com.MonoApp.MonoApp.dto;

import com.MonoApp.MonoApp.model.User;

public class AuthRegisterResponse {
    private User user;
    private String accessToken;
    private String refreshToken;

    public AuthRegisterResponse(User user, String accessToken, String refreshToken) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // getters y setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
