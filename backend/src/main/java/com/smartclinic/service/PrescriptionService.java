package com.smartclinic.service;

import com.smartclinic.model.document.Prescription;
import com.smartclinic.repository.mongo.PrescriptionMongoRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionMongoRepository mongoRepository;

    public Prescription addPrescription(Prescription prescription) {
        prescription.setIssuedAt(LocalDateTime.now());
        return mongoRepository.save(prescription);
    }

    public List<Prescription> getAllPrescriptions() {
        return mongoRepository.findAll();
    }

    public Optional<Prescription> getPrescriptionById(String id) {
        return mongoRepository.findById(id);
    }

    public Optional<Prescription> updatePrescription(String id, Prescription details) {
        return mongoRepository.findById(id).map(existing -> {
            existing.setDoctorId(details.getDoctorId());
            existing.setPatientId(details.getPatientId());
            existing.setMedications(details.getMedications());
            existing.setNotes(details.getNotes());
            return mongoRepository.save(existing);
        });
    }

    public boolean deletePrescription(String id) {
        if (mongoRepository.existsById(id)) {
            mongoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return mongoRepository.findByPatientId(patientId);
    }

    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        return mongoRepository.findByDoctorId(doctorId);
    }
}