package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.StudentRequestDTO;
import com.example.schoolmanagementsystem.entity.Section;
import com.example.schoolmanagementsystem.entity.Student;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.SectionRepository;
import com.example.schoolmanagementsystem.repository.StudentRepository;
import com.example.schoolmanagementsystem.util.Constants;
import com.example.schoolmanagementsystem.util.UtilService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;

    public StudentService(final StudentRepository studentRepository, final SectionRepository sectionRepository) {
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public ResponseDTO create(final StudentRequestDTO studentRequestDTO) {
        final Section section = this.sectionRepository.findById(studentRequestDTO.getSectionId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final String email = checkEmailExist(studentRequestDTO);
        final String phoneNo = checkPhoneNoValid(studentRequestDTO);
        final Student student = Student.builder()
                .section(section)
                .name(studentRequestDTO.getName())
                .dateOfBirth(studentRequestDTO.getDateOfBirth())
                .address(studentRequestDTO.getAddress())
                .email(email)
                .phoneNo(phoneNo)
                .createdBy(studentRequestDTO.getCreatedBy())
                .updatedBy(studentRequestDTO.getUpdatedBy())
                .build();
        return new ResponseDTO(Constants.CREATED, this.studentRepository.save(student), HttpStatus.CREATED.getReasonPhrase());
    }

    public String checkEmailExist(final StudentRequestDTO studentRequestDTO) {
        if (UtilService.emailValidation(studentRequestDTO.getEmail())) {
            List<Student> student = this.studentRepository.findByEmail(studentRequestDTO.getEmail());
            if (student.isEmpty()) {
                return studentRequestDTO.getEmail();
            } else {
                throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
            }
        } else {
            throw new BadRequestServiceAlertException(Constants.EMAIL_NOT_VALID + Constants.EMAIL_PATTERN);
        }
    }

    public String checkPhoneNoValid(final StudentRequestDTO studentRequestDTO) {
        if (UtilService.phoneNoValidation(studentRequestDTO.getPhoneNo())) {
            return studentRequestDTO.getPhoneNo();
        } else {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
    }

    @Transactional
    public ResponseDTO update(final String id, final StudentRequestDTO studentRequestDTO) {
        final Student student = this.studentRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));

        if (studentRequestDTO.getName() != null) {
            student.setName(studentRequestDTO.getName());
        }
        if (studentRequestDTO.getDateOfBirth() != null) {
            student.setDateOfBirth(studentRequestDTO.getDateOfBirth());
        }
        if (studentRequestDTO.getAddress() != null) {
            student.setAddress(studentRequestDTO.getAddress());
        }
        if (studentRequestDTO.getEmail() != null) {
            if (UtilService.emailValidation(studentRequestDTO.getEmail())) {
                student.setEmail(studentRequestDTO.getEmail());
            } else {
                throw new BadRequestServiceAlertException(Constants.EMAIL_NOT_VALID + Constants.EMAIL_PATTERN);
            }
        }
        if (studentRequestDTO.getPhoneNo() != null) {
            if (UtilService.phoneNoValidation(studentRequestDTO.getPhoneNo())) {
                student.setPhoneNo(studentRequestDTO.getPhoneNo());
            } else {
                throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
            }
        }
        return new ResponseDTO(Constants.UPDATED, this.studentRepository.save(student), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.studentRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.studentRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED,id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Student student = this.studentRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, student, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.studentRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

}
