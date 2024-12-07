package com.example.schoolmanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO {
    private String message;
    private Object data;
    private String status;

    public ResponseDTO(String message, Object data, String status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
