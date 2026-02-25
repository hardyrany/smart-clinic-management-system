package com.smartclinic.service;

import com.smartclinic.model.entity.Appointment;
import com.smartclinic.model.entity.Doctor;
import com.smartclinic.repository.mongo.AppointmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Criar uma nova consulta
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Listar todas as consultas
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Obter consulta por ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // Atualizar consulta
    public Optional<Appointment> updateAppointment(Long id, Appointment appointmentDetails) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointment.setPatient(appointmentDetails.getPatient());
            appointment.setDoctor(appointmentDetails.getDoctor());
            appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
            appointment.setReason(appointmentDetails.getReason());
            return appointmentRepository.save(appointment);
        });
    }

    // Deletar consulta
    public boolean deleteAppointment(Long id) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointmentRepository.delete(appointment);
            return true;
        }).orElse(false);
    }

    // Listar consultas por paciente
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Listar consultas por médico
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<String> getAvailableTimesForDate(Long id, LocalDate date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableTimesForDate'");
    }

    public List<String> getAvailableTimesForDoctorOnDate(Doctor doctor, LocalDate date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableTimesForDoctorOnDate'");
    }

    // Get appointments by doctor ID and specific date
    public List<Appointment> getAppointmentsByDoctorIdAndDate(Long doctorId, LocalDate date) {
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);
    }
}