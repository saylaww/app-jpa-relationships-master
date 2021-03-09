package uz.pdp.appjparelationships.dao;

import lombok.Data;

@Data
public class ClassesDAO {

    private String name;
    private Integer subjectId;
    private Integer teacherId;
    private Integer maxStudents;
}
