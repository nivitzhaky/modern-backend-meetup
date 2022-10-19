package com.handson.backend.controller;

import com.handson.backend.model.GradeIn;
import com.handson.backend.model.Student;
import com.handson.backend.service.StudentService;

import com.handson.backend.model.StudentGrade;
import com.handson.backend.service.StudentGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/students")
public class StudentsGradesController {
    @Autowired
    StudentService studentService;

    @Autowired
    StudentGradeService studentGradeService;



    @RequestMapping(value = "/{studentId}/grades", method = RequestMethod.POST)
    public ResponseEntity<?> insertStudentGrade(Long studentId,  @RequestBody GradeIn gradeIn)
    {
        var student = studentService.findById(studentId);
        if (student.isEmpty()) throw new RuntimeException("Student:" + studentId +" not found");
        StudentGrade studentGrade = gradeIn.toGrade(student.get());
        studentGrade = studentGradeService.save(studentGrade);
        return new ResponseEntity<>(studentGrade, HttpStatus.OK);
    }

    @RequestMapping(value = "/{studentId}/grades/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId, @PathVariable Long id, @RequestBody GradeIn gradeIn)
    {
        Optional<Student> dbStudent = studentService.findById(studentId);
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + studentId + " not found");

        Optional<StudentGrade> dbStudentGrade = studentGradeService.findById(id);
        if (dbStudentGrade.isEmpty()) throw new RuntimeException("Student grade with id: " + id + " not found");

        gradeIn.updateStudentGrade(dbStudentGrade.get());
        StudentGrade updatedStudentGrade = studentGradeService.save(dbStudentGrade.get());
        return new ResponseEntity<>(updatedStudentGrade, HttpStatus.OK);
    }

    @RequestMapping(value = "/{studentId}/grades/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudentGrade(@PathVariable Long studentId, @PathVariable Long id)
    {
        Optional<Student> dbStudent = studentService.findById(studentId);
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + studentId + " not found");

        Optional<StudentGrade> dbStudentGrade = studentGradeService.findById(id);
        if (dbStudentGrade.isEmpty()) throw new RuntimeException("Student grade with id: " + id + " not found");

        studentGradeService.delete(dbStudentGrade.get());
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }
}
