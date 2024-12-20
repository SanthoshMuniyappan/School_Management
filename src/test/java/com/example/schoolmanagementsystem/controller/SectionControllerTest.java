package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SectionRequestDTO;
import com.example.schoolmanagementsystem.service.SectionService;
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
public class SectionControllerTest {

    @MockBean
    private SectionService sectionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Thiru", roles = "ADMIN")
    public void testCreateSection() throws Exception {
        SectionRequestDTO sectionRequestDTO = new SectionRequestDTO();
        sectionRequestDTO.setName("10th A");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section created successfully");

        when(sectionService.create(sectionRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/section/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"10th A\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Section created successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Thiru", roles = "ADMIN")
    void testUpdateSection() throws Exception {
        String id = "11";
        SectionRequestDTO sectionRequestDTO = new SectionRequestDTO();
        sectionRequestDTO.setName("9th B");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section updated successfully");

        when(sectionService.update(id, sectionRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/section/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"9th B\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Section updated successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Thiru", roles = "ADMIN")
    void testRetrieveSectionById() throws Exception {
        String id = "21";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section retrieved successfully");

        when(sectionService.retrieve(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/section/retrieve/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Section retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Thiru", roles = "ADMIN")
    void testRetrieveSections() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Sections retrieved successfully");

        when(sectionService.retrieveAll()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/section/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Sections retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(username = "Thiru", roles = "ADMIN")
    void testDeleteSection() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section deleted successfully");

        when(sectionService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/section/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Section deleted successfully\"}"));
    }
}
