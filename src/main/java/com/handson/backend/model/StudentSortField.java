package com.handson.backend.model;

public enum StudentSortField {
    id("id") ,
    createdAt ("created_at"),
    fullName ("fullname"),
    satScore ("sat_score"),
    graduationScore ("graduation_score"),
    phone ("phone"),
    profilepicture ("profile_picture");

    public final String fieldName;
    private StudentSortField(String fieldName) {
        this.fieldName = fieldName;
    }
}
