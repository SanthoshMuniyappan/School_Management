package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.TeacherSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherSectionRepository extends JpaRepository<TeacherSection, String> {
}
