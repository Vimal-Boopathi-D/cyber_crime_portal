package com.cyber.portal.citizenManagement.repository;

import com.cyber.portal.citizenManagement.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByMobileNo(String mobileNo);
    Optional<Admin> findByEmail(String email);
}
