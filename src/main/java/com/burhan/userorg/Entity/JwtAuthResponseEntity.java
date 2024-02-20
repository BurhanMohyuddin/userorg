package com.burhan.userorg.Entity;


public class JwtAuthResponseEntity {

    private String token;

    public JwtAuthResponseEntity() {
    }

    public JwtAuthResponseEntity(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
