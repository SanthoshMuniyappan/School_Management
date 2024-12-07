package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class StandardRequestDTO {
    private String name;
    private String schoolId;
    private String fees;
    private String createdBy;
    private String updatedBy;
}
