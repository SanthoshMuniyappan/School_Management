package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SectionRequestDTO;
import com.example.schoolmanagementsystem.entity.Section;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.SectionRepository;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final StandardRepository standardRepository;

    public SectionService(final SectionRepository sectionRepository, final StandardRepository standardRepository) {
        this.sectionRepository = sectionRepository;
        this.standardRepository = standardRepository;
    }

    @Transactional
    public ResponseDTO create(final SectionRequestDTO sectionRequestDTO) {
        final Standard standard = this.standardRepository.findById(sectionRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceAlertException("Id is not found"));
        final Section section = Section.builder()
                .standard(standard)
                .name(sectionRequestDTO.getName())
                .maximumCapacity(sectionRequestDTO.getMaximumCapacity())
                .createdBy(sectionRequestDTO.getCreatedBy())
                .updatedBy(sectionRequestDTO.getUpdatedBy())
                .build();
        return new ResponseDTO(Constants.CREATED, this.sectionRepository.save(section), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final SectionRequestDTO sectionRequestDTO) {
        final Section section = this.sectionRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));

        if (sectionRequestDTO.getName() != null) {
            section.setName(sectionRequestDTO.getName());
        }
        if (sectionRequestDTO.getMaximumCapacity() != null) {
            section.setMaximumCapacity(sectionRequestDTO.getMaximumCapacity());
        }
        if (sectionRequestDTO.getUpdatedBy() != null) {
            section.setUpdatedBy(sectionRequestDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.UPDATED, this.sectionRepository.save(section), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.sectionRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.sectionRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED,id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Section section = this.sectionRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, section, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.sectionRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveSection(final String name) {
        return new ResponseDTO(Constants.RETRIEVED, this.sectionRepository.findBySection(name), HttpStatus.OK.getReasonPhrase());
    }
}
