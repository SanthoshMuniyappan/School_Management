package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Mark;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, String> {

    List<Mark> findByMarkGreaterThan(final String value);

    @Query(nativeQuery = true, value = "SELECT * FROM mark WHERE mark<:value")
    List<Mark> findByMarkLessThan(@Param("value") final String value);

    @Query(nativeQuery = true, value = "SELECT * FROM mark WHERE mark>:minValue AND mark<:maxValue")
    List<Mark> findByMarkRange(@Param("minValue") final String minValue, @Param("maxValue") final String maxValue);

    @Query("select s as sectionName,sub as subjectName,t as teacherName,m as mark " +
            "FROM Section s,Subject sub ,Teacher t,Mark m " +
            "WHERE s.name=:sectionName AND sub.subjectName=:subjectName " +
            "AND t.name=:teacherName AND (true=:greaterThan AND m.mark>:rangeValue)")
    List<Tuple> findByAllFiltered(@Param("sectionName") final String sectionName,
                                  @Param("subjectName") final String subjectName,
                                  @Param("teacherName") final String teacherName,
                                  @Param("rangeValue") final String rangeValue,
                                  @Param("greaterThan") final boolean greaterThan);
}
