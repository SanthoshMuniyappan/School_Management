//package com.example.schoolmanagementsystem.service;
//
//import com.example.schoolmanagementsystem.dto.ResponseDTO;
//import com.example.schoolmanagementsystem.entity.Mark;
//import com.example.schoolmanagementsystem.entity.Section;
//import com.example.schoolmanagementsystem.entity.Subject;
//import com.example.schoolmanagementsystem.entity.Teacher;
//import com.example.schoolmanagementsystem.repository.MarkRepository;
//import com.example.schoolmanagementsystem.repository.SectionRepository;
//import com.example.schoolmanagementsystem.repository.SubjectRepository;
//import com.example.schoolmanagementsystem.repository.TeacherRepository;
//import com.example.schoolmanagementsystem.util.Constants;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CustomService {
//
//    private final SectionRepository sectionRepository;
//    private final SubjectRepository subjectRepository;
//    private final TeacherRepository teacherRepository;
//    private final MarkRepository markRepository;
//
//    public CustomService(final SectionRepository sectionRepository, final SubjectRepository subjectRepository, final TeacherRepository teacherRepository, final MarkRepository markRepository) {
//        this.sectionRepository = sectionRepository;
//        this.subjectRepository = subjectRepository;
//        this.teacherRepository = teacherRepository;
//        this.markRepository = markRepository;
//    }
//
//    public ResponseDTO retrieveAll(final String sectionName, final String subjectName, final String teacherName, final String mark, final String greaterThanOr, final String lessThanOr, final String minValue, final String maxValue) {
//        List<Object> retrieveValues = new ArrayList<>();
//        List<Section> section = this.sectionRepository.findBySection(sectionName);
//        List<Subject> subject = this.subjectRepository.findBySubject(subjectName);
//        List<Teacher> teacher = this.teacherRepository.findByName(teacherName);
//        if (greaterThanOr.equals("true")) {
//            List<Mark> greaterThan = this.markRepository.findByMarkGreaterThan(mark);
//            retrieveValues.add(greaterThan);
//        }
//        if (lessThanOr.equals("true")) {
//            List<Mark> lessThan = this.markRepository.findByMarkLessThan(mark);
//            retrieveValues.add(lessThan);
//        }
//        List<Mark> markRange = this.markRepository.findByMarkRange(minValue, maxValue);
//        retrieveValues.add(section);
//        retrieveValues.add(subject);
//        retrieveValues.add(teacher);
//        retrieveValues.add(markRange);
//
//        return new ResponseDTO(Constants.RETRIEVED, retrieveValues, HttpStatus.OK.getReasonPhrase());
//    }
//}
