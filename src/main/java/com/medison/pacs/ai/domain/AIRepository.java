package com.medison.pacs.ai.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIRepository extends JpaRepository<AI, String> {
    public List<AI>findByStudyKey(long studKey);
}
