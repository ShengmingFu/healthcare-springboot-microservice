package com.sfu.healthcare.enrolleeservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/***
 * Java entity representing an Enrollee
 */
@Entity
@Table(name = "enrollees")
public class Enrollee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Enrollee name is required")
    private String name;
    @NotNull(message = "Enrollee activation status is required")
    private boolean activation;
    @NotNull(message = "Enrollee birth date status is required")
    @JsonFormat
    private LocalDate birthDate;

    private String phoneNumber;

//    @OneToMany(mappedBy = "enrollee")
//    private List<Dependent> dependents = new LinkedList<>();

    public Enrollee() {
    }

//    public Enrollee(Long id, @NotNull(message = "Enrollee name is required") String name, @NotNull(message = "Enrollee activation status is required") boolean activation, @NotNull(message = "Enrollee birth date status is required") LocalDate birthDate, String phoneNumber, List<Dependent> dependents) {
//        this.id = id;
//        this.name = name;
//        this.activation = activation;
//        this.birthDate = birthDate;
//        this.phoneNumber = phoneNumber;
//        this.dependents = dependents;
//    }


    public Enrollee(Long id, @NotNull(message = "Enrollee name is required") String name, @NotNull(message = "Enrollee activation status is required") boolean activation, @NotNull(message = "Enrollee birth date status is required") LocalDate birthDate, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.activation = activation;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
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

    public boolean isActivation() {
        return activation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    public List<Dependent> getDependents() {
//        return dependents;
//    }
//
//    public void setDependents(List<Dependent> dependents) {
//        this.dependents = dependents;
//    }
}
