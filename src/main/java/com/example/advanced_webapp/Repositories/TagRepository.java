package com.example.advanced_webapp.Repositories;

import com.example.advanced_webapp.Tables.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
