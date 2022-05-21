package com.example.chatapp_cloud.models;

import java.io.Serializable;

public class Student implements Serializable {
    public String Firstname,Birthday,MobileNum,image,email,token,id;

    public Student() {

    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public Student(String firstname, String birthday, String mobileNum, String image, String email) {
        Firstname = firstname;
        Birthday = birthday;
        MobileNum = mobileNum;
        this.image = image;
        this.email = email;

    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getMobileNum() {
        return MobileNum;
    }

    public void setMobileNum(String mobileNum) {
        MobileNum = mobileNum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
