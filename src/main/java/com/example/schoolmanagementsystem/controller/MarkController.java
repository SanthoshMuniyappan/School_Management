package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.MarkRequestDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.service.MarkService;
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
@RequestMapping("/api/mark")
public class MarkController {

    private final MarkService markService;

    public MarkController(final MarkService markService) {
        this.markService = markService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final MarkRequestDTO markRequestDTO) {
        return this.markService.create(markRequestDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody final MarkRequestDTO markRequestDTO) {
        return this.markService.update(id, markRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.markService.retrieve(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.markService.remove(id);
    }

    @GetMapping("/retrieve-mark")
    public ResponseDTO retrieveMark(@RequestParam(required = false) final String value) {
        return this.markService.retrieveMark(value);
    }

    @GetMapping("/retrieve-mark-range")
    public ResponseDTO retrieveMarkRange(@RequestParam(required = false) final String minValue, @RequestParam(required = false) final String maxValue) {
        return this.markService.retrieveMarkRange(minValue, maxValue);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveByAllFilter(@RequestParam(required = false) final String sectionName,
                                           @RequestParam(required = false) final String subjectName,
                                           @RequestParam(required = false) final String teacherName,
                                           @RequestParam(required = false) final String rangeValue,
                                           @RequestParam(required = false) final boolean greaterThan) {
        return this.markService.retrieveAllByFilter(sectionName, subjectName, teacherName, rangeValue, greaterThan);
    }
}
