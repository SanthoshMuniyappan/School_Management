package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.TeacherRequestDTO;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.entity.Teacher;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.repository.TeacherRepository;
import com.example.schoolmanagementsystem.util.Constants;
import com.example.schoolmanagementsystem.util.UtilService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StandardRepository standardRepository;

    public TeacherService(final TeacherRepository teacherRepository, final StandardRepository standardRepository) {
        this.teacherRepository = teacherRepository;
        this.standardRepository = standardRepository;
    }

    @Transactional
    public ResponseDTO create(final TeacherRequestDTO teacherRequestDTO) {
        final Standard standard = this.standardRepository.findById(teacherRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final String phoneNo = checkPhoneNoValid(teacherRequestDTO);
        final Teacher teacher = Teacher.builder()
                .standard(standard)
                .name(teacherRequestDTO.getName())
                .joinOfDate(teacherRequestDTO.getJoinOfDate())
                .knownSubject(teacherRequestDTO.getKnownSubject())
                .gender(teacherRequestDTO.getGender())
                .salary(teacherRequestDTO.getSalary())
                .createdBy(teacherRequestDTO.getCreatedBy())
                .updatedBy(teacherRequestDTO.getUpdatedBy())
                .address(teacherRequestDTO.getAddress())
                .phone(phoneNo)
                .build();
        return new ResponseDTO(Constants.CREATED, this.teacherRepository.save(teacher), HttpStatus.CREATED.getReasonPhrase());
    }

    public String checkPhoneNoValid(final TeacherRequestDTO teacherRequestDTO) {
        if (UtilService.phoneNoValidation(teacherRequestDTO.getPhoneNo())) {
            return teacherRequestDTO.getPhoneNo();
        } else {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
    }

    @Transactional
    public ResponseDTO update(final String id, final TeacherRequestDTO teacherRequestDTO) {
        final Teacher teacher = this.teacherRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));

        if (teacherRequestDTO.getName() != null) {
            teacher.setName(teacherRequestDTO.getName());
        }
        if (teacherRequestDTO.getJoinOfDate() != null) {
            teacher.setJoinOfDate(teacherRequestDTO.getJoinOfDate());
        }
        if (teacherRequestDTO.getKnownSubject() != null) {
            teacher.setKnownSubject(teacher.getKnownSubject());
        }
        if (teacherRequestDTO.getGender() != null) {
            teacher.setGender(teacherRequestDTO.getGender());
        }
        if (teacherRequestDTO.getSalary() != null) {
            teacher.setSalary(teacherRequestDTO.getSalary());
        }
        if (teacherRequestDTO.getPhoneNo() != null) {
            if (UtilService.phoneNoValidation(teacherRequestDTO.getPhoneNo())) {
                teacher.setPhone(teacherRequestDTO.getPhoneNo());
            } else {
                throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
            }
        }
        if (teacherRequestDTO.getAddress() != null) {
            teacher.setAddress(teacherRequestDTO.getAddress());
        }
        if (teacherRequestDTO.getUpdatedBy() != null) {
            teacher.setUpdatedBy(teacherRequestDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.UPDATED, this.teacherRepository.save(teacher), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.teacherRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.teacherRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED,id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Teacher teacher = this.teacherRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, teacher, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.teacherRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveTeacher(final String name) {
        final List<Teacher> teacher = this.teacherRepository.findByName(name);
        return new ResponseDTO(Constants.RETRIEVED, teacher, HttpStatus.OK.getReasonPhrase());
    }
}
