package com.burhan.userorg.Entity;

public class JwtAuthRequestEntity {

    private String userName;
    private String password;

    public JwtAuthRequestEntity() {
    }

    public JwtAuthRequestEntity(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
