package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.StudentRequestDTO;
import com.example.schoolmanagementsystem.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateStudent() throws Exception {
        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setName("Anitha");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student created successfully");

        when(studentService.create(studentRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Anitha\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student created successfully\"}"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        String id = "14";
        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setName("Santhosh");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student updated successfully");

        when(studentService.update(id, studentRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/student/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Santhosh\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student updated successfully\"}"));
    }

    @Test
    void testRetrieveStudentById() throws Exception {
        String id = "18";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student retrieved successfully");

        when(studentService.retrieve(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/student/retrieve/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student retrieved successfully\"}"));
    }

    @Test
    void testRetrieveStudents() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Students retrieved successfully");

        when(studentService.retrieveAll()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/student/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Students retrieved successfully\"}"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student deleted successfully");

        when(studentService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/student/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student deleted successfully\"}"));
    }
}
