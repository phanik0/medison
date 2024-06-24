package com.medison.search.service;

import com.medison.detail.domain.Study;
import com.medison.detail.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final StudyRepository studyRepository;


    public Page<Study> findAllWithPage(Pageable pageable) {
        return studyRepository.findAll(pageable);
    }
}
