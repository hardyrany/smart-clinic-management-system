package com.smartclinic.repository.jpa;

import com.smartclinic.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Find doctors by specialty
    List<Doctor> findBySpecialty(String specialty);

    Optional<Doctor> findByEmail(String email);
}