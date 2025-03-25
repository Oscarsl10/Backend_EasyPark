package com.corhuila.backend_EasyPark.requests;

public class LoginAdminRequest {

    public LoginAdminRequest(){

    }

    public LoginAdminRequest (String userId, String password){
        this.userId = userId;
        this.password = password;
    }

    private String userId;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
