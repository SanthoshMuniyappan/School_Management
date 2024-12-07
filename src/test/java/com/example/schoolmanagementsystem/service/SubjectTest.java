package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SubjectRequestDTO;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.entity.Subject;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.repository.SubjectRepository;
import com.example.schoolmanagementsystem.service.SubjectService;
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
public class SubjectTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private StandardRepository standardRepository;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    public void testCreate() {
        String id = "54";
        SubjectRequestDTO subjectRequestDTO = new SubjectRequestDTO();
        subjectRequestDTO.setSubjectName("Tamil");
        subjectRequestDTO.setStandardId(id);
        subjectRequestDTO.setCreatedBy("santo");
        subjectRequestDTO.setUpdatedBy("santo");

        Subject subject = new Subject();
        Standard standard = new Standard();
        when(this.standardRepository.findById(id)).thenReturn(Optional.of(standard));
        subject.setSubjectName(subjectRequestDTO.getSubjectName());
        subject.setCreatedBy(subjectRequestDTO.getCreatedBy());
        subject.setUpdatedBy(subjectRequestDTO.getUpdatedBy());
        subject.setStandard(standard);
        when(this.subjectRepository.save(Mockito.any(Subject.class))).thenReturn(subject);

        ResponseDTO responseDTO = this.subjectService.create(subjectRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(subject, data);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testUpdate() {
        String id = "9";
        SubjectRequestDTO subjectRequestDTO = new SubjectRequestDTO();
        subjectRequestDTO.setSubjectName("Tamil");
        subjectRequestDTO.setUpdatedBy("jano");

        Subject subject = new Subject();
        subject.setSubjectName(subjectRequestDTO.getSubjectName());
        subject.setUpdatedBy(subjectRequestDTO.getUpdatedBy());

        when(this.subjectRepository.findById(id)).thenReturn(Optional.of(subject));
        when(this.subjectRepository.save(subject)).thenReturn(subject);

        ResponseDTO responseDTO = this.subjectService.update(id, subjectRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, subject);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieve() {
        String id = "6";

        Subject subject = new Subject();
        when(this.subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        ResponseDTO responseDTO = this.subjectService.retrieve(id);
        Object data = responseDTO.getData();
        assertEquals(data, subject);

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieveAll() {
        List<Subject> subjects = new ArrayList<>();
        when(this.subjectRepository.findAll()).thenReturn(subjects);

        ResponseDTO responseDTO = this.subjectService.retrieveAll();

        assertEquals(subjects, responseDTO.getData());

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRemove() {
        String id = "37";

        when(this.subjectRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.subjectService.remove(id);

        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
}
