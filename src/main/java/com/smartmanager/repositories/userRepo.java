package com.smartmanager.repositories;
//  to interacts with the database


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmanager.entities.user;
public interface userRepo  extends JpaRepository<user, String> {
     Optional<user> findByEmail(String email);
}
