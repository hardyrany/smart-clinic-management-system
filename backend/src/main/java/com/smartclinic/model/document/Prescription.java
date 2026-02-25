package com.smartclinic.model.document;

import jakarta.persistence.Entity;
import jakarta.persistence.Id; // JPA
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Entity // Para JPA
@Document(collection = "prescriptions") // Para MongoDB
public class Prescription {

    // JPA usa javax.persistence.Id ou jakarta.persistence.Id
    @Id
    @MongoId // MongoDB usa org.springframework.data.annotation.Id
    private String id;

    private String description;
    private String patientName;
    private String doctorName;

    // Construtor padrão
    public Prescription() {}

    // Construtor com campos
    public Prescription(String id, String description, String patientName, String doctorName) {
        this.id = id;
        this.description = description;
        this.patientName = patientName;
        this.doctorName = doctorName;
    }

    // Getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public Object getDoctorId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDoctorId'");
    }

    public Object getPatientId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPatientId'");
    }

    public Object getMedications() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMedications'");
    }

    public Object getNotes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNotes'");
    }

    public void setDoctorId(Object doctorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDoctorId'");
    }

    public void setPatientId(Object patientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPatientId'");
    }

    public void setMedications(Object medications) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMedications'");
    }

    public void setNotes(Object notes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setNotes'");
    }
}