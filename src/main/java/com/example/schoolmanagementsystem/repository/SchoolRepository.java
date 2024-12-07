package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School,String>, JpaSpecificationExecutor<School> {

}
