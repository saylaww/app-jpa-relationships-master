package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.dao.StudentDAO;
import uz.pdp.appjparelationships.entity.Address;
import uz.pdp.appjparelationships.entity.Group;
import uz.pdp.appjparelationships.entity.Student;
import uz.pdp.appjparelationships.repository.AddressRepository;
import uz.pdp.appjparelationships.repository.GroupRepository;
import uz.pdp.appjparelationships.repository.StudentRepository;

import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    GroupRepository groupRepository;

    //1. VAZIRLIK
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //2. UNIVERSITY
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId,
                                                     @RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
        return studentPage;
    }

    //3. FACULTY DEKANAT
    @GetMapping("/forFaculty/{id}")
    public Page<Student> getStudentListForFaculty(@PathVariable Integer id, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_FacultyId(id, pageable);

        return studentPage;
    }

    //4. GROUP OWNER
    @GetMapping("/forGroup/{id}")
    public Page<Student> getStudentListForGroup(@PathVariable Integer id, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroupId(id, pageable);

        return studentPage;
    }

    //Create
    @PostMapping
    public String save(@RequestBody StudentDAO studentDAO) {
        Student student = new Student();
        student.setFirstName(studentDAO.getFirstName());
        student.setLastName(studentDAO.getLastName());

        Optional<Address> optionalAddress = addressRepository.findById(studentDAO.getAddressId());
        if (!optionalAddress.isPresent())
            return "Address not found!";
        student.setAddress(optionalAddress.get());

        Optional<Group> optionalGroup = groupRepository.findById(studentDAO.getGroupId());
        if (!optionalGroup.isPresent())
            return "Group not found!";
        student.setGroup(optionalGroup.get());

        studentRepository.save(student);
        return "Student saved!";
    }

    //Update
    @PutMapping("/{id}")
    public String update(@RequestBody StudentDAO studentDAO, @PathVariable Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isPresent()){
            optionalStudent.get().setFirstName(studentDAO.getFirstName());
            optionalStudent.get().setLastName(studentDAO.getLastName());

            Optional<Address> optionalAddress = addressRepository.findById(studentDAO.getAddressId());
            if (!optionalAddress.isPresent())
                return "Address not found!";

            Optional<Group> optionalGroup = groupRepository.findById(studentDAO.getGroupId());
            if (!optionalGroup.isPresent())
                return "Group not found!";

            studentRepository.save(optionalStudent.get());
            return "Student updated!";
        }
        return "Student not found!";
    }

    //Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        studentRepository.deleteById(id);

        return "Student deleted!";
    }


}
