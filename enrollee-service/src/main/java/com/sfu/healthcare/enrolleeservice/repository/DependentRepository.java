package com.sfu.healthcare.enrolleeservice.repository;

import com.sfu.healthcare.enrolleeservice.entity.Dependent;
import com.sfu.healthcare.enrolleeservice.entity.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DependentRepository extends JpaRepository<Dependent, Long> {
    List<Dependent> findAllByEnrolleeId(Long enrolleeId);
}
