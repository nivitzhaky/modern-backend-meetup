package com.handson.backend.repo;


import com.handson.backend.model.StudentGrade;
import org.springframework.data.repository.CrudRepository;

public interface StudentGradeRepository extends CrudRepository<StudentGrade,Long> {

}
