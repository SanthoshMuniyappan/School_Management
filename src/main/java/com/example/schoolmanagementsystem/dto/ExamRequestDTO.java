package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class ExamRequestDTO {

    private String name;
    private String date;
    private String standardId;
    private String subjectId;
    private String createdBy;
    private String updatedBy;
}
