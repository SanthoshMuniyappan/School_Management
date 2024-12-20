package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.TeacherSectionRequestDTO;
import com.example.schoolmanagementsystem.service.TeacherSectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherSectionControllerTest {
    @MockBean
    private TeacherSectionService teacherSectionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Thiru", roles = "ADMIN")
    public void testCreateSchool() throws Exception {
        TeacherSectionRequestDTO teacherSectionRequestDTO = new TeacherSectionRequestDTO();
        teacherSectionRequestDTO.setCreatedBy("Jano");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("TeacherSection created successfully");

        when(teacherSectionService.create(teacherSectionRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/teacherSection/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"createdBy\":\"Jano\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"TeacherSection created successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Thiru", roles = "ADMIN")
    void testUpdateSchool() throws Exception {
        String id = "23";
        TeacherSectionRequestDTO teacherSectionRequestDTO = new TeacherSectionRequestDTO();
        teacherSectionRequestDTO.setSectionId("55");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("TeacherSection updated successfully");

        when(teacherSectionService.update(id, teacherSectionRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/teacherSection/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sectionId\":\"55\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"TeacherSection updated successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Thiru", roles = "ADMIN")
    void testDeleteSchool() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("TeacherSection deleted successfully");

        when(teacherSectionService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/teacherSection/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"TeacherSection deleted successfully\"}"));
    }
}
