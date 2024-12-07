package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ExamRequestDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.entity.Exam;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.entity.Subject;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.ExamRepository;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.repository.SubjectRepository;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final StandardRepository standardRepository;
    private final SubjectRepository subjectRepository;

    public ExamService(final ExamRepository examRepository, final StandardRepository standardRepository, final SubjectRepository subjectRepository) {
        this.examRepository = examRepository;
        this.standardRepository = standardRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public ResponseDTO create(final ExamRequestDTO examRequestDTO) {
        final Standard standard = this.standardRepository.findById(examRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final Subject subject = this.subjectRepository.findById(examRequestDTO.getSubjectId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final Exam exam = Exam.builder()
                .subject(subject)
                .standard(standard)
                .name(examRequestDTO.getName())
                .date(examRequestDTO.getDate())
                .createdBy(examRequestDTO.getCreatedBy())
                .updatedBy(examRequestDTO.getUpdatedBy())
                .build();
        return new ResponseDTO(Constants.CREATED, this.examRepository.save(exam), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final ExamRequestDTO examRequestDTO) {
        final Exam exam = this.examRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));

        if (examRequestDTO.getName() != null) {
            exam.setName(examRequestDTO.getName());
        }
        if (examRequestDTO.getDate() != null) {
            exam.setDate(examRequestDTO.getDate());
        }
        if (examRequestDTO.getUpdatedBy() != null) {
            exam.setUpdatedBy(examRequestDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.UPDATED, this.examRepository.save(exam), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Exam exam = this.examRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, exam, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.examRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.examRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.examRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED,id, HttpStatus.OK.getReasonPhrase());
    }
}
