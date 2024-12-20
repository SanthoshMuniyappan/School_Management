package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.TeacherRequestDTO;
import com.example.schoolmanagementsystem.service.TeacherService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {
    @MockBean
    private TeacherService teacherService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Deena", roles = "HR")
    public void testCreateTeacher() throws Exception {
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Meena");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teacher created successfully");

        when(teacherService.create(teacherRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/teacher/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Meena\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teacher created successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Deena", roles = "HR")
    void testUpdateTeacher() throws Exception {
        String id = "1";
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Arun");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teacher updated successfully");

        when(teacherService.update(id, teacherRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/teacher/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Arun\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teacher updated successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Deena", roles = "HR")
    void testRetrieveTeacherById() throws Exception {
        String id = "61";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teacher retrieved successfully");

        when(teacherService.retrieve(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/teacher/retrieve/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teacher retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Deena", roles = "HR")
    void testRetrieveTeachers() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teachers retrieved successfully");

        when(teacherService.retrieveAll()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/teacher/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teachers retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Deena", roles = "HR")
    void testDeleteTeacher() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teacher deleted successfully");

        when(teacherService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/teacher/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teacher deleted successfully\"}"));
    }
}
