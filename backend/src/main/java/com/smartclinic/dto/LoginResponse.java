package com.smartclinic.dto;

public class LoginResponse {
    private boolean success;
    private String message;
    private Long doctorId;
    private String doctorName;
    private String doctorEmail;
    private String specialty;

    // Construtor privado para forçar uso dos factory methods
    private LoginResponse() {
    }

    private LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    private LoginResponse(boolean success, String message, Long doctorId, 
                          String doctorName, String doctorEmail, String specialty) {
        this.success = success;
        this.message = message;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        this.specialty = specialty;
    }

    // Factory methods
    public static LoginResponse success(String message, Long doctorId, 
                                       String doctorName, String doctorEmail, 
                                       String specialty) {
        return new LoginResponse(true, message, doctorId, doctorName, doctorEmail, specialty);
    }

    public static LoginResponse failure(String message) {
        return new LoginResponse(false, message);
    }

    // Getters e Setters (todos)
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}