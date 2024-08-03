package com.example.demo2.Service.Impl;

import com.example.demo2.Service.StudentService;
import com.example.demo2.entity.Student;
import com.example.demo2.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> findByNameLike(String name, String email) {
        return studentRepository.findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email);
    }

    @Override
    public List<Student> findByActive(boolean active) {
        return studentRepository.findByActive(active);
    }

    @Override
    public List<Student> sortByMarks(Sort sort) {
        return studentRepository.findAll(sort);
    }

    @Override
    public List<Student> getPages(Pageable pageable) {
        return studentRepository.findAll(pageable).getContent();
    }

    @Override
    public long countAllStudents() {
        return studentRepository.count();
    }
}
