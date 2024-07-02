package com.medison.pacs.study.service;

import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.domain.StudyRepository;
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
    public List<Study> getAllStudies() {
        return studyRepository.findAll();
    }

}
