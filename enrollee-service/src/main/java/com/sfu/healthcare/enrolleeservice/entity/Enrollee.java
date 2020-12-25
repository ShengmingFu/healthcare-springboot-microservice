package com.sfu.healthcare.enrolleeservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/***
 * Java entity representing an Enrollee
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate birthDate;

    private String phoneNumber;

    @OneToMany(mappedBy = "enrollee")
    private List<Dependent> dependents = new LinkedList<>();
}
