package com.example.registrationsecurity.repository;

import com.example.registrationsecurity.entity.RegistrationTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationTable, Long>, CrudRepository<RegistrationTable, Long> {

    Optional<RegistrationTable> findByEmail(String email);
    Optional<RegistrationTable> findByUsername(String username);
}
