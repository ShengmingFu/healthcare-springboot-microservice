package com.sfu.healthcare.enrolleeservice.service;

import com.sfu.healthcare.enrolleeservice.entity.Dependent;
import com.sfu.healthcare.enrolleeservice.entity.Enrollee;
import com.sfu.healthcare.enrolleeservice.repository.DependentRepository;
import com.sfu.healthcare.enrolleeservice.repository.EnrolleeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class EnrolleeServiceTest {
    @Autowired
    private EnrolleeService service;
    @MockBean
    private EnrolleeRepository enrolleeRepository;
    @MockBean
    private DependentRepository dependentRepository;

    @Test
    void testFindAll() {
        List<Enrollee> enrollees = new ArrayList<>();
        enrollees.add(new Enrollee(1L, "Scoot", true, LocalDate.now(), null, null));
        enrollees.add(new Enrollee(2L, "May", true, LocalDate.now(), null, null));

        doReturn(enrollees).when(enrolleeRepository).findAll();

        List<Enrollee> act = service.findAll().getBody();
        assertEquals(2, act.size());
    }

    @Test
    void testFindAllNotFound() {
        doReturn(new ArrayList<Enrollee>()).when(enrolleeRepository).findAll();
        ResponseEntity<List<Enrollee>> act = service.findAll();
        assertEquals(HttpStatus.NOT_FOUND, act.getStatusCode());
    }

    @Test
    void testAddEnrollee() {
        Enrollee enrollee = new Enrollee(1L, "Scoot", true, LocalDate.now(), null, null);
        ResponseEntity<Enrollee> exp = ResponseEntity.status(HttpStatus.CREATED).body(enrollee);
        doReturn(exp).when(enrolleeRepository).save(any(Enrollee.class));
        ResponseEntity<Enrollee> actual = service.addEnrollee(any(Enrollee.class));

        assertTrue(actual.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testAddEnrolleeBadRequest() {
        Enrollee enrollee = new Enrollee(1L, "Scoot", true, LocalDate.now(), null, null);
        ResponseEntity<Enrollee> exp = ResponseEntity.status(HttpStatus.CREATED).body(enrollee);
        doReturn(exp).when(enrolleeRepository).save(any());
        ResponseEntity<Enrollee> actual = service.addEnrollee(any());
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void testFindEnrolleeById() {
        Long id = 1L;
        Enrollee enrollee = new Enrollee(id, "Scoot", true, LocalDate.now(), null, null);
        doReturn(Optional.of(enrollee)).when(enrolleeRepository).findById(id);

        Optional<Enrollee> actual = Optional.of(service.findEnrolleeById(id.intValue()).getBody());
        assertTrue(actual.isPresent());
        assertSame(actual.get(), enrollee);
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 11L;
        doReturn(Optional.empty()).when(enrolleeRepository).findById(id);
        ResponseEntity<Enrollee> actual = service.findEnrolleeById(id.intValue());
        assertTrue(actual.getStatusCode().is4xxClientError());
    }

    @Test
    void findEnrolleesByName() {
        String name = "May";
        Enrollee enrollee1 = new Enrollee(1L, "May", true, LocalDate.now(), null, null);
        Enrollee enrollee2 = new Enrollee(11L, "May", true, LocalDate.now(), null, null);
        doReturn(Arrays.asList(enrollee1, enrollee2)).when(enrolleeRepository).findByNameIgnoreCase(name);

        Optional<List<Enrollee>> actual = Optional.of(service.findEnrolleesByName(name).getBody());
        assertTrue(actual.isPresent());
        assertEquals(actual.get().size(), 2);
    }

    @Test
    void testUpdateEnrollee() {
        Enrollee enrollee = new Enrollee(1L, "Scoot", true, LocalDate.now(), null, null);
        Enrollee newEnrollee = new Enrollee(null, "Scoot", false, LocalDate.now(), null, null);
        ResponseEntity<Enrollee> exp = ResponseEntity.status(HttpStatus.OK).body(enrollee);
        doReturn(Optional.of(enrollee)).when(enrolleeRepository).findById(1L);
        doReturn(enrollee).when(enrolleeRepository).save(newEnrollee);
        ResponseEntity<Enrollee> actual = service.updateEnrollee(1, newEnrollee);

        assertTrue(actual.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testUpdateEnrolleeNotFound() {
        Enrollee newEnrollee = new Enrollee(null, "Scoot", false, LocalDate.now(), null, null);
        doReturn(Optional.empty()).when(enrolleeRepository).findById(any(Long.class));
        ResponseEntity<Enrollee> actual = service.updateEnrollee(3, newEnrollee);

        assertTrue(actual.getStatusCode().is4xxClientError());
    }

    @Test
    void testUpdateEnrolleeBadRequest() {
        Enrollee enrollee = new Enrollee(1L, "Scoot", true, LocalDate.now(), null, null);
        doReturn(Optional.of(enrollee)).when(enrolleeRepository).findById(1L);
        ResponseEntity<Enrollee> actual = service.updateEnrollee(1, any(Enrollee.class));
        assertTrue(actual.getStatusCode().is4xxClientError());
    }

    @Test
    void testDeleteEnrollee() {
        doNothing().when(enrolleeRepository).deleteById(1L);
        ResponseEntity<Void> actual = service.deleteEnrollee(1);
        assertTrue(actual.getStatusCode().is2xxSuccessful());

    }

    @Test
    void addDependents() {
        List<Dependent> newDependents = new ArrayList<>();
        newDependents.add(new Dependent(null, "Tom", LocalDate.now(), null));

        Enrollee enrollee = new Enrollee(1L, "Scoot", true, LocalDate.now(), null, null);
        Dependent preparedDependent = new Dependent(null, "Tom", LocalDate.now(), enrollee);
        Dependent addedDependent = new Dependent(1L, "Tom", LocalDate.now(), enrollee);
        doReturn(Optional.of(enrollee)).when(enrolleeRepository).findById(1L);
        doReturn(Arrays.asList(addedDependent)).when(dependentRepository).saveAll(Arrays.asList(preparedDependent));

        ResponseEntity<List<Dependent>> actual = service.addDependents(1, newDependents);
        assertTrue(actual.getStatusCode().is2xxSuccessful());
        assertEquals(1, actual.getBody().size());
    }

    @Test
    void deleteAllDependentsByEnrolleeId() {
        Dependent dependent = new Dependent(1L, "Ken", LocalDate.now(), null);
        Enrollee enrollee = new Enrollee(1L, "Scoot", true, LocalDate.now(), null, Arrays.asList(dependent));
        dependent.setEnrollee(enrollee);
        doNothing().when(dependentRepository).deleteById(1L);
        doReturn(Optional.of(enrollee)).when(enrolleeRepository).findById(1L);
        ResponseEntity<Void> actual = service.deleteAllDependentsByEnrolleeId(1);
        assertTrue(actual.getStatusCode().is2xxSuccessful());
    }

    @Test
    void deleteDependentByDependentId() {
        Dependent dependent = new Dependent(1L, "Ken", LocalDate.now(), null);
        Enrollee enrollee = new Enrollee(1L, "Scoot", true, LocalDate.now(), null, Arrays.asList(dependent));
        dependent.setEnrollee(enrollee);
        doReturn(Optional.of(enrollee)).when(enrolleeRepository).findById(1L);
        doReturn(dependent).when(dependentRepository).getOne(1L);
        doNothing().when(dependentRepository).deleteById(1L);
        ResponseEntity<Void> actual = service.deleteDependentByDependentId(1, 1);
        assertTrue(actual.getStatusCode().is2xxSuccessful());
    }

    @Test
    void updateDependent() {
        Dependent dependent = new Dependent(1L, "Ken", LocalDate.now(), null);
        Dependent newDependent = new Dependent(null, "Ken", LocalDate.now(), null);
        Enrollee enrollee = new Enrollee(1L, "Scoot", true, LocalDate.now(), null, Arrays.asList(dependent));
        dependent.setEnrollee(enrollee);
        doReturn(Optional.of(enrollee)).when(enrolleeRepository).findById(1L);
        doReturn(Optional.of(dependent)).when(dependentRepository).findById(1L);
        ResponseEntity<Dependent> actual = service.updateDependent(1, 1, newDependent);
        assertTrue(actual.getStatusCode().is2xxSuccessful());
    }
}