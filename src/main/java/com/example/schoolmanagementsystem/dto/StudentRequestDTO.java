package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class StudentRequestDTO {
    private String name;
    private String dateOfBirth;
    private String address;
    private String email;
    private String phoneNo;
    private String sectionId;
    private String createdBy;
    private String updatedBy;
}
