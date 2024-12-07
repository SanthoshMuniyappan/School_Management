package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.MarkRequestDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.service.MarkService;
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

@WebMvcTest(MarkController.class)
public class MarkControllerTest {
    @MockBean
    private MarkService markService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateSchool() throws Exception {
        MarkRequestDTO markRequestDTO = new MarkRequestDTO();
        markRequestDTO.setMark("90");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Mark created successfully");

        when(markService.create(markRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/exam/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mark\":\"90\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School created successfully\"}"));
    }

    @Test
    void testUpdateSchool() throws Exception {
        String id = "32";
        MarkRequestDTO markRequestDTO = new MarkRequestDTO();
        markRequestDTO.setMark("65");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Mark updated successfully");

        when(markService.update(id, markRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/mark/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mark\":\"65\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Mark updated successfully\"}"));
    }

    @Test
    void testRetrieveSchoolById() throws Exception {
        String id = "43";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Mark retrieved successfully");

        when(markService.retrieve(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/mark/retrieve/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Mark retrieved successfully\"}"));
    }

    @Test
    void testDeleteSchool() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Mark deleted successfully");

        when(markService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/mark/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Mark deleted successfully\"}"));
    }
}
