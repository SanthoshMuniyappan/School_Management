package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ExamRequestDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.service.ExamService;
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

@WebMvcTest(ExamController.class)
public class ExamControllerTest {

    @MockBean
    private ExamService examService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateSchool() throws Exception {
        ExamRequestDTO examRequestDTO = new ExamRequestDTO();
        examRequestDTO.setName("Half yearly exam");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Exam created successfully");

        when(examService.create(examRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/exam/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Half yearly exam\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Exam created successfully\"}"));
    }

    @Test
    void testUpdateSchool() throws Exception {
        String id = "43";
        ExamRequestDTO examRequestDTO = new ExamRequestDTO();
        examRequestDTO.setName("Quarterly exam");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Exam updated successfully");

        when(examService.update(id, examRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/exam/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Quarterly exam\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Exam updated successfully\"}"));
    }

    @Test
    void testRetrieveSchoolById() throws Exception {
        String id = "23";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Exam retrieved successfully");

        when(examService.retrieve(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/exam/retrieve/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Exam retrieved successfully\"}"));
    }

    @Test
    void testRetrieveSchools() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Exams retrieved successfully");

        when(examService.retrieveAll()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/exam/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Exams retrieved successfully\"}"));
    }

    @Test
    void testDeleteSchool() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Exam deleted successfully");

        when(examService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/exam/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Exam deleted successfully\"}"));
    }
}
