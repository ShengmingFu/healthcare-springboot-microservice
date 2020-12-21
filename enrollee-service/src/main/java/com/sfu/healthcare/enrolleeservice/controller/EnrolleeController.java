package com.sfu.healthcare.enrolleeservice.controller;

import com.sfu.healthcare.enrolleeservice.entity.Dependent;
import com.sfu.healthcare.enrolleeservice.entity.Enrollee;
import com.sfu.healthcare.enrolleeservice.service.EnrolleeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/enrollees")
public class EnrolleeController {

    @Autowired
    EnrolleeService enrolleeService;

    @Operation(summary = "Get all enrollees")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Enrollee.class)))}),
            @ApiResponse(responseCode = "404", description = "No enrollee found", content = @Content)})
    @GetMapping
    public ResponseEntity<List<Enrollee>> getAllEnrollees() {
        return enrolleeService.findAll();
    }

    @Operation(summary = "Get a enrollee by enrollee id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Enrollee.class)))}),
            @ApiResponse(responseCode = "404", description = "No enrollee found", content = @Content)})
    @GetMapping("/{enrolleeId}")
    public ResponseEntity<Enrollee> getEnrolleeById(@PathVariable("enrolleeId") int enrolleeId) {
        return enrolleeService.findEnrolleeById(enrolleeId);
    }

    @Operation(summary = "Get enrollees by insensitive enrollee name")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Enrollee.class)))}),
            @ApiResponse(responseCode = "404", description = "No enrollee found", content = @Content)})
    @GetMapping("/")
    public ResponseEntity<List<Enrollee>> getEnrolleesByName(@RequestParam String name) {
        return enrolleeService.findEnrolleesByName(name);
    }

    @Operation(summary = "Create a enrollee")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Enrollee.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad enrollee information", content = @Content)})
    @PostMapping
    public ResponseEntity<Enrollee> addEnrollee(@RequestBody @Valid Enrollee enrollee) {
        return enrolleeService.addEnrollee(enrollee);
    }

    @Operation(summary = "Update a enrollee")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Updated", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Enrollee.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad dependents information", content = @Content),
            @ApiResponse(responseCode = "404", description = "No enrollee available for update", content = @Content)})
    @PutMapping("/{enrolleeId}")
    public ResponseEntity<Enrollee> updateEnrollee(@PathVariable("enrolleeId") int id,
                                                   @RequestBody @Valid Enrollee enrollee) {
        return enrolleeService.updateEnrollee(id, enrollee);
    }

    @Operation(summary = "Delete a enrollee.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "403", description = "Failed", content = @Content)})
    @DeleteMapping("/{enrolleeId}")
    public ResponseEntity<Void> deleteEnrollee(@PathVariable("enrolleeId") int enrolleeId) {
        return enrolleeService.deleteEnrollee(enrolleeId);
    }

    @Operation(summary = "Create dependents by enrollee ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Dependent.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad dependents information", content = @Content),
            @ApiResponse(responseCode = "404", description = "Create dependents failed because no enrollee found",
                    content = @Content)})
    @PostMapping("/{enrolleeId}/dependents")
    public ResponseEntity<List<Dependent>> addDependentsByEnrolleeId(@PathVariable("enrolleeId") int enrolleeId,
                                                                     @RequestBody List<Dependent> dependents) {
        return enrolleeService.addDependents(enrolleeId, dependents);
    }

    @Operation(summary = "Delete dependents by enrollee id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "404", description = "Delete failed because enrollee not found",
                    content = @Content)})
    @DeleteMapping("/{enrolleeId}/dependents")
    public ResponseEntity<Void> deleteDependentsByEnrolleeId(@PathVariable("enrolleeId") int enrolleeId) {
        return enrolleeService.deleteAllDependentsByEnrolleeId(enrolleeId);
    }

    @Operation(summary = "Delete a dependent by dependent id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "Delete failed because enrollee not found or invalidated" +
                    "dependent id",
                    content = @Content)})
    @DeleteMapping("/{enrolleeId}/dependents/{dependentId}")
    public ResponseEntity<Void> deleteDependentByDependentId(@PathVariable("enrolleeId") int enrolleeId, @PathVariable("dependentId") int dependentId) {
        return enrolleeService.deleteDependentByDenpendentId(enrolleeId, dependentId);
    }

    @Operation(summary = "Update a dependent")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Updated", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Enrollee.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad dependents information or no correct enrollee found",
                    content = @Content)})
    @PutMapping("/{enrolleeId}/dependents/{dependentId}")
    public ResponseEntity<Dependent> updateDependent(@PathVariable("enrolleeId") int enrolleeId,
                                                     @PathVariable("dependentId") int dependentId,
                                                     @RequestBody @Valid Dependent dependent) {
        return enrolleeService.updateDependent(enrolleeId, dependentId, dependent);
    }
}
