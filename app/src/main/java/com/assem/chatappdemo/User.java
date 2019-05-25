package com.assem.chatappdemo;

public class User {

    private String id;
    private String email;
    private String password;
    private String imgUrl;

    public User() {
    }

    public User(String id, String email, String password, String imgUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.imgUrl = imgUrl;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
