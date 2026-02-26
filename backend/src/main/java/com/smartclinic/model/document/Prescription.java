package com.smartclinic.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Document(collection = "prescriptions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    @Id
    private String id;

    private Long patientId;       
    private Long doctorId;     
    private String patientName;
    private String doctorName;
    private String medications;
    private String notes;
    private LocalDateTime issuedAt;
}