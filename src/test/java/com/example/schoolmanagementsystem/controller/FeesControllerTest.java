package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.FeesRequestDTO;
import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.service.FeesService;
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

@WebMvcTest(FeesController.class)
public class FeesControllerTest {

    @MockBean
    private FeesService feesService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCreateFees() throws Exception {
        FeesRequestDTO feesRequestDTO = new FeesRequestDTO();
        feesRequestDTO.setTotalFees("24000");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Fees paid successfully");

        when(feesService.create(feesRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/fees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"totalFees\":\"24000\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Fees paid successfully\"}"));
    }

    @Test
    void testUpdateFees() throws Exception {
        String id = "1";
        FeesRequestDTO feesRequestDTO = new FeesRequestDTO();
        feesRequestDTO.setTotalFees("45000");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Fees updated successfully");

        when(feesService.update(id, feesRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/fees/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"totalFees\":\"45000\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Fees updated successfully\"}"));
    }

    @Test
    void testRetrieveFeesById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Fees retrieved successfully");

        when(feesService.retrieve(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/fees/retrieve/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Fees retrieved successfully\"}"));
    }

    @Test
    void testRetrieveFees() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Fees retrieved successfully");

        when(feesService.retrieveAll()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/fees/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Fees retrieved successfully\"}"));
    }

    @Test
    void testDeleteFees() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Fees deleted successfully");

        when(feesService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/fees/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Fees deleted successfully\"}"));
    }
}
