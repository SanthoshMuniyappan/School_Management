package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ExamRequestDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.entity.Exam;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.entity.Student;
import com.example.schoolmanagementsystem.entity.Subject;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.ExamRepository;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.repository.SubjectRepository;
import com.example.schoolmanagementsystem.service.ExamService;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExamTest {
    @Mock
    private ExamRepository examRepository;

    @Mock
    private StandardRepository standardRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private ExamService examService;

    @Test
    public void testCreate(){
        String subjectId="54";
        String standardId="89";
        ExamRequestDTO examRequestDTO=new ExamRequestDTO();
        examRequestDTO.setName("Half yearly Exam");
        examRequestDTO.setDate("25/07/2025");
        examRequestDTO.setSubjectId(subjectId);
        examRequestDTO.setStandardId(standardId);
        examRequestDTO.setCreatedBy("Anu");
        examRequestDTO.setUpdatedBy("Anu");

        Exam exam=new Exam();
        Subject subject=new Subject();
        Standard standard=new Standard();
        when(this.subjectRepository.findById(examRequestDTO.getSubjectId())).thenReturn(Optional.of(subject));
        when(this.standardRepository.findById(examRequestDTO.getStandardId())).thenReturn(Optional.of(standard));
        exam.setName(examRequestDTO.getName());
        exam.setDate(examRequestDTO.getDate());
        exam.setStandard(standard);
        exam.setSubject(subject);
        exam.setCreatedBy(examRequestDTO.getCreatedBy());
        exam.setUpdatedBy(examRequestDTO.getUpdatedBy());
        when(this.examRepository.save(Mockito.any(Exam.class))).thenReturn(exam);

        ResponseDTO responseDTO = this.examService.create(examRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(exam, data);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }
    @Test
    public void testUpdate(){
        String id="35";
        ExamRequestDTO examRequestDTO=new ExamRequestDTO();
        examRequestDTO.setName("Half yearly Exam");
        examRequestDTO.setDate("25/07/2025");
        examRequestDTO.setUpdatedBy("santo");

        Exam exam=new Exam();
        exam.setName(examRequestDTO.getName());
        exam.setDate(examRequestDTO.getDate());
        exam.setUpdatedBy(examRequestDTO.getUpdatedBy());

        when(this.examRepository.findById(id)).thenReturn(Optional.of(exam));
        when(this.examRepository.save(exam)).thenReturn(exam);

        ResponseDTO responseDTO = this.examService.update(id,examRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, exam);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
    @Test
    public void testRetrieve(){
        String id = "6";

        Exam exam=new Exam();
        when(this.examRepository.findById(id)).thenReturn(Optional.of(exam));

        ResponseDTO responseDTO = this.examService.retrieve(id);
        Object data = responseDTO.getData();
        assertEquals(data, exam);

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
    @Test
    public void testRetrieveAll(){
        List<Exam> exams = new ArrayList<>();
        when(this.examRepository.findAll()).thenReturn(exams);

        ResponseDTO responseDTO = this.examService.retrieveAll();

        assertEquals(exams, responseDTO.getData());

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
    @Test
    public void testRemove(){
        String id = "37";

        when(this.examRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.examService.remove(id);

        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRemoveNegative(){
        String id ="10";
        when(this.examRepository.existsById(id)).thenReturn(false);
        try{
            ResponseDTO responseDTO=this.examService.remove(id);
        }catch (BadRequestServiceAlertException exception){
            assertEquals(Constants.ID_NOT_NULL,exception.getMessage());
        }
    }
}
