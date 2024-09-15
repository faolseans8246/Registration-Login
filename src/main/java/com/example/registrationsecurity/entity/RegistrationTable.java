package com.example.registrationsecurity.entity;


import com.example.registrationsecurity.enums.Roles;
import com.example.registrationsecurity.template.IpNotes;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "registrations")
public class RegistrationTable extends IpNotes {

    private String username;
    private String email;
    private String password;
    private String confPass;

//    @Enumerated(EnumType.STRING)
    private Roles roles;
}
