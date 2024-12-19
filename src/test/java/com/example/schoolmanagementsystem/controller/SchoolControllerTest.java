package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SchoolRequestDTO;
import com.example.schoolmanagementsystem.service.JwtService;
import com.example.schoolmanagementsystem.service.SchoolService;
import com.example.schoolmanagementsystem.service.UserInfoUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SchoolController.class)
@AutoConfigureMockMvc
public class SchoolControllerTest {

    @MockBean
    private SchoolService schoolService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserInfoUserDetailsService userInfoUserDetailsService;

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void testCreateSchool() throws Exception {
//        SchoolRequestDTO schoolRequestDTO=new SchoolRequestDTO();
//        schoolRequestDTO.setName("Maharishi higher sec school");
//
//        ResponseDTO responseDTO = new ResponseDTO();
//        responseDTO.setMessage("School created successfully");
////        responseDTO.setStatus("Created");
//
//        when(schoolService.create(schoolRequestDTO)).thenReturn(responseDTO);
//
//        mockMvc.perform(post("/api/school/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"Maharishi higher sec school\"}"))
//                .andExpect(status().isOk())
//              .andExpect(content().json("{\"message\":\"School created successfully\"}"));
//
//        verify(schoolService, times(1)).create(schoolRequestDTO);
//    }

    @Test
    @WithMockUser(username = "admin", roles = "SUPER_ADMIN")
    void testCreateSchool_WhenValidRequest_ThenSchoolCreatedSuccessfully() throws Exception {
        // Prepare the input DTO
        SchoolRequestDTO schoolRequestDTO = new SchoolRequestDTO();
        schoolRequestDTO.setName("Maharishi Higher Sec School");
        schoolRequestDTO.setCreatedBy("admin");
        schoolRequestDTO.setUpdatedBy("admin");

        // Mock the response DTO from the service
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School created successfully");
        responseDTO.setStatus("Created");

        // Mock the service layer
        when(schoolService.create(schoolRequestDTO)).thenReturn(responseDTO);

        // Perform the POST request using MockMvc
        mockMvc.perform(post("/api/school/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Maharishi Higher Sec School\", \"createdBy\":\"admin\", \"updatedBy\":\"admin\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk()) // Check for CREATED status (201)
                .andExpect(content().json("{\"message\":\"School created successfully\",\"status\":\"Created\"}"));

        // Verify that the create method of schoolService was called once with the correct argument
        verify(schoolService, times(1)).create(schoolRequestDTO);
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
