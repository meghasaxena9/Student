package org.launchcode.library.controllers;

import jakarta.validation.Valid;
import org.launchcode.library.data.StudentRepository;
import org.launchcode.library.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping ("students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("add")
    public String renderCreateStudentForm(Model model){
        model.addAttribute("title", "Create Student");
        model.addAttribute(new Student());
        model.addAttribute("categories", studentRepository.findAll());
        return "student/add";
    }

    @PostMapping("add")
    public String createEvent(@ModelAttribute @Valid Student newEvent, Errors errors, Model model){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Student");
            return "students/add";
        }
        studentRepository.save(newEvent);
        return "redirect:/students";

    }

    @GetMapping ("delete")
    public String displayDeleteStudentForm (Model model){
        model.addAttribute("title", "Delete Student");
        model.addAttribute("events", studentRepository.findAll());
        return "student/delete";
    }

    @PostMapping ("delete")
    public String processDeleteEvent(@RequestParam(required = false) int[] studentIds){
        if (studentIds != null)
        {
            for (int id : studentIds) {
                studentRepository.deleteById(id);
            }
        }
        return "redirect:/students";

    }
}
