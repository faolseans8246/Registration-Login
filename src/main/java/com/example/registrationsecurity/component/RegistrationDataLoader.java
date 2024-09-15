package com.example.registrationsecurity.component;


import com.example.registrationsecurity.entity.RegistrationTable;
import com.example.registrationsecurity.enums.Roles;
import com.example.registrationsecurity.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationDataLoader implements CommandLineRunner {

    private final RegistrationRepository registrationRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) {
        if (initMode.equals("always")) {
            RegistrationTable registrationTable = new RegistrationTable();
            registrationTable.setUsername("login");
            registrationTable.setEmail("feruzbekhamrayev2002@gmail.com");
            registrationTable.setPassword(passwordEncoder.encode("parol"));
            registrationTable.setConfPass("parol");
            registrationTable.setRoles(Roles.USER);

            registrationRepository.save(registrationTable);
        }
    }
}
