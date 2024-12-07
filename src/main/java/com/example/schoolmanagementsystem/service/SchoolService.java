package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SchoolRequestDTO;
import com.example.schoolmanagementsystem.entity.School;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.SchoolRepository;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Transactional
    public ResponseDTO create(final SchoolRequestDTO schoolRequestDTO) {
        final School school = School.builder()
                .name(schoolRequestDTO.getName())
                .createdBy(schoolRequestDTO.getCreatedBy())
                .updatedBy(schoolRequestDTO.getUpdatedBy())
                .build();
        return new ResponseDTO(Constants.CREATED, schoolRepository.save(school), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final SchoolRequestDTO schoolRequestDTO) {
        final School school = this.schoolRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));

        if (schoolRequestDTO.getName() != null) {
            school.setName(schoolRequestDTO.getName());
        }
        if (schoolRequestDTO.getUpdatedBy() != null) {
            school.setUpdatedBy(schoolRequestDTO.getUpdatedBy());
        }

        return new ResponseDTO(Constants.UPDATED, this.schoolRepository.save(school), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.schoolRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.schoolRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED,id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final School school = this.schoolRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, school, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.schoolRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public Specification<School> searchByAll(final String value) {
        return (root, query, criteriaBuilder) -> {
            if (value.isEmpty()) {
                throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
            }
            return criteriaBuilder.or(criteriaBuilder.like(root.get("id"), "%" + value + "%")
                    , criteriaBuilder.like(root.get("name"), "%" + value + "%")
                    , criteriaBuilder.like(root.get("createdBy"), "%" + value + "%")
                    , criteriaBuilder.like(root.get("updatedBy"), "%" + value + "%"));
        };
    }

    public ResponseDTO search(final String value) {
        final Specification<School> school = searchByAll(value);
        final List<School> students = this.schoolRepository.findAll(school);
        return new ResponseDTO(Constants.FOUND, students, HttpStatus.OK.getReasonPhrase());
    }
}
