package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, String> {
}
