package com.example.advanced_webapp.Repositories;

import com.example.advanced_webapp.Tables.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    Optional<User> findByEmail(String email);
    void deleteByUserId(String userId);
}
