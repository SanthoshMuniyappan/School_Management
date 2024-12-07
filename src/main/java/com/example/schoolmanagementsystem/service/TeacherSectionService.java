package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.TeacherSectionRequestDTO;
import com.example.schoolmanagementsystem.entity.Section;
import com.example.schoolmanagementsystem.entity.Teacher;
import com.example.schoolmanagementsystem.entity.TeacherSection;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.SectionRepository;
import com.example.schoolmanagementsystem.repository.TeacherRepository;
import com.example.schoolmanagementsystem.repository.TeacherSectionRepository;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherSectionService {

    private final TeacherSectionRepository teacherSectionRepository;
    private final SectionRepository sectionRepository;
    private final TeacherRepository teacherRepository;

    public TeacherSectionService(final TeacherSectionRepository teacherSectionRepository, final SectionRepository sectionRepository, final TeacherRepository teacherRepository) {
        this.teacherSectionRepository = teacherSectionRepository;
        this.sectionRepository = sectionRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public ResponseDTO create(final TeacherSectionRequestDTO teacherSectionRequestDTO) {
        final Teacher teacher = this.teacherRepository.findById(teacherSectionRequestDTO.getTeacherId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final Section section = this.sectionRepository.findById(teacherSectionRequestDTO.getSectionId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final TeacherSection studentSection = TeacherSection.builder()
                .teacher(teacher)
                .section(section)
                .createdBy(teacherSectionRequestDTO.getCreatedBy())
                .updatedBy(teacherSectionRequestDTO.getUpdatedBy())
                .build();
        return new ResponseDTO(Constants.CREATED, this.teacherSectionRepository.save(studentSection), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final TeacherSectionRequestDTO teacherSectionRequestDTO) {
        final TeacherSection studentSection = this.teacherSectionRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        if (teacherSectionRequestDTO.getCreatedBy() != null) {
            studentSection.setCreatedBy(teacherSectionRequestDTO.getCreatedBy());
        }
        if (teacherSectionRequestDTO.getUpdatedBy() != null) {
            studentSection.setUpdatedBy(teacherSectionRequestDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.UPDATED, this.teacherSectionRepository.save(studentSection), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.teacherSectionRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.teacherSectionRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED,id, HttpStatus.OK.getReasonPhrase());
    }
}
