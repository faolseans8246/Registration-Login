package com.example.registrationsecurity.dto;

import com.example.registrationsecurity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Roles roles;
    private String confPass;
}
