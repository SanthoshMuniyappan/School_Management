package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class TeacherSectionRequestDTO {

    private String sectionId;
    private String teacherId;
    private String createdBy;
    private String updatedBy;
}
