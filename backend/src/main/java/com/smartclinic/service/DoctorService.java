package com.smartclinic.service;

import com.smartclinic.model.entity.Appointment;
import com.smartclinic.model.entity.Doctor;
import com.smartclinic.repository.jpa.DoctorRepository;
import com.smartclinic.dto.LoginRequest;
import com.smartclinic.exception.InvalidCredentialsException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentService appointmentService;
    private final JdbcTemplate jdbcTemplate;

    public DoctorService(DoctorRepository doctorRepository,
            AppointmentService appointmentService, JdbcTemplate jdbcTemplate) {
        this.doctorRepository = doctorRepository;
        this.appointmentService = appointmentService;
        this.jdbcTemplate = jdbcTemplate;
    }

    // --- CRUD MÉDICO ---

    @Transactional
    public Doctor addDoctor(Doctor doctor) {
        validateDoctor(doctor);

        if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um médico cadastrado com este email: " + doctor.getEmail());
        }

        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Transactional
    public Optional<Doctor> updateDoctor(Long id, Doctor doctorDetails) {
        return doctorRepository.findById(id).map(existingDoctor -> {
            if (!existingDoctor.getEmail().equals(doctorDetails.getEmail())) {
                if (doctorRepository.findByEmail(doctorDetails.getEmail()).isPresent()) {
                    throw new IllegalArgumentException(
                            "Email já está em uso por outro médico: " + doctorDetails.getEmail());
                }
            }

            updateDoctorFields(existingDoctor, doctorDetails);
            return doctorRepository.save(existingDoctor);
        });
    }

    @Transactional
    public boolean deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            return false;
        }

        List<Appointment> doctorAppointments = appointmentService.getAppointmentsByDoctorId(id);
        if (!doctorAppointments.isEmpty()) {
            throw new IllegalStateException("Não é possível deletar médico com consultas agendadas");
        }

        doctorRepository.deleteById(id);
        return true;
    }

    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        if (specialty == null || specialty.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return doctorRepository.findBySpecialty(specialty);
    }

    // --- MÉTODOS ADICIONAIS ---

    public Doctor findById(Long doctorId) {
        return doctorRepository.findById(doctorId).orElse(null);
    }

    public List<String> getAvailableTimesForDate(Doctor doctor, LocalDate date) {
        if (doctor == null) {
            return Collections.emptyList();
        }

        return appointmentService.getAvailableTimesForDate(doctor.getId(), date);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, String>> validateLogin(LoginRequest loginRequest) {
        Map<String, String> response = new HashMap<>();
        
        // Validação de entrada
        if (loginRequest == null ||
                loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty() ||
                loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            
            response.put("message", "Email e senha são obrigatórios");
            response.put("status", "error");
            response.put("timestamp", String.valueOf(System.currentTimeMillis()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String email = loginRequest.getEmail().trim();
        String password = loginRequest.getPassword();

        // Busca o médico pelo email
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);

        if (doctorOpt.isEmpty()) {
            response.put("message", "Email ou senha inválidos");
            response.put("status", "error");
            response.put("timestamp", String.valueOf(System.currentTimeMillis()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Doctor doctor = doctorOpt.get();

        // Verifica a senha
        if (!password.equals(doctor.getPassword())) {
            response.put("message", "Email ou senha inválidos");
            response.put("status", "error");
            response.put("timestamp", String.valueOf(System.currentTimeMillis()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Resposta de sucesso
        response.put("message", "Login realizado com sucesso");
        response.put("status", "success");
        response.put("doctorId", String.valueOf(doctor.getId()));
        response.put("doctorName", doctor.getName());
        response.put("doctorEmail", doctor.getEmail());
        response.put("doctorSpecialty", doctor.getSpecialty());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        
        return ResponseEntity.ok(response);
    }

    @Transactional(readOnly = true)
    public Doctor authenticateDoctor(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new InvalidCredentialsException("Email e senha são obrigatórios");
        }

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Email ou senha inválidos"));

        if (!password.equals(doctor.getPassword())) {
            throw new InvalidCredentialsException("Email ou senha inválidos");
        }

        return doctor;
    }

    // --- MÉTODOS UTILITÁRIOS ---

    private void validateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        if (doctor.getEmail() == null || doctor.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (doctor.getName() == null || doctor.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (doctor.getPassword() == null || doctor.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
    }

    private void updateDoctorFields(Doctor existing, Doctor updated) {
        existing.setName(updated.getName());
        existing.setSpecialty(updated.getSpecialty());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setAvailableTimes(updated.getAvailableTimes());

        if (updated.getPassword() != null && !updated.getPassword().trim().isEmpty()) {
            existing.setPassword(updated.getPassword());
        }
    }

    public List<String> checkDatabaseTables() {
        return jdbcTemplate.queryForList("SHOW TABLES", String.class);
    }

}