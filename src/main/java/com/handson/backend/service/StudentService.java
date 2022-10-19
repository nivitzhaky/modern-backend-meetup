package com.handson.backend.service;

import com.handson.backend.model.Student;
import com.handson.backend.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository repository;

    public Iterable<Student> all() {
        return repository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }


    public Student save(Student student) {
        return repository.save(student);
    }

    public void delete(Student student) {
        repository.delete(student);
    }

}
