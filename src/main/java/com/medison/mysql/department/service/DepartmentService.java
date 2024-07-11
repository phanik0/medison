package com.medison.mysql.department.service;

import com.medison.mysql.department.domain.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public String getNameById(int code) {
        return departmentRepository.findById(code).get().getName();
    }
}
