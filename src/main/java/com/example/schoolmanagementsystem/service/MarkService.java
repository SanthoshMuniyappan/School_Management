package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.MarkRequestDTO;
import com.example.schoolmanagementsystem.dto.RecordValueDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.entity.Exam;
import com.example.schoolmanagementsystem.entity.Mark;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.entity.Student;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.ExamRepository;
import com.example.schoolmanagementsystem.repository.MarkRepository;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.repository.StudentRepository;
import com.example.schoolmanagementsystem.util.Constants;
import jakarta.persistence.Tuple;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarkService {

    private final MarkRepository markRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final StandardRepository standardRepository;

    public MarkService(final StandardRepository standardRepository, final MarkRepository markRepository, final StudentRepository studentRepository, ExamRepository examRepository) {
        this.standardRepository = standardRepository;
        this.markRepository = markRepository;
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public ResponseDTO create(final MarkRequestDTO markRequestDTO) {
        final Student student = this.studentRepository.findById(markRequestDTO.getStudentId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final Exam exam = this.examRepository.findById(markRequestDTO.getExamId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final Standard standard = this.standardRepository.findById(markRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final Mark mark = Mark.builder()
                .student(student)
                .exam(exam)
                .standard(standard)
                .mark(markRequestDTO.getMark())
                .createdBy(markRequestDTO.getCreatedBy())
                .updatedBy(markRequestDTO.getUpdatedBy())
                .build();
        return new ResponseDTO(Constants.CREATED, this.markRepository.save(mark), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final MarkRequestDTO markRequestDTO) {
        final Mark mark = this.markRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));

        if (markRequestDTO.getMark() != null) {
            mark.setMark(markRequestDTO.getMark());
        }
        if (markRequestDTO.getUpdatedBy() != null) {
            mark.setUpdatedBy(markRequestDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.UPDATED, this.markRepository.save(mark), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Mark mark = this.markRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, mark, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.markRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.markRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveMark(final String value) {
        final List<Mark> marks = this.markRepository.findByMarkGreaterThan(value);
        return new ResponseDTO(Constants.RETRIEVED, marks, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveMarkRange(final String minValue, final String maxValue) {
        final List<Mark> marks = this.markRepository.findByMarkRange(minValue, maxValue);
        return new ResponseDTO(Constants.RETRIEVED, marks, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAllByFilter(final String sectionName, final String subjectName, final String teacherName, final String rangeValue, final boolean greaterThan) {
        final List<Tuple> retrieve = this.markRepository.findByAllFiltered(sectionName, subjectName, teacherName, rangeValue, greaterThan);
        final List<RecordValueDTO> userRecords = retrieve.stream()
                .map(tuple -> new RecordValueDTO(
                        tuple.get("sectionName"),
                        tuple.get("subjectName"),
                        tuple.get("teacherName"),
                        tuple.get("mark")
                ))
                .toList();
        return new ResponseDTO(Constants.RETRIEVED, userRecords, HttpStatus.OK.getReasonPhrase());
    }
}
