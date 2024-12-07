package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.FeesRequestDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.entity.Fees;
import com.example.schoolmanagementsystem.entity.Student;
import com.example.schoolmanagementsystem.repository.FeesRepository;
import com.example.schoolmanagementsystem.repository.StudentRepository;
import com.example.schoolmanagementsystem.service.FeesService;
import com.example.schoolmanagementsystem.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeesTest {
    @Mock
    private FeesRepository feesRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private FeesService feesService;

    @Test
    public void testCreate() {
        String id = "13";
        FeesRequestDTO feesRequestDTO = new FeesRequestDTO();
        feesRequestDTO.setStudentId(id);
        feesRequestDTO.setTotalFees("50000");
        feesRequestDTO.setPaidFees("25000");
        feesRequestDTO.setUnpaidFees("25000");
        feesRequestDTO.setCreatedBy("Mani");
        feesRequestDTO.setUpdatedBy("Mani");

        Fees fees = new Fees();
        Student student = new Student();
        when(this.studentRepository.findById(id)).thenReturn(Optional.of(student));
        fees.setStudent(student);
        fees.setPaidFees(feesRequestDTO.getPaidFees());
        fees.setTotalFees(feesRequestDTO.getTotalFees());
        fees.setUnpaidFees(feesRequestDTO.getUnpaidFees());
        fees.setCreatedBy(feesRequestDTO.getCreatedBy());
        fees.setUpdatedBy(feesRequestDTO.getUpdatedBy());
        when(this.feesRepository.save(Mockito.any(Fees.class))).thenReturn(fees);

        ResponseDTO responseDTO = this.feesService.create(feesRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(fees, data);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testUpdate() {
        String id = "64";
        FeesRequestDTO feesRequestDTO = new FeesRequestDTO();
        feesRequestDTO.setStudentId(id);
        feesRequestDTO.setTotalFees("50000");
        feesRequestDTO.setPaidFees("25000");
        feesRequestDTO.setUnpaidFees("25000");
        feesRequestDTO.setUpdatedBy("Santo");

        Fees fees = new Fees();
        fees.setPaidFees(feesRequestDTO.getPaidFees());
        fees.setTotalFees(feesRequestDTO.getTotalFees());
        fees.setUnpaidFees(feesRequestDTO.getUnpaidFees());

        when(this.feesRepository.findById(id)).thenReturn(Optional.of(fees));
        when(this.feesRepository.save(fees)).thenReturn(fees);

        ResponseDTO responseDTO = this.feesService.update(id, feesRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, fees);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieve() {
        String id = "6";

        Fees fees = new Fees();
        when(this.feesRepository.findById(id)).thenReturn(Optional.of(fees));

        ResponseDTO responseDTO = this.feesService.retrieve(id);
        Object data = responseDTO.getData();
        assertEquals(data, fees);

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieveAll() {
        List<Fees> fees = new ArrayList<>();
        when(this.feesRepository.findAll()).thenReturn(fees);

        ResponseDTO responseDTO = this.feesService.retrieveAll();

        assertEquals(fees, responseDTO.getData());

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRemove() {
        String id = "37";

        when(this.feesRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.feesService.remove(id);

        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
}
