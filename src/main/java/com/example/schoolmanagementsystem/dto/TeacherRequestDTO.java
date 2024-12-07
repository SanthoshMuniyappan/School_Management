package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class TeacherRequestDTO {
    private String name;
    private String joinOfDate;
    private String address;
    private String gender;
    private String salary;
    private String phoneNo;
    private String standardId;
    private String knownSubject;
    private String createdBy;
    private String updatedBy;
}
