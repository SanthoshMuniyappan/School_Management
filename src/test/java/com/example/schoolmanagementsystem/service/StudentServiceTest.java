package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.StudentRequestDTO;
import com.example.schoolmanagementsystem.entity.Section;
import com.example.schoolmanagementsystem.entity.Student;
import com.example.schoolmanagementsystem.repository.SectionRepository;
import com.example.schoolmanagementsystem.repository.StudentRepository;
import com.example.schoolmanagementsystem.service.StudentService;
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
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testCreate() {
        String id = "45";
        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setName("Anu");
        studentRequestDTO.setDateOfBirth("10-02-2009");
        studentRequestDTO.setAddress("Trichy");
        studentRequestDTO.setSectionId(id);
        studentRequestDTO.setEmail("anu1234@gmail.com");
        studentRequestDTO.setPhoneNo("9749865938");
        studentRequestDTO.setCreatedBy("jana");
        studentRequestDTO.setUpdatedBy("jana");

        Student student = new Student();
        Section section = new Section();
        when(this.sectionRepository.findById(id)).thenReturn(Optional.of(section));
        student.setName(studentRequestDTO.getName());
        student.setDateOfBirth(studentRequestDTO.getDateOfBirth());
        student.setPhoneNo(studentRequestDTO.getPhoneNo());
        student.setAddress(studentRequestDTO.getAddress());
        student.setEmail(studentRequestDTO.getEmail());
        student.setSection(section);
        when(this.studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        ResponseDTO responseDTO = this.studentService.create(studentRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(student, data);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testUpdate() {
        String id = "34";
        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setName("Anu");
        studentRequestDTO.setDateOfBirth("10-02-2009");
        studentRequestDTO.setAddress("Trichy");
        studentRequestDTO.setEmail("anu1234@gmail.com");
        studentRequestDTO.setPhoneNo("9749865938");

        Student student = new Student();
        student.setName(studentRequestDTO.getName());
        student.setDateOfBirth(studentRequestDTO.getDateOfBirth());
        student.setPhoneNo(studentRequestDTO.getPhoneNo());
        student.setAddress(studentRequestDTO.getAddress());
        student.setEmail(studentRequestDTO.getEmail());

        when(this.studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(this.studentRepository.save(student)).thenReturn(student);

        ResponseDTO responseDTO = this.studentService.update(id, studentRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, student);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieve() {
        String id = "6";

        Student student = new Student();
        when(this.studentRepository.findById(id)).thenReturn(Optional.of(student));

        ResponseDTO responseDTO = this.studentService.retrieve(id);
        Object data = responseDTO.getData();
        assertEquals(data, student);

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieveAll() {
        List<Student> students = new ArrayList<>();
        when(this.studentRepository.findAll()).thenReturn(students);

        ResponseDTO responseDTO = this.studentService.retrieveAll();

        assertEquals(students, responseDTO.getData());

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRemove() {
        String id = "37";

        when(this.studentRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.studentService.remove(id);

        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
}
