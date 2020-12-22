package com.sfu.healthcare.enrolleeservice.service;

import com.sfu.healthcare.enrolleeservice.entity.Enrollee;
import com.sfu.healthcare.enrolleeservice.repository.EnrolleeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EnrolleeServiceTest {

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @Test
    void findAll() {
        Enrollee enrollee = new Enrollee(1L,"Scoot", true, LocalDate.now(), null, null);
//        Enrollee enrollee = new Enrollee(1L,"Scoot", true, LocalDate.now(), null);
        enrolleeRepository.save(enrollee);
        EnrolleeService service = new EnrolleeService(enrolleeRepository);

        ResponseEntity<List<Enrollee>> enrolleeResp = service.findAll();
        Enrollee exp = enrolleeResp.getBody().get(enrolleeResp.getBody().size()-1);

        assertEquals(enrollee.getName(), exp.getName());
        assertEquals(enrollee.getBirthDate(), exp.getBirthDate());

    }

    @Test
    void getById() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}