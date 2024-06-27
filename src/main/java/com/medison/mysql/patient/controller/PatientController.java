package com.medison.mysql.patient.controller;

import com.medison.mysql.patient.domain.Patient;
import com.medison.mysql.patient.domain.PatientRepository;

import com.medison.mysql.patient.service.PatientService;
import com.medison.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PatientController {

    private final PatientService patientService;
    private final SearchService searchService;

    @ResponseBody
    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
      List<Patient> result = patientService.findAll();
      return result;
 }

}