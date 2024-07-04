package com.medison.pacs.ai.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIRepository extends JpaRepository<AI, String> {

}
