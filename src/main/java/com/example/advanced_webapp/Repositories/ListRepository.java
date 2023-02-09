package com.example.advanced_webapp.Repositories;

import com.example.advanced_webapp.Tables.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListRepository extends JpaRepository<List, Long> {
    void deleteByName(String name);
    Optional<List> findByName(String name);
    List findListByName(String listName);
}
