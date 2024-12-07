package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM subject where subject_name=:subject")
    List<Subject> findBySubject(@Param("subject") String subject);
}
