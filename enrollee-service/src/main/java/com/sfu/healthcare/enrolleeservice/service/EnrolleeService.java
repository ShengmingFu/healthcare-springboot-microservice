package com.sfu.healthcare.enrolleeservice.service;

import com.sfu.healthcare.enrolleeservice.entity.Dependent;
import com.sfu.healthcare.enrolleeservice.entity.Enrollee;
import com.sfu.healthcare.enrolleeservice.repository.DependentRepository;
import com.sfu.healthcare.enrolleeservice.repository.EnrolleeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrolleeService {
    @Autowired
    private EnrolleeRepository enrolleeRepository;
    @Autowired
    private DependentRepository dependentRepository;

    public EnrolleeService(EnrolleeRepository enrolleeRepository) {
        this.enrolleeRepository = enrolleeRepository;
    }

    public ResponseEntity<List<Enrollee>> findAll() {
        List<Enrollee> enrollees = enrolleeRepository.findAll();

        return enrollees.isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(enrollees);
    }

    public ResponseEntity<Enrollee> addEnrollee(Enrollee enrollee) {
        Enrollee addEnrollee;
        try {
            addEnrollee = enrolleeRepository.save(enrollee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(addEnrollee);
    }

    public ResponseEntity<Enrollee> findEnrolleeById(int enrolleeId) {
        return enrolleeRepository.findById(Long.valueOf(enrolleeId))
                .map(enrollee -> ResponseEntity.ok(enrollee))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Enrollee>> findEnrolleesByName(String name) {
        Optional<List<Enrollee>> optionalEnrollees = Optional.of(enrolleeRepository.findByNameIgnoreCase(name));

        return optionalEnrollees.get().isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(optionalEnrollees.get());
    }

    public ResponseEntity<Enrollee> updateEnrollee(int id, Enrollee enrollee) {
        return enrolleeRepository.findById(Long.valueOf(id))
                .map(e -> {
                    Enrollee saveEnrollee = null;
                    try {
                        enrollee.setId(Long.valueOf(id));
                        saveEnrollee = enrolleeRepository.save(enrollee);
                    } catch (Exception exception) {
                        return ResponseEntity.badRequest().body(saveEnrollee);
                    }
                    return ResponseEntity.ok(saveEnrollee);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteEnrollee(int id) {
        try {
            enrolleeRepository.deleteById(Long.valueOf(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<Dependent>> addDependents(int enrolleeId,
                                                         List<Dependent> dependents) {
        return enrolleeRepository.findById(Long.valueOf(enrolleeId))
                .map(enrollee -> {
                    dependents.forEach(dependent -> {
                        dependent.setEnrollee(enrollee);
                    });

                    List<Dependent> dependentResp = null;
                    try {
                        dependentResp = dependentRepository.saveAll(dependents);
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body(dependents);
                    }

                    return ResponseEntity.status(HttpStatus.CREATED).body(dependentResp);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteAllDependentsByEnrolleeId(int enrolleeId) {
        Optional<Enrollee> enrollee = enrolleeRepository.findById(Long.valueOf(enrolleeId));

        if (enrollee.isPresent()) {
            try {
                dependentRepository.findAllByEnrolleeId(Long.valueOf(enrolleeId)).forEach(dependent -> dependentRepository.deleteById(dependent.getId()));
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }


        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<Void> deleteDependentByDependentId(int enrolleeId, int dependentId) {
        Optional<Enrollee> enrolleeOpt = enrolleeRepository.findById(Long.valueOf(enrolleeId));

        if (enrolleeOpt.isPresent()) {
            try {
                if (enrolleeId == dependentRepository.getOne(Long.valueOf(dependentId)).getEnrollee().getId().intValue()) {
                    dependentRepository.deleteById(Long.valueOf(dependentId));
                    return ResponseEntity.noContent().build();
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<Dependent> updateDependent(int enrolleeId, int dependentId, Dependent dependent) {
        Optional<Enrollee> enrollee = enrolleeRepository.findById(Long.valueOf(enrolleeId));

        if (enrollee.isPresent()) {
            return dependentRepository.findById(Long.valueOf(dependentId))
                    .map(findDependent -> {
                        try {
                            dependent.setEnrollee(enrollee.get());
                            dependent.setId(Long.valueOf(dependentId));
                            dependentRepository.save(dependent);
                        } catch (Exception e) {
                            return ResponseEntity.badRequest().body(dependent);
                        }
                        return ResponseEntity.ok(dependent);
                    }).orElseGet(() -> ResponseEntity.badRequest().build());
        }

        return ResponseEntity.badRequest().build();
    }
}
