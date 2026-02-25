package com.smartclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartclinic.model.document.Prescription;
import com.smartclinic.model.entity.Appointment;
import com.smartclinic.model.entity.Doctor;
import com.smartclinic.model.entity.Patient;
import com.smartclinic.model.entity.User;

public class Repository {

    public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);
    }

    public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    }

    public interface PatientRepository extends JpaRepository<Patient, Long> {
    }

    public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    }

    public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
    }

}
