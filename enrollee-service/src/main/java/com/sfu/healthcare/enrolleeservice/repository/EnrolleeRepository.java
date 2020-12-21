package com.sfu.healthcare.enrolleeservice.repository;

import com.sfu.healthcare.enrolleeservice.entity.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
    List<Enrollee> findByNameIgnoreCase(String name);
}
