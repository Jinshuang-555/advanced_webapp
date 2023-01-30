package com.example.advanced_webapp.Repositories;

import com.example.advanced_webapp.Tables.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
