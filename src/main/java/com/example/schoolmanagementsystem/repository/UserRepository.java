package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,String> {
     Optional<UserInfo> findByUserName(String userName);
}
