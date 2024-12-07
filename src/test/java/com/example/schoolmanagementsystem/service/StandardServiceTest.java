package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.StandardRequestDTO;
import com.example.schoolmanagementsystem.entity.School;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.repository.SchoolRepository;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.service.StandardService;
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
public class StandardServiceTest {

    @Mock
    private StandardRepository standardRepository;

    @InjectMocks
    private StandardService standardService;

    @Mock
    private SchoolRepository schoolRepository;

    @Test
    public void testCreate() {
        String id = "123";
        StandardRequestDTO standardRequestDTO = new StandardRequestDTO();
        standardRequestDTO.setName("10");
        standardRequestDTO.setFees("40000");
        standardRequestDTO.setSchoolId(id);
        standardRequestDTO.setCreatedBy("jano");
        standardRequestDTO.setUpdatedBy("jano");

        Standard standard = new Standard();
        School school = new School();
        when(this.schoolRepository.findById(id)).thenReturn(Optional.of(school));
        standard.setName(standardRequestDTO.getName());
        standard.setFees(standardRequestDTO.getFees());
        standard.setCreatedBy(standardRequestDTO.getCreatedBy());
        standard.setUpdatedBy(standardRequestDTO.getUpdatedBy());
        standard.setSchool(school);
        when(this.standardRepository.save(Mockito.any(Standard.class))).thenReturn(standard);

        ResponseDTO responseDTO = this.standardService.create(standardRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(standard, data);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testUpdate() {
        String id = "98";
        StandardRequestDTO standardRequestDTO = new StandardRequestDTO();
        standardRequestDTO.setName("10");
        standardRequestDTO.setFees("40000");
        standardRequestDTO.setUpdatedBy("santo");

        Standard standard = new Standard();
        standard.setName(standardRequestDTO.getName());
        standard.setFees(standardRequestDTO.getFees());
        standard.setUpdatedBy(standardRequestDTO.getUpdatedBy());

        when(this.standardRepository.findById(id)).thenReturn(Optional.of(standard));
        when(this.standardRepository.save(standard)).thenReturn(standard);

        ResponseDTO responseDTO = this.standardService.update(id, standardRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, standard);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieve() {
        String id = "90";

        Standard standard = new Standard();
        when(this.standardRepository.findById(id)).thenReturn(Optional.of(standard));

        ResponseDTO responseDTO = this.standardService.retrieve(id);
        Object data = responseDTO.getData();
        assertEquals(data, standard);

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieveAll() {
        List<Standard> standards = new ArrayList<>();
        when(this.standardRepository.findAll()).thenReturn(standards);

        ResponseDTO responseDTO = this.standardService.retrieveAll();

        assertEquals(standards, responseDTO.getData());

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRemove() {
        String id = "17";

        when(this.standardRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.standardService.remove(id);

        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
}
