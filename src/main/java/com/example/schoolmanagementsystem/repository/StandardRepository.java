package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardRepository extends JpaRepository<Standard, String> {
}
