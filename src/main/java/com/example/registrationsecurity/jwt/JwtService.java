package com.example.registrationsecurity.jwt;

import com.example.registrationsecurity.entity.RegistrationTable;
import com.example.registrationsecurity.repository.RegistrationRepository;
import com.example.registrationsecurity.service.RegistrationService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtService implements UserDetailsService {

    private final JwtUnit jwtUnit;
    private final RegistrationRepository registrationRepository;

    public JwtService(JwtUnit jwtUnit, RegistrationRepository registrationRepository) {
        this.jwtUnit = jwtUnit;
        this.registrationRepository = registrationRepository;
    }


    public String getUsername(String token) {
        return jwtUnit.extractUsername(token);
    }

    public List<String> extractRoles(String token) {
        return jwtUnit.extractRoles(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegistrationTable registrationTable = registrationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found username" + username));

        return new User(registrationTable.getUsername(), registrationTable.getPassword(), new ArrayList<>());
    }
}
