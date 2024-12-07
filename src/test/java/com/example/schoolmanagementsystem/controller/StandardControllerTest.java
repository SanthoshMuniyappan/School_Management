package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.StandardRequestDTO;
import com.example.schoolmanagementsystem.service.StandardService;
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

@WebMvcTest(StandardController.class)
public class StandardControllerTest {

    @MockBean
    private StandardService standardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateStandard() throws Exception {
        StandardRequestDTO standardRequestDTO = new StandardRequestDTO();
        standardRequestDTO.setName("10th");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard created successfully");

        when(standardService.create(standardRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/standard/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"10th\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Standard created successfully\"}"));
    }

    @Test
    void testUpdateStandard() throws Exception {
        String id = "34";
        StandardRequestDTO standardRequestDTO = new StandardRequestDTO();
        standardRequestDTO.setName("9th");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard updated successfully");

        when(standardService.update(id, standardRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/standard/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"9th\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Standard updated successfully\"}"));
    }

    @Test
    void testRetrieveStandardById() throws Exception {
        String id = "98";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard retrieved successfully");

        when(standardService.retrieve(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/standard/retrieve/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Standard retrieved successfully\"}"));
    }

    @Test
    void testRetrieveStandards() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standards retrieved successfully");

        when(standardService.retrieveAll()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/standard/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Standards retrieved successfully\"}"));
    }

    @Test
    void testDeleteStandard() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard deleted successfully");

        when(standardService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/standard/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Standard deleted successfully\"}"));
    }
}
