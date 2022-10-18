package com.handson.backend.model;

public enum StudentSortField {
    id("s.id") ,
    createdAt ("s.created_at"),
    fullName ("s.fullname"),
    satScore ("s.at_score"),
    graduationScore ("s.graduation_score"),
    phone ("s.phone"),
    profilepicture ("s.profile_picture"),
    avgScore (" (select avg(sg.course_score) from  student_grade sg where sg.student_id = s.id ) ");
    public final String fieldName;
    private StudentSortField(String fieldName) {
        this.fieldName = fieldName;
    }
}
