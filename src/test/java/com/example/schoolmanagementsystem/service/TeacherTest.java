package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.TeacherRequestDTO;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.entity.Teacher;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.repository.TeacherRepository;
import com.example.schoolmanagementsystem.service.TeacherService;
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
public class TeacherTest {
    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StandardRepository standardRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    public void testCreate() {
        String id = "36";
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Karthick");
        teacherRequestDTO.setJoinOfDate("10/02/2018");
        teacherRequestDTO.setAddress("chennai");
        teacherRequestDTO.setPhoneNo("9078964942");
        teacherRequestDTO.setGender("Male");
        teacherRequestDTO.setKnownSubject("Tamil");
        teacherRequestDTO.setStandardId(id);
        teacherRequestDTO.setSalary("35000");
        teacherRequestDTO.setCreatedBy("jano");
        teacherRequestDTO.setUpdatedBy("jano");

        Teacher teacher = new Teacher();
        Standard standard = new Standard();
        when(this.standardRepository.findById(id)).thenReturn(Optional.of(standard));
        teacher.setName(teacherRequestDTO.getName());
        teacher.setJoinOfDate(teacherRequestDTO.getJoinOfDate());
        teacher.setAddress(teacherRequestDTO.getAddress());
        teacher.setPhone(teacherRequestDTO.getPhoneNo());
        teacher.setGender(teacherRequestDTO.getGender());
        teacher.setSalary(teacherRequestDTO.getSalary());
        teacher.setKnownSubject(teacherRequestDTO.getKnownSubject());
        teacher.setStandard(standard);
        teacher.setCreatedBy(teacherRequestDTO.getCreatedBy());
        teacher.setUpdatedBy(teacherRequestDTO.getUpdatedBy());
        when(this.teacherRepository.save(Mockito.any(Teacher.class))).thenReturn(teacher);

        ResponseDTO responseDTO = this.teacherService.create(teacherRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(teacher, data);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testUpdate() {
        String id = "23";

        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Karthick");
        teacherRequestDTO.setJoinOfDate("10/02/2018");
        teacherRequestDTO.setAddress("chennai");
        teacherRequestDTO.setPhoneNo("9078964942");
        teacherRequestDTO.setGender("Male");
        teacherRequestDTO.setKnownSubject("Tamil");
        teacherRequestDTO.setSalary("35000");
        teacherRequestDTO.setUpdatedBy("jano");

        Teacher teacher = new Teacher();
        teacher.setName(teacherRequestDTO.getName());
        teacher.setJoinOfDate(teacherRequestDTO.getJoinOfDate());
        teacher.setAddress(teacherRequestDTO.getAddress());
        teacher.setPhone(teacherRequestDTO.getPhoneNo());
        teacher.setGender(teacherRequestDTO.getGender());
        teacher.setSalary(teacherRequestDTO.getSalary());
        teacher.setKnownSubject(teacherRequestDTO.getKnownSubject());
        teacher.setUpdatedBy(teacherRequestDTO.getUpdatedBy());

        when(this.teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
        when(this.teacherRepository.save(teacher)).thenReturn(teacher);

        ResponseDTO responseDTO = this.teacherService.update(id, teacherRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, teacher);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieve() {
        String id = "6";

        Teacher teacher = new Teacher();
        when(this.teacherRepository.findById(id)).thenReturn(Optional.of(teacher));

        ResponseDTO responseDTO = this.teacherService.retrieve(id);
        Object data = responseDTO.getData();
        assertEquals(data, teacher);

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieveAll() {
        List<Teacher> teachers = new ArrayList<>();
        when(this.teacherRepository.findAll()).thenReturn(teachers);

        ResponseDTO responseDTO = this.teacherService.retrieveAll();

        assertEquals(teachers, responseDTO.getData());

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRemove() {
        String id = "37";

        when(this.teacherRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.teacherService.remove(id);

        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
}
