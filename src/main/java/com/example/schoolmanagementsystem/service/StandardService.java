package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.StandardRequestDTO;
import com.example.schoolmanagementsystem.entity.School;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.SchoolRepository;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StandardService {

    private final StandardRepository standardRepository;
    private final SchoolRepository schoolRepository;

    public StandardService(final StandardRepository standardRepository, final SchoolRepository schoolRepository) {
        this.standardRepository = standardRepository;
        this.schoolRepository = schoolRepository;
    }

    @Transactional
    public ResponseDTO create(final StandardRequestDTO standardRequestDTO) {
        final School school = this.schoolRepository.findById(standardRequestDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final Standard standard = Standard.builder()
                .school(school)
                .name(standardRequestDTO.getName())
                .fees(standardRequestDTO.getFees())
                .createdBy(standardRequestDTO.getCreatedBy())
                .updatedBy(standardRequestDTO.getUpdatedBy())
                .build();
        return new ResponseDTO(Constants.CREATED, this.standardRepository.save(standard), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final StandardRequestDTO standardRequestDTO) {
        final Standard standard = this.standardRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));

        if (standardRequestDTO.getName() != null) {
            standard.setName(standardRequestDTO.getName());
        }
        if (standardRequestDTO.getFees() != null) {
            standard.setFees(standardRequestDTO.getFees());
        }
        if (standardRequestDTO.getUpdatedBy() != null) {
            standard.setUpdatedBy(standardRequestDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.UPDATED, this.standardRepository.save(standard), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Standard standard = this.standardRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, standard, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.standardRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.standardRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.standardRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED,id, HttpStatus.OK.getReasonPhrase());
    }
}
