package com.example.advanced_webapp.Repositories;

import com.example.advanced_webapp.Tables.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
}
