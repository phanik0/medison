package com.medison.mysql.patient.controller;

import com.medison.mysql.patient.domain.Patient;
import com.medison.mysql.patient.domain.PatientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PatientController {

    private final PatientRepository patientRepository;

//    @GetMapping("/patient")
//    public ResponseEntity<Patient> getPatient(@RequestParam("pid") int pid) {
//        Optional<Patient> patient = patientRepository.findById(pid);
//        return patient.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
}