package com.example.chatapp_cloud.lecturer.model;

public class LecturerModel {
    public String lectureremail;

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