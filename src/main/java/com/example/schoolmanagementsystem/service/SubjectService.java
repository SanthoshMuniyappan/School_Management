package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SubjectRequestDTO;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.entity.Subject;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.repository.SubjectRepository;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StandardRepository standardRepository;

    public SubjectService(final SubjectRepository subjectRepository, final StandardRepository standardRepository) {
        this.subjectRepository = subjectRepository;
        this.standardRepository = standardRepository;
    }

    @Transactional
    public ResponseDTO create(final SubjectRequestDTO subjectRequestDTO) {
        final Standard standard = this.standardRepository.findById(subjectRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final Subject subject = Subject.builder()
                .standard(standard)
                .subjectName(subjectRequestDTO.getSubjectName())
                .build();
        return new ResponseDTO(Constants.CREATED, this.subjectRepository.save(subject), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final SubjectRequestDTO subjectRequestDTO) {
        final Subject subject = this.subjectRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        if (subjectRequestDTO.getSubjectName() != null) {
            subject.setSubjectName(subjectRequestDTO.getSubjectName());
        }
        if (subjectRequestDTO.getUpdatedBy() != null) {
            subject.setUpdatedBy(subjectRequestDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.UPDATED, this.subjectRepository.save(subject), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.subjectRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.subjectRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED,id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Subject subject = this.subjectRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, subject, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.subjectRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveSubject(final String subject) {
        final List<Subject> subjects = this.subjectRepository.findBySubject(subject);
        return new ResponseDTO(Constants.RETRIEVED, subjects, HttpStatus.OK.getReasonPhrase());
    }
}
