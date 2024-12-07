package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.FeesRequestDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.entity.Fees;
import com.example.schoolmanagementsystem.entity.Student;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.FeesRepository;
import com.example.schoolmanagementsystem.repository.StudentRepository;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeesService {

    private final FeesRepository feesRepository;
    private final StudentRepository studentRepository;

    public FeesService(final FeesRepository feesRepository, final StudentRepository studentRepository) {
        this.feesRepository = feesRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public ResponseDTO create(final FeesRequestDTO feesRequestDTO) {
        final Student student = this.studentRepository.findById(feesRequestDTO.getStudentId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        final Fees fees = Fees.builder()
                .student(student)
                .totalFees(feesRequestDTO.getTotalFees())
                .paidFees(feesRequestDTO.getPaidFees())
                .unpaidFees(feesRequestDTO.getUnpaidFees())
                .createdBy(feesRequestDTO.getCreatedBy())
                .updatedBy(feesRequestDTO.getUpdatedBy())
                .build();
        return new ResponseDTO(Constants.CREATED, this.feesRepository.save(fees), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final FeesRequestDTO feesRequestDTO) {
        final Fees fees = this.feesRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        if (feesRequestDTO.getTotalFees() != null) {
            fees.setTotalFees(feesRequestDTO.getTotalFees());
        }
        if (feesRequestDTO.getPaidFees() != null) {
            fees.setPaidFees(feesRequestDTO.getPaidFees());
        }
        if (feesRequestDTO.getUnpaidFees() != null) {
            fees.setUnpaidFees(feesRequestDTO.getUnpaidFees());
        }
        if (feesRequestDTO.getUpdatedBy() != null) {
            fees.setUpdatedBy(feesRequestDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.UPDATED, this.feesRepository.save(fees), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.feesRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.feesRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED,id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Fees fees = this.feesRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, fees, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.feesRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }
}
