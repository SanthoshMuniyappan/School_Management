package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SchoolRequestDTO;
import com.example.schoolmanagementsystem.service.SchoolService;
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


@WebMvcTest(SchoolController.class)
public class SchoolControllerTest {

    @MockBean
    private SchoolService schoolService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateSchool() throws Exception {
        SchoolRequestDTO schoolRequestDTO=new SchoolRequestDTO();
        schoolRequestDTO.setName("Maharishi higher sec school");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School created successfully");

        when(schoolService.create(schoolRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/school/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Maharishi higher sec school\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School created successfully\"}"));
    }
    @Test
    void testUpdateSchool() throws Exception {
        String id = "1";
        SchoolRequestDTO schoolRequestDTO=new SchoolRequestDTO();
        schoolRequestDTO.setName("bharath Higher sec school");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School updated successfully");

        when(schoolService.update(id, schoolRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/school/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"bharath Higher sec school\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School updated successfully\"}"));
    }
    @Test
    void testRetrieveSchoolById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School retrieved successfully");

        when(schoolService.retrieve(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/school/retrieve/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School retrieved successfully\"}"));
    }
    @Test
    void testRetrieveSchools() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Schools retrieved successfully");

        when(schoolService.retrieveAll()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/school/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Schools retrieved successfully\"}"));
    }
    @Test
    void testDeleteSchool() throws Exception {
        String id = "12";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School deleted successfully");

        when(schoolService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/school/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School deleted successfully\"}"));
    }
}
