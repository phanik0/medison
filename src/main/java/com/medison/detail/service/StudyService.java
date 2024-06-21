package com.medison.detail.service;

import com.medison.detail.domain.Study;
import com.medison.detail.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyService {
    private final StudyRepository studyRepository;

    public List<Study> getAllStudy() {
        return studyRepository.findAll();
    }

}
