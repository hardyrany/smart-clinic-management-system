package com.smartclinic.controller;

import com.smartclinic.model.entity.Doctor;
import com.smartclinic.service.AppointmentService;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AppointmentService appointmentService;

    /**
     * GET endpoint to retrieve doctor's availability.
     *
     * URL format: /api/doctors/availability/{user}/{doctorId}/{date}/{token}
     *
     * @param user     Role of the requester (e.g., patient, admin)
     * @param doctorId ID of the doctor
     * @param date     Desired date for availability
     * @param token    Authorization token
     * @return List of available times or an error message
     */
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<?> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String token) {

        // Validate token
        if (!tokenService.isValid(token, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
        }

        // Retrieve doctor
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Doctor not found");
        }

        // Get available times for the given date
        List<String> availableTimes = appointmentService.getAvailableTimesForDate(doctorId, date);

        return ResponseEntity.ok(availableTimes);
    }
}