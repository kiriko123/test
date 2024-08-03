package com.example.demo2.repository;

import com.example.demo2.entity.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
    List<Student> findByActive(boolean active);
}
