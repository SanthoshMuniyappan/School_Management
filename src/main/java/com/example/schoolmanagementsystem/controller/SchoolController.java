package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SchoolRequestDTO;
import com.example.schoolmanagementsystem.service.SchoolService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(final SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final SchoolRequestDTO schoolRequestDTO) {
        return this.schoolService.create(schoolRequestDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody final SchoolRequestDTO schoolRequestDTO) {
        return this.schoolService.update(id, schoolRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.schoolService.retrieve(id);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieveAll() {
        return this.schoolService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.schoolService.remove(id);
    }

    @GetMapping("/search")
    public ResponseDTO search(@RequestParam(required = false) String value) {
        return this.schoolService.search(value);
    }

}
