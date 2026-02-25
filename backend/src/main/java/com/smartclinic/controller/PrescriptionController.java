package com.smartclinic.controller;

import com.smartclinic.model.document.Prescription;
import com.smartclinic.service.PrescriptionService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private TokenService tokenService;

    // Create a new prescription with validation and token
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> addPrescription(
            @PathVariable String token,
            @Valid @RequestBody Prescription prescription) {

        Map<String, String> response = new HashMap<>();
        if (!tokenService.isValid(token, "doctor")) {
            response.put("status", "error");
            response.put("message", "Unauthorized token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        prescriptionService.addPrescription(prescription);
        response.put("status", "success");
        response.put("message", "Prescription created successfully");
        return ResponseEntity.ok(response);
    }

    // Get all prescriptions
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    // Get prescription by ID
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable String id) {
        return prescriptionService.getPrescriptionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update prescription
    @PutMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> updatePrescription(
            @PathVariable String id,
            @PathVariable String token,
            @Valid @RequestBody Prescription prescriptionDetails) {

        Map<String, String> response = new HashMap<>();
        if (!tokenService.isValid(token, "doctor")) {
            response.put("status", "error");
            response.put("message", "Unauthorized token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return prescriptionService.updatePrescription(id, prescriptionDetails)
                .map(updated -> {
                    response.put("status", "success");
                    response.put("message", "Prescription updated successfully");
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("status", "error");
                    response.put("message", "Prescription not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    // Delete prescription
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> deletePrescription(
            @PathVariable String id,
            @PathVariable String token) {

        Map<String, String> response = new HashMap<>();
        if (!tokenService.isValid(token, "doctor")) {
            response.put("status", "error");
            response.put("message", "Unauthorized token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        boolean deleted = prescriptionService.deletePrescription(id);
        if (deleted) {
            response.put("status", "success");
            response.put("message", "Prescription deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Prescription not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get prescriptions by patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatient(patientId));
    }

    // Get prescriptions by doctor
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByDoctor(doctorId));
    }
}