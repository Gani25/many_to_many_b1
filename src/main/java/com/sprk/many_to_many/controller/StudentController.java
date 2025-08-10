package com.sprk.many_to_many.controller;

import com.sprk.many_to_many.entity.Course;
import com.sprk.many_to_many.entity.Student;
import com.sprk.many_to_many.repository.CourseRepository;
import com.sprk.many_to_many.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student) {
        student.setCourses(null);
        Student savedStudent =  studentRepository.save(student);

        return savedStudent;
    }

    @PostMapping("/save-course/{rollNo}/{courseId}")
    public Student addCourse(@PathVariable int rollNo, @PathVariable int courseId) {
        Student dbStudent = studentRepository.findById(rollNo).orElse(null);
        Course dbCourse = courseRepository.findById(courseId).orElse(null);
        Student savedStudent = null;
        if (dbStudent != null && dbCourse != null) {
            if (dbStudent.getCourses() == null) {
                dbStudent.setCourses(new ArrayList<>());
            }
           dbStudent.getCourses().add(dbCourse);
            savedStudent =  studentRepository.save(dbStudent);


        }
        return savedStudent;
    }
}
