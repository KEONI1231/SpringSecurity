package com.example.SpringJwtStudyOnYoutube.repository;

import com.example.SpringJwtStudyOnYoutube.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Boolean existsByUserName(String username);

    Users findByUserName(String userName);

}
