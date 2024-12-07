package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SubjectRequestDTO;
import com.example.schoolmanagementsystem.service.SubjectService;
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

@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {

    @MockBean
    private SubjectService subjectService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateSubject() throws Exception {
        SubjectRequestDTO subjectRequestDTO = new SubjectRequestDTO();
        subjectRequestDTO.setSubjectName("Tamil");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject created successfully");

        when(subjectService.create(subjectRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/subject/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subjectName\":\"Tamil\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Subject created successfully\"}"));
    }

    @Test
    void testUpdateSubject() throws Exception {
        String id = "21";
        SubjectRequestDTO subjectRequestDTO = new SubjectRequestDTO();
        subjectRequestDTO.setSubjectName("English");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject updated successfully");

        when(subjectService.update(id, subjectRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/subject/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subjectName\":\"English\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Subject updated successfully\"}"));
    }

    @Test
    void testRetrieveSubjectById() throws Exception {
        String id = "32";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject retrieved successfully");

        when(subjectService.retrieve(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/subject/retrieve/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Subject retrieved successfully\"}"));
    }

    @Test
    void testRetrieveSubjects() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subjects retrieved successfully");

        when(subjectService.retrieveAll()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/subject/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Subjects retrieved successfully\"}"));
    }

    @Test
    void testDeleteSubject() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject deleted successfully");

        when(subjectService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/subject/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Subject deleted successfully\"}"));
    }
}
