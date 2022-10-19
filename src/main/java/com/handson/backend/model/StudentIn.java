package com.handson.backend.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Data
public class StudentIn implements Serializable {

    @Length(max = 60)
    private String fullname;

    @Min(100)
    @Max(800)
    private Integer satScore;

    @Min(30)
    @Max(110)
    private Double graduationScore;

    @Length(max = 20)
    private String phone;


    public Student toStudent() {
        return Student.builder().createdAt(new Date()).fullname(fullname)
                .satScore(satScore).graduationScore(graduationScore)
                .phone(phone)
                .build();
    }

    public void updateStudent(Student student) {
        student.setFullname(fullname);
        student.setSatScore(satScore);
        student.setGraduationScore(graduationScore);
        student.setPhone(phone);
    }

}
