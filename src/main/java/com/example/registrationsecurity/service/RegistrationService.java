package com.example.registrationsecurity.service;

import com.example.registrationsecurity.dto.LoginDto;
import com.example.registrationsecurity.dto.RegistrationDto;
import com.example.registrationsecurity.entity.RegistrationTable;
import com.example.registrationsecurity.enums.Roles;
import com.example.registrationsecurity.jwt.JwtUnit;
import com.example.registrationsecurity.payload.ApiResponse;
import com.example.registrationsecurity.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUnit jwtUnit;

    public ApiResponse registrationUser(RegistrationDto registrationDto) {

        // email tekshirish qismi
        Optional<RegistrationTable> checkEmail = registrationRepository.findByEmail(registrationDto.getEmail());
        if (checkEmail.isPresent()) {
            return new ApiResponse("This is email address already registration", false);
        }

        // Usernameni tekshrish qismi
        Optional<RegistrationTable> checkUsername = registrationRepository.findByUsername(registrationDto.getUsername());
        if (checkUsername.isPresent()) {
            return new ApiResponse("This is username already registration!", false);
        }

        // Passwordni teckshirish qismi
        if (!registrationDto.getPassword().equals(registrationDto.getConfPass())) {
            return new ApiResponse("This password do not match!", false);
        }

        // Foyda;anuvchini yaratish qimi
        RegistrationTable registrationTable = new RegistrationTable();
        registrationTable.setUsername(registrationDto.getUsername());
        registrationTable.setEmail(registrationDto.getEmail());
        registrationTable.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        registrationTable.setConfPass(registrationDto.getConfPass());

        Roles roles = registrationDto.getRoles();
        if (roles != null) {
            registrationTable.setRoles(roles);
        } else {
            registrationTable.setRoles(Roles.USER);
        }


        registrationRepository.save(registrationTable);

        return new ApiResponse("Registration!", true);
    }



    // Tizimga kirish qismini shakllanirish qismi
    public ApiResponse loginUser(LoginDto loginDto) {

        // foydalanuvchini username yoki email orqali topish
        Optional<RegistrationTable> findUserOrMail = registrationRepository.findByEmail(loginDto.getLogin());
        if (findUserOrMail.isEmpty()) {
            findUserOrMail = registrationRepository.findByUsername(loginDto.getLogin());
        }

        if (findUserOrMail.isEmpty()) {
            return new ApiResponse("Foydalanuvchi topilmadi, Ro'yxatdan o'ting!", false);
        }

        RegistrationTable registrationTable = findUserOrMail.get();
        if (!passwordEncoder.matches(loginDto.getParol(), registrationTable.getPassword())) {
            return new ApiResponse("Parol noto'g'ri", false);
        }

        String token = jwtUnit.generateToken(registrationTable, List.of(registrationTable.getRoles().name()));
        return new ApiResponse("Kirish muvaffaqiyatli amalga oshirildi!" ,true, token);
    }
}
