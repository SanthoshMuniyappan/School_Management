//package com.example.schoolmanagementsystem.controller;
//
//import com.example.schoolmanagementsystem.dto.ResponseDTO;
//import com.example.schoolmanagementsystem.service.CustomService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/custom")
//public class CustomController {
//
//    private final CustomService customService;
//
//    public CustomController(final CustomService customService){
//        this.customService=customService;
//    }
//    @GetMapping("/retrieve")
//    public ResponseDTO retrieveAll(@RequestParam(required = false) final String sectionName,
//                                   @RequestParam(required = false) final String subjectName,
//                                   @RequestParam(required = false) final String teacherName,
//                                   @RequestParam(required = false) final String mark,
//                                   @RequestParam(required = false) final String greaterThanOr,
//                                   @RequestParam(required = false) final String lessThanOr,
//                                   @RequestParam(required = false) final String minValue,
//                                   @RequestParam(required = false) final String maxValue){
//        return this.customService.retrieveAll(sectionName,subjectName,teacherName,mark,greaterThanOr,lessThanOr,minValue,maxValue);
//    }
//}
