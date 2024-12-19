package com.example.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDTO {

    @NotNull
    private String userName;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String roles;
}
