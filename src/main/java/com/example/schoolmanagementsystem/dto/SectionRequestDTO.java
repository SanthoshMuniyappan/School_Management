package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class SectionRequestDTO {
    private String name;
    private String standardId;
    private String maximumCapacity;
    private String createdBy;
    private String updatedBy;

}
