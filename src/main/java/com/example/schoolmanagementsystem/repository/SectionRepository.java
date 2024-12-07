package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, String> {

    @Query("SELECT s FROM Section s where s.name=:name")
    List<Section> findBySection(@Param("name") String name);
}
