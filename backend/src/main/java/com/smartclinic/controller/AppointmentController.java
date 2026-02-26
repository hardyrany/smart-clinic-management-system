package com.smartclinic.controller;

import com.smartclinic.model.entity.Appointment;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TokenService tokenService;

    // Criar uma nova consulta
    @PostMapping
    public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentService.addAppointment(appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    // Listar todas as consultas
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = ((Optional<Appointment>) appointmentService.getAppointmentById(id))
                .orElse(null);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id,
            @RequestBody Appointment appointmentDetails) {
        Optional<Appointment> updated = (Optional<Appointment>) appointmentService.updateAppointment(id, appointmentDetails);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar consulta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        boolean deleted = appointmentService.deleteAppointment(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // Listar consultas por paciente
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    // Listar consultas por médico
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    // Endpoint seguro para disponibilidade do médico
    @GetMapping("/doctor/{doctorId}/availability/{date}/{token}")
    public ResponseEntity<?> getDoctorAvailability(
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String token) {

        // Corrigido: validação do token
        if (!tokenService.isValid(token, "patient")) { // ou "admin", dependendo do caso
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }

        List<String> availableTimes = appointmentService.getAvailableTimesForDoctorOnDate(doctor, date);
        return ResponseEntity.ok(availableTimes);
    }
}