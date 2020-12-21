package com.sfu.healthcare.enrolleeservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dependents")
public class Dependent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Dependent name is required")
    private String name;
    @NotNull(message = "Dependent birth date is required")
    @JsonFormat
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "enrollee_id")
    private Enrollee enrollee;

    public Dependent() {
    }

    public Dependent(Long id, @NotNull(message = "Dependent name is required") String name, @NotNull(message = "Dependent birth date is required") LocalDate birthDate, Enrollee enrollee) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.enrollee = enrollee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Enrollee getEnrollee() {
        return enrollee;
    }

    public void setEnrollee(Enrollee enrollee) {
        this.enrollee = enrollee;
    }
}
