package com.example.chatapp_cloud.models;

public class CourseInfo {
    private String courseid;
    private String courseimage;
    private String coursename;
    private String coursetype;
    private boolean join = false;
    private int isJoined = 0;

    public CourseInfo(String image, String name, String type, String id, int join) {
    }


    public int getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(int isJoined) {
        this.isJoined = isJoined;
    }

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }

    public CourseInfo(){
    }

    public String getCourseId() {
        return courseid;
    }

    public void setCourseId(String courseId) {
        this.courseid = courseId;
    }

    public CourseInfo(String courseimage, String coursename, String coursetype, String courseid) {
        this.courseimage = courseimage;
        this.coursename = coursename;
        this.coursetype = coursetype;
        this.courseid = courseid;
    }


    public CourseInfo(String courseimage, String coursename, String coursetype, boolean join) {
        this.courseimage = courseimage;
        this.coursename = coursename;
        this.coursetype = coursetype;
        this.join = join;
    }

    public String getCourseimage() {
        return courseimage;
    }

    public void setCourseimage(String courseimage) {
        this.courseimage = courseimage;
    }

    public String getCourseName() {
        return coursename;
    }

    public void setCourseName(String courseName) {
        this.coursename = courseName;
    }

    public String getCourseType() {
        return coursetype;
    }

    public void setCourseType(String courseType) {
        this.coursetype = courseType;
    }
}
