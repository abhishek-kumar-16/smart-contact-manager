package com.smartmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartmanager.entities.contact;
import com.smartmanager.entities.user;

@Repository
public interface ContactRepo extends JpaRepository<contact, String> {
    // This interface will automatically provide CRUD operations for Contact entities
    // Additional custom query methods can be defined here if needed


    //  custom method defined here to find contacts by user
     List<contact> findByuser(user user);


    //  custom query to find contacts by user ID
     @Query("SELECT c FROM contact c WHERE c.user.userId = :Id")
     List<contact> findByUserID(@Param("Id") String Id);
}
