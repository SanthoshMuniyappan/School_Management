package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.MarkRequestDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.entity.Exam;
import com.example.schoolmanagementsystem.entity.Mark;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.entity.Student;
import com.example.schoolmanagementsystem.repository.ExamRepository;
import com.example.schoolmanagementsystem.repository.MarkRepository;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.repository.StudentRepository;
import com.example.schoolmanagementsystem.service.MarkService;
import com.example.schoolmanagementsystem.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MarkTest {

    @Mock
    private MarkRepository markRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private StandardRepository standardRepository;

    @InjectMocks
    private MarkService markService;

    @Test
    public void testCreate() {
        String studentId = "97";
        String examId = "34";
        String standardId = "54";

        MarkRequestDTO markRequestDTO = new MarkRequestDTO();
        markRequestDTO.setExamId(examId);
        markRequestDTO.setMark("90");
        markRequestDTO.setStudentId(studentId);
        markRequestDTO.setStandardId(standardId);
        markRequestDTO.setCreatedBy("jano");
        markRequestDTO.setUpdatedBy("jano");

        Mark mark = new Mark();
        Standard standard = new Standard();
        Exam exam = new Exam();
        Student student = new Student();
        when(this.standardRepository.findById(markRequestDTO.getStandardId())).thenReturn(Optional.of(standard));
        when(this.examRepository.findById(markRequestDTO.getExamId())).thenReturn(Optional.of(exam));
        when(this.studentRepository.findById(markRequestDTO.getStudentId())).thenReturn(Optional.of(student));
        mark.setMark(markRequestDTO.getMark());
        mark.setStandard(standard);
        mark.setStudent(student);
        mark.setExam(exam);
        mark.setCreatedBy(markRequestDTO.getCreatedBy());
        mark.setUpdatedBy(markRequestDTO.getUpdatedBy());
        when(this.markRepository.save(Mockito.any(Mark.class))).thenReturn(mark);

        ResponseDTO responseDTO = this.markService.create(markRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(mark, data);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testUpdate() {
        String markId = "32";
        MarkRequestDTO markRequestDTO = new MarkRequestDTO();
        markRequestDTO.setMark("90");
        markRequestDTO.setUpdatedBy("santo");

        Mark mark = new Mark();
        mark.setMark(markRequestDTO.getMark());
        mark.setUpdatedBy(markRequestDTO.getUpdatedBy());

        when(this.markRepository.findById(markId)).thenReturn(Optional.of(mark));
        when(this.markRepository.save(mark)).thenReturn(mark);

        ResponseDTO responseDTO = this.markService.update(markId, markRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, mark);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieve() {
        String id = "6";

        Mark mark = new Mark();
        when(this.markRepository.findById(id)).thenReturn(Optional.of(mark));

        ResponseDTO responseDTO = this.markService.retrieve(id);
        Object data = responseDTO.getData();
        assertEquals(data, mark);

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRemove() {
        String id = "37";

        when(this.markRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.markService.remove(id);

        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
}
