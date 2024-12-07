package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.TeacherSectionRequestDTO;
import com.example.schoolmanagementsystem.service.TeacherSectionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacherSection")
public class TeacherSectionController {

    private final TeacherSectionService teacherSectionService;

    public TeacherSectionController(final TeacherSectionService studentSectionService) {
        this.teacherSectionService = studentSectionService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final TeacherSectionRequestDTO teacherSectionRequestDTO) {
        return this.teacherSectionService.create(teacherSectionRequestDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody final TeacherSectionRequestDTO teacherSectionRequestDTO) {
        return this.teacherSectionService.update(id, teacherSectionRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.teacherSectionService.remove(id);
    }
}
