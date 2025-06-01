package com.smartmanager.repositories;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     Page<contact> findByuser(user user,Pageable pageable);

     
    // //  custom query to find contacts by user ID
    // @Query("SELECT c FROM Contact c WHERE c.user.userId = :userId")
    // List<contact> findByUserId(@Param("userId") String userId);


    //  custom method to search contacts by name
    Page<contact> findByUserAndNameContainingIgnoreCase(user user,String name, Pageable pageable); // here after findBy, the filed name should be in camelCase as per JPA naming conventions irrespective of the field name in the entity class
    //  custom method to search contacts by email   
    Page<contact> findByuserAndEmailContainingIgnoreCase(user user,String email,Pageable pageable);
    //  custom method to search contacts by phone
    Page<contact> findByuserAndPhoneContainingIgnoreCase(user user,String phone,Pageable pageable);
    

    

}
