package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class MarkRequestDTO {

    private String mark;
    private String studentId;
    private String examId;
    private String standardId;
    private String createdBy;
    private String updatedBy;
}
