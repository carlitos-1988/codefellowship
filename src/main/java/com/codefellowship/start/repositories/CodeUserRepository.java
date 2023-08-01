package com.codefellowship.start.repositories;

import com.codefellowship.start.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeUserRepository extends JpaRepository<ApplicationUser,Long > {
    //defaults: save(), delete(), update(), findAll()

    public ApplicationUser findByUserName(String username);
}
