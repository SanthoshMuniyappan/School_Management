package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SchoolRequestDTO;
import com.example.schoolmanagementsystem.entity.School;
import com.example.schoolmanagementsystem.repository.SchoolRepository;
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
public class SchoolServiceTest {

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private SchoolService schoolService;

    @Test
    public void testCreate() {

        SchoolRequestDTO schoolRequestDTO = new SchoolRequestDTO();
        schoolRequestDTO.setName("SigarAm higher secondary school");
        schoolRequestDTO.setCreatedBy("Jano line");
        schoolRequestDTO.setUpdatedBy("Jano line");

        School school = new School();
        school.setName(schoolRequestDTO.getName());
        school.setCreatedBy(schoolRequestDTO.getCreatedBy());
        school.setUpdatedBy(schoolRequestDTO.getUpdatedBy());

        when(this.schoolRepository.save(Mockito.any(School.class))).thenReturn(school);

        ResponseDTO responseDTO = this.schoolService.create(schoolRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, school);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testUpdate() {
        School school = new School();
        school.setId("1");
        school.setName("Maharishi higher secondary school");
        school.setCreatedBy("Janina");
        school.setUpdatedBy("Janina");

        when(schoolRepository.findById("1")).thenReturn(Optional.of(school));
        when(this.schoolRepository.save(school)).thenReturn(school);

        SchoolRequestDTO schoolRequestDTO = new SchoolRequestDTO();
        schoolRequestDTO.setName(school.getName());
        schoolRequestDTO.setUpdatedBy(school.getUpdatedBy());

        ResponseDTO responseDTO = this.schoolService.update(school.getId(), schoolRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, school);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());

    }

    @Test
    public void testRemove() {

        String id = "1";

        when(this.schoolRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.schoolService.remove(id);
        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());

    }

    @Test
    public void testRetrieve() {
        String id = "123";

        School school = new School();
        when(this.schoolRepository.findById(id)).thenReturn(Optional.of(school));

        ResponseDTO responseDTO = this.schoolService.retrieve(id);
        Object data = responseDTO.getData();
        assertEquals(data, school);

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieveAll() {

        List<School> schools = new ArrayList<>();
        when(this.schoolRepository.findAll()).thenReturn(schools);

        ResponseDTO responseDTO = this.schoolService.retrieveAll();

        assertEquals(schools, responseDTO.getData());

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());

    }
}
