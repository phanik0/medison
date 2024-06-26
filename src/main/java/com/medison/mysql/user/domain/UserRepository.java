package com.medison.mysql.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    public List<User> findByDepartmentCodeOrderByRegDateDesc(int departmentCode);
}
