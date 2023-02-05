package com.example.advanced_webapp.Repositories;

import com.example.advanced_webapp.Tables.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);

    User findUserByEmail(String email);
    Optional<User> findByEmail(String email);
    void deleteByUserId(String userId);
    void deleteUsersByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.verified = TRUE WHERE u.email = ?1")
    int verifyUser(String email);
}
