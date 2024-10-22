package com.medison.mysql.patient.service;

import com.medison.mysql.patient.domain.Patient;
import com.medison.mysql.patient.domain.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient findPatientById(String pCode) {
        return patientRepository.findById(pCode).orElse(null);
    }
}
