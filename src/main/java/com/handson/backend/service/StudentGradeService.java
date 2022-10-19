package com.handson.backend.service;


import com.handson.backend.model.StudentGrade;
import com.handson.backend.repo.StudentGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentGradeService {

    @Autowired
    StudentGradeRepository repository;

    public Iterable<StudentGrade> all() {
        return repository.findAll();
    }

    public Optional<StudentGrade> findById(Long id) {
        return repository.findById(id);
    }


    public StudentGrade save(StudentGrade studentGrade) {
        return repository.save(studentGrade);
    }

    public void delete(StudentGrade studentGrade) {
        repository.delete(studentGrade);
    }

}
