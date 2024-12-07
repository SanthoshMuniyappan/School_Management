package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.SectionRequestDTO;
import com.example.schoolmanagementsystem.entity.Section;
import com.example.schoolmanagementsystem.entity.Standard;
import com.example.schoolmanagementsystem.repository.SectionRepository;
import com.example.schoolmanagementsystem.repository.StandardRepository;
import com.example.schoolmanagementsystem.service.SectionService;
import com.example.schoolmanagementsystem.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SectionServiceTest {

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private StandardRepository standardRepository;

    @InjectMocks
    private SectionService sectionService;

    @Test
    public void testCreate() {
        String id = "23";
        SectionRequestDTO sectionRequestDTO = new SectionRequestDTO();
        sectionRequestDTO.setName("10th A");
        sectionRequestDTO.setMaximumCapacity("30");
        sectionRequestDTO.setStandardId(id);
        sectionRequestDTO.setCreatedBy("jano");
        sectionRequestDTO.setUpdatedBy("jano");

        Section section = new Section();
        Standard standard = new Standard();
        when(this.standardRepository.findById(id)).thenReturn(Optional.of(standard));
        section.setName(sectionRequestDTO.getName());
        section.setMaximumCapacity(sectionRequestDTO.getMaximumCapacity());
        section.setStandard(standard);
        section.setCreatedBy(sectionRequestDTO.getCreatedBy());
        section.setUpdatedBy(sectionRequestDTO.getUpdatedBy());
        when(this.sectionRepository.save(Mockito.any(Section.class))).thenReturn(section);

        ResponseDTO responseDTO = this.sectionService.create(sectionRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(section, data);

        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testUpdate() {
        String id = "98";
        SectionRequestDTO sectionRequestDTO = new SectionRequestDTO();
        sectionRequestDTO.setName("10th A");
        sectionRequestDTO.setMaximumCapacity("30");
        sectionRequestDTO.setUpdatedBy("anto");

        Section section = new Section();
        section.setName(sectionRequestDTO.getName());
        section.setMaximumCapacity(sectionRequestDTO.getMaximumCapacity());
        section.setUpdatedBy(sectionRequestDTO.getUpdatedBy());

        when(this.sectionRepository.findById(id)).thenReturn(Optional.of(section));
        when(this.sectionRepository.save(section)).thenReturn(section);

        ResponseDTO responseDTO = this.sectionService.update(id, sectionRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, section);

        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieve() {
        String id = "60";

        Section section = new Section();
        when(this.sectionRepository.findById(id)).thenReturn(Optional.of(section));

        ResponseDTO responseDTO = this.sectionService.retrieve(id);
        Object data = responseDTO.getData();
        assertEquals(data, section);

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRetrieveAll() {
        List<Section> sections = new ArrayList<>();
        when(this.sectionRepository.findAll()).thenReturn(sections);

        ResponseDTO responseDTO = this.sectionService.retrieveAll();

        assertEquals(sections, responseDTO.getData());

        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }

    @Test
    public void testRemove() {
        String id = "17";

        when(this.sectionRepository.existsById(id)).thenReturn(true);

        ResponseDTO responseDTO = this.sectionService.remove(id);

        Object data = responseDTO.getData();
        assertEquals(data, id);

        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatus().toUpperCase());
    }
}
