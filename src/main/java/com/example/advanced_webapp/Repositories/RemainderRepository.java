package com.example.advanced_webapp.Repositories;

import com.example.advanced_webapp.Tables.Remainder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemainderRepository extends JpaRepository<Remainder, Long> {
}
