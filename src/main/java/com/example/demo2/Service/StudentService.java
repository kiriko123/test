package com.example.demo2.Service;

import com.example.demo2.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StudentService {
    Student getStudentById(String id);
    List<Student> getAllStudents();
    Student addStudent(Student student);
    Student updateStudent(Student student);
    void deleteStudent(String id);
    List<Student> findByNameLike(String name, String email);
    List<Student> findByActive(boolean active);

    List<Student> sortByMarks(Sort sort);

    List<Student> getPages(Pageable pageable);

    long countAllStudents();
}
