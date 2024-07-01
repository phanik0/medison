package com.medison.mysql.emergency.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface EmergencyRepository extends JpaRepository<Emergency, Integer> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE emergency_management", nativeQuery = true)
    public void truncateTable();
}
