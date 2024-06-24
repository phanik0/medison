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

    public Study getStudyByStudyKey(long studyKey) {
        return studyRepository.findById(studyKey).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 검사입니다.")
        );
    }
}
