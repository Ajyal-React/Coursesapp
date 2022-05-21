package com.example.chatapp_cloud.lecturer.model;

public class LecturerModel {
    public String lectureremail;
    public String lecturername;
    public String lecturerbirth;
    public String lecturerImage;
    public String lecturermobile;

    public String getLecturername() {
        return lecturername;
    }

    public void setLecturername(String lecturername) {
        this.lecturername = lecturername;
    }

    public String getLecturerbirth() {
        return lecturerbirth;
    }

    public void setLecturerbirth(String lecturerbirth) {
        this.lecturerbirth = lecturerbirth;
    }

    public String getLecturerImage() {
        return lecturerImage;
    }

    public void setLecturerImage(String lecturerImage) {
        this.lecturerImage = lecturerImage;
    }

    public String getLecturermobile() {
        return lecturermobile;
    }

    public void setLecturermobile(String lecturermobile) {
        this.lecturermobile = lecturermobile;
    }

    public LecturerModel(String lectureremail, String lecturername, String lecturerbirth, String lecturerImage, String lecturermobile) {
        this.lectureremail = lectureremail;
        this.lecturername = lecturername;
        this.lecturerbirth = lecturerbirth;
        this.lecturerImage = lecturerImage;
        this.lecturermobile = lecturermobile;
    }

    public LecturerModel(String email) {
        this.lectureremail = email;
    }
//    public String lecturerpass;

//    public LecturerModel(String email) {
////        this.lecturername = name;
//        this.lectureremail = email;
//    }


    public String getLectureremail() {
        return lectureremail;
    }

    public void setLectureremail(String lectureremail) {
        this.lectureremail = lectureremail;
    }
}