package com.example.schoolmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @ManyToOne
    private Standard standard;

    @ManyToOne
    private Subject subject;

    @Column(name = "created_by")
    private String createdBy;

    @CurrentTimestamp
    private String createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @UpdateTimestamp
    private String updatedAt;
}