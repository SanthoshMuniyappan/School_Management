package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.TeacherSectionRequestDTO;
import com.example.schoolmanagementsystem.entity.Section;
import com.example.schoolmanagementsystem.entity.Teacher;
import com.example.schoolmanagementsystem.entity.TeacherSection;
import com.example.schoolmanagementsystem.repository.SectionRepository;
import com.example.schoolmanagementsystem.repository.TeacherRepository;
import com.example.schoolmanagementsystem.repository.TeacherSectionRepository;
import com.example.schoolmanagementsystem.service.TeacherSectionService;
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
public class TeacherSectionTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private TeacherSectionRepository teacherSectionRepository;

    @InjectMocks
    private TeacherSectionService teacherSectionService;

    @Test
    public void testCreate() {
        String teacherId = "43";
        String sectionId = "23";
        TeacherSectionRequestDTO teacherSectionRequestDTO = new TeacherSectionRequestDTO();
        teacherSectionRequestDTO.setTeacherId(teacherId);
        teacherSectionRequestDTO.setSectionId(sectionId);
        teacherSectionRequestDTO.setCreatedBy("jano");
        teacherSectionRequestDTO.setUpdatedBy("jano");

        TeacherSection teacherSection = new TeacherSection();
        Section section = new Section();
        Teacher teacher = new Teacher();
        when(this.sectionRepository.findById(sectionId)).thenReturn(Optional.of(section));
        when(this.teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        teacherSection.setCreatedBy(teacherSectionRequestDTO.getCreatedBy());
        teacherSection.setUpdatedBy(teacherSectionRequestDTO.getUpdatedBy());

        when(this.teacherSectionRepository.save(Mockito.any(TeacherSection.class))).thenReturn(teacherSection);

        ResponseDTO responseDTO = this.teacherSectionService.create(teacherSectionRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, teacherSection);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testUpdate() {
        String id = "34";
        TeacherSectionRequestDTO teacherSectionRequestDTO = new TeacherSectionRequestDTO();
        teacherSectionRequestDTO.setCreatedBy("santo");
        teacherSectionRequestDTO.setUpdatedBy("santo");

        TeacherSection teacherSection = new TeacherSection();
        teacherSection.setCreatedBy(teacherSectionRequestDTO.getCreatedBy());
        teacherSection.setUpdatedBy(teacherSectionRequestDTO.getUpdatedBy());

        when(this.teacherSectionRepository.findById(id)).thenReturn(Optional.of(teacherSection));
        when(this.teacherSectionRepository.save(teacherSection)).thenReturn(teacherSection);

        ResponseDTO responseDTO = this.teacherSectionService.update(id, teacherSectionRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, teacherSection);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRemove() {
        String id = "37";

        when(this.teacherSectionRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.teacherSectionService.remove(id);

        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
}
