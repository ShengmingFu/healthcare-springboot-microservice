package com.sfu.healthcare.enrolleeservice.controller;

import com.sfu.healthcare.enrolleeservice.entity.Enrollee;
import com.sfu.healthcare.enrolleeservice.service.EnrolleeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class EnrolleeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EnrolleeService enrolleeService;

    @Test
    void getAllEnrollees() throws Exception {
        List<Enrollee> enrolleeList = new ArrayList<Enrollee>();
//        enrolleeList.add(new Enrollee(1L,"Scoot", true, LocalDate.now(), null,null));
//        enrolleeList.add(new Enrollee(2L,"Mary", false, LocalDate.now(), null, null));
        enrolleeList.add(new Enrollee(1L,"Scoot", true, LocalDate.now(), null));
        enrolleeList.add(new Enrollee(2L,"Mary", false, LocalDate.now(), null));
        ResponseEntity<List<Enrollee>> responseEntity = new ResponseEntity<>(enrolleeList, HttpStatus.OK);


        when(enrolleeService.findAll()).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/enrollees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }
}
