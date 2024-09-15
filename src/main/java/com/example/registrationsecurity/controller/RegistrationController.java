package com.example.registrationsecurity.controller;

import com.example.registrationsecurity.dto.LoginDto;
import com.example.registrationsecurity.dto.RegistrationDto;
import com.example.registrationsecurity.payload.ApiResponse;
import com.example.registrationsecurity.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/registration")
    public ResponseEntity<ApiResponse> registrationUser(@RequestBody RegistrationDto registrationDto) {
        ApiResponse apiResponse = registrationService.registrationUser(registrationDto);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = registrationService.loginUser(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

}
