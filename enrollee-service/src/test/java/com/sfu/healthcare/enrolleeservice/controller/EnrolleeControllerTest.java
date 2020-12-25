package com.sfu.healthcare.enrolleeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfu.healthcare.enrolleeservice.entity.Dependent;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class EnrolleeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EnrolleeService enrolleeService;

    @Test
    void testGetAllEnrollees() throws Exception {
        List<Enrollee> enrolleeList = new ArrayList<Enrollee>();
        enrolleeList.add(new Enrollee(1L, "Scoot", true, LocalDate.now(), null, null));
        enrolleeList.add(new Enrollee(2L, "Mary", false, LocalDate.now(), null, null));
        ResponseEntity<List<Enrollee>> responseEntity = new ResponseEntity<>(enrolleeList, HttpStatus.OK);
        when(enrolleeService.findAll()).thenReturn(responseEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void getEnrolleeById() throws Exception {
        Long enrolleeId = 1L;
        Enrollee expEnrollee = new Enrollee(1L, "Scoot", true, LocalDate.now(), null, null);
        ResponseEntity<Enrollee> exp = new ResponseEntity<>(expEnrollee, HttpStatus.OK);
        when(enrolleeService.findEnrolleeById(enrolleeId.intValue())).thenReturn(exp);
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollees/{enrolleeId}", enrolleeId.intValue())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(enrolleeId.intValue())))
                .andExpect(jsonPath("name", is("Scoot")))
                .andDo(print());

    }

    @Test
    void getEnrolleesByName() throws Exception {
        String name = "Scoot";
        List<Enrollee> enrolleeList = new ArrayList<Enrollee>();
        enrolleeList.add(new Enrollee(1L, name, true, LocalDate.now(), null, null));
        enrolleeList.add(new Enrollee(2L, name, false, LocalDate.now(), null, null));
        ResponseEntity<List<Enrollee>> responseEntity = new ResponseEntity<>(enrolleeList, HttpStatus.OK);
        when(enrolleeService.findEnrolleesByName(name)).thenReturn(responseEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollees/?name={name}", name).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is(name)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is(name)))
                .andDo(print());
    }

    @Test
    void addEnrollee() throws Exception {
        Long enrolleeId = 1L;
        String name = "Scoot";
        LocalDate birth = LocalDate.now();
        Enrollee postEnrollee = new Enrollee(null, name, true, birth, null, new ArrayList<>());
        Enrollee expEnrollee = new Enrollee(1L, name, true, birth, null, null);
        ResponseEntity<Enrollee> exp = new ResponseEntity<>(expEnrollee, HttpStatus.CREATED);
        when(enrolleeService.addEnrollee(postEnrollee)).thenReturn(exp);
        mockMvc.perform(MockMvcRequestBuilders.post("/enrollees")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(postEnrollee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(enrolleeId.intValue())))
                .andExpect(jsonPath("name", is(name)))
                .andExpect(jsonPath("birthDate", is(birth.toString())))
                .andDo(print());
    }

    @Test
    void updateEnrollee() throws Exception {
        Long enrolleeId = 1L;
        String name = "Scoot";
        LocalDate birth = LocalDate.now();
        Enrollee postEnrollee = new Enrollee(null, name, true, birth, null, new ArrayList<>());
        Enrollee expEnrollee = new Enrollee(1L, name, true, birth, null, null);
        ResponseEntity<Enrollee> exp = new ResponseEntity<>(expEnrollee, HttpStatus.CREATED);
        when(enrolleeService.updateEnrollee(enrolleeId.intValue(), postEnrollee)).thenReturn(exp);
        mockMvc.perform(MockMvcRequestBuilders.put("/enrollees/{enrolleeId}", enrolleeId.intValue())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(postEnrollee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(enrolleeId.intValue())))
                .andExpect(jsonPath("name", is(name)))
                .andExpect(jsonPath("birthDate", is(birth.toString())))
                .andDo(print());
    }

    @Test
    void deleteEnrollee() throws Exception {
        ResponseEntity<Void> exp = new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        when(enrolleeService.deleteEnrollee(1)).thenReturn(exp);
        mockMvc.perform(MockMvcRequestBuilders.delete("/enrollees/{enrolleeId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void addDependentsByEnrolleeId() throws Exception {
        String name = "Mary";
        LocalDate birthDate = LocalDate.now();
        List<Dependent> postDependents = new ArrayList<>();
        postDependents.add(new Dependent(null, name, birthDate, any()));
        List<Dependent> dependents = new ArrayList<>();
        dependents.add(new Dependent(1L, name, birthDate, any()));
        ResponseEntity<List<Dependent>> exp = new ResponseEntity<List<Dependent>>(dependents, HttpStatus.CREATED);
        when(enrolleeService.addDependents(1, postDependents)).thenReturn(exp);
        mockMvc.perform(MockMvcRequestBuilders.post("/enrollees/{enrolleeId}/dependents", 1)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(postDependents)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(name)));
    }

    @Test
    void deleteDependentsByEnrolleeId() throws Exception {
        ResponseEntity<Void> exp = new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        when(enrolleeService.deleteAllDependentsByEnrolleeId(1)).thenReturn(exp);
        mockMvc.perform(MockMvcRequestBuilders.delete("/enrollees/{enrolleeId}/dependents", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteDependentByDependentId() throws Exception {
        ResponseEntity<Void> exp = new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        when(enrolleeService.deleteDependentByDependentId(1, 1)).thenReturn(exp);
        mockMvc.perform(MockMvcRequestBuilders.delete("/enrollees/{enrolleeId}/dependents/{dependentId}", 1, 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateDependent() throws Exception {
        String name = "Mary";
        Dependent postDependent = new Dependent(null, name, LocalDate.now(), new Enrollee());
        ResponseEntity<Dependent> exp = new ResponseEntity<Dependent>(new Dependent(1L, name, LocalDate.now(), new Enrollee()),
                HttpStatus.CREATED);
        when(enrolleeService.updateDependent(1, 1, postDependent)).thenReturn(exp);
        mockMvc.perform(MockMvcRequestBuilders.put("/enrollees/{enrolleeId}/dependents/{dependentId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postDependent))).andExpect(status().isOk());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
