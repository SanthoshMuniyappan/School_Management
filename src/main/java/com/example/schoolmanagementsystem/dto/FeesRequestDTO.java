package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class FeesRequestDTO {

    private String totalFees;
    private String paidFees;
    private String unpaidFees;
    private String studentId;
    private String createdBy;
    private String updatedBy;
}
