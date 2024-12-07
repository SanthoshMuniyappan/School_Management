package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {

    @Query("SELECT t FROM Teacher t WHERE t.name=:name")
    List<Teacher> findByName(@Param("name") String name);
}
