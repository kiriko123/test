package com.example.demo2.controller;

import com.example.demo2.Service.StudentService;
import com.example.demo2.config.DateConfig;
import com.example.demo2.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private void setPage(Model model){
        int page = 0;
        int size = 1;

        long totalRecords = studentService.countAllStudents();

        int totalPages = (int) Math.ceil((double) totalRecords / size);

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
    }

    @GetMapping("/student/form")
    public String studentForm(Model model,  @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "1") int size) {
        //ko l loi
        Student student = new Student();
        model.addAttribute("student", student);

        Pageable pageable = PageRequest.of(page, size);

        model.addAttribute("studentList", studentService.getPages(pageable));

        long totalRecords = studentService.countAllStudents();

        int totalPages = (int) Math.ceil((double) totalRecords / size);

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "student/form";
    }


    @GetMapping("/student/{name}")
    public String getStudent(@PathVariable("name") String name, Model model) {
        Student student = studentService.getStudentById(name);

        model.addAttribute("student", student);

        model.addAttribute("studentList", loadAll());

        //model.addAttribute("messages", "hahahah");

        setPage(model);

        return "student/form";
    }

    private List<Student> loadAll(){
        return studentService.getAllStudents();
    }
    @PostMapping("/student/save")
    public String studentSave(@Validated @ModelAttribute(name = "student") Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            setPage(model);
            return "student/form";
        }

        if (studentService.getStudentById(student.getName()) != null){
            model.addAttribute("messages", "Da ton tai nha");
            model.addAttribute("studentList", loadAll());
            setPage(model);
            return "student/form";
        }

        System.out.println(student.isActive());
        studentService.addStudent(student);

        model.addAttribute("studentList", loadAll());
        model.addAttribute("student", new Student());
        setPage(model);
        return "student/form";
    }
    @PostMapping("/student/update")
    public String studentUpdate(@Validated @ModelAttribute(name = "student") Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            setPage(model);
            return "student/form";
        }

        if (studentService.getStudentById(student.getName()) == null){
            model.addAttribute("messages", "ko co nha");
            model.addAttribute("studentList", loadAll());
            setPage(model);
            return "student/form";
        }

        model.addAttribute("studentList", loadAll());
        model.addAttribute("student", studentService.updateStudent(student));
        setPage(model);
        return "student/form";
    }
    @PostMapping("/student/{name}")
    public String studentDelete(@PathVariable("name") String name, Model model) {
        if (studentService.getStudentById(name) == null){
            model.addAttribute("messages", "ko co nha");
            model.addAttribute("studentList", loadAll());
            setPage(model);
            return "student/form";
        }
        studentService.deleteStudent(name);

        model.addAttribute("studentList", loadAll());
        model.addAttribute("student", new Student());
        setPage(model);
        return "student/form";
    }
    @PostMapping("/student/find")
    public String studentFind(@RequestParam("keyword") String keyword, Model model) {
        if(!keyword.isBlank()){
            model.addAttribute("studentList", studentService.findByNameLike(keyword, keyword));
        }else{
            model.addAttribute("studentList", loadAll());
        }
        model.addAttribute("student", new Student());
        model.addAttribute("kw", keyword);
        setPage(model);
        return "student/form";
    }

    @PostMapping("/student/filter")
    public String studentFilter(@RequestParam("keyword1") boolean keyword, Model model) {
        model.addAttribute("studentList", studentService.findByActive(keyword));
        model.addAttribute("student", new Student());
        model.addAttribute("kw1", keyword);
        setPage(model);
        return "student/form";
    }

    @PostMapping("/student/sort")
    public String studentSort(@RequestParam("sort") boolean sort, Model model) {

        Sort sortList = Sort.by(sort ? Sort.Direction.ASC : Sort.Direction.DESC, "marks");

        model.addAttribute("studentList", studentService.sortByMarks(sortList));
        model.addAttribute("student", new Student());

        model.addAttribute("sort", sort);
        setPage(model);
        return "student/form";
    }


}
