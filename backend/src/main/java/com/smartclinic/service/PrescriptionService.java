package com.smartclinic.service;

import com.smartclinic.model.document.Prescription;
import com.smartclinic.repository.jpa.PrescriptionJpaRepository;
import com.smartclinic.repository.mongo.PrescriptionMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    private final PrescriptionJpaRepository jpaRepository;
    private final PrescriptionMongoRepository mongoRepository;

    public PrescriptionService(PrescriptionJpaRepository jpaRepository,
                               PrescriptionMongoRepository mongoRepository) {
        this.jpaRepository = jpaRepository;
        this.mongoRepository = mongoRepository;
    }

    // Create
    public Prescription addPrescription(Prescription prescription) {
        jpaRepository.save(prescription);
        return mongoRepository.save(prescription);
    }

    // Read all
    public List<Prescription> getAllPrescriptions() {
        // Pode retornar do JPA ou Mongo (a mesma entidade)
        return jpaRepository.findAll();
    }

    // Read by ID
    public Optional<Prescription> getPrescriptionById(String id) {
        return jpaRepository.findById(id)
                .or(() -> mongoRepository.findById(id));
    }

    // Update
public Optional<Prescription> updatePrescription(String id, Prescription prescriptionDetails) {
    Optional<Prescription> updated = Optional.empty();

    // Tenta atualizar no JPA
    if (jpaRepository.existsById(id)) {
        updated = jpaRepository.findById(id).map(prescription -> {
            prescription.setDoctorId(prescriptionDetails.getDoctorId());
            prescription.setPatientId(prescriptionDetails.getPatientId());
            prescription.setMedications(prescriptionDetails.getMedications());
            prescription.setNotes(prescriptionDetails.getNotes());
            Prescription savedJPA = jpaRepository.save(prescription);

            // Sincroniza com Mongo
            if (mongoRepository.existsById(id)) {
                mongoRepository.save(savedJPA);
            }
            return savedJPA;
        });
    } 
    // Se não existe no JPA, tenta no Mongo
    else if (mongoRepository.existsById(id)) {
        updated = mongoRepository.findById(id).map(prescription -> {
            prescription.setDoctorId(prescriptionDetails.getDoctorId());
            prescription.setPatientId(prescriptionDetails.getPatientId());
            prescription.setMedications(prescriptionDetails.getMedications());
            prescription.setNotes(prescriptionDetails.getNotes());
            Prescription savedMongo = mongoRepository.save(prescription);

            // Sincroniza com JPA
            jpaRepository.save(savedMongo);
            return savedMongo;
        });
    }

    return updated;
}

    // Delete
    public boolean deletePrescription(String id) {
        boolean deleted = false;
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            deleted = true;
        }
        if (mongoRepository.existsById(id)) {
            mongoRepository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }

    // List by patient
    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        List<Prescription> jpaList = jpaRepository.findByPatientId(patientId);
        List<Prescription> mongoList = mongoRepository.findByPatientId(patientId);
        jpaList.addAll(mongoList); // combina os resultados
        return jpaList;
    }

    // List by doctor
    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        List<Prescription> jpaList = jpaRepository.findByDoctorId(doctorId);
        List<Prescription> mongoList = mongoRepository.findByDoctorId(doctorId);
        jpaList.addAll(mongoList);
        return jpaList;
    }
}