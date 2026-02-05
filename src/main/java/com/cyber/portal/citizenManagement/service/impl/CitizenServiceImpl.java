package com.cyber.portal.citizenManagement.service.impl;

import com.cyber.portal.citizenManagement.entity.Admin;
import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.citizenManagement.entity.PoliceOfficer;
import com.cyber.portal.citizenManagement.entity.PoliceStation;
import com.cyber.portal.citizenManagement.repository.AdminRepository;
import com.cyber.portal.citizenManagement.repository.CitizenRepository;
import com.cyber.portal.citizenManagement.repository.PoliceOfficerRepository;
import com.cyber.portal.citizenManagement.repository.PoliceStationRepository;
import com.cyber.portal.citizenManagement.service.CitizenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {
    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;
    private final PoliceOfficerRepository policeOfficerRepository;
    private final PoliceStationRepository policeStationRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public Citizen registerCitizen(Citizen citizen) {
        String encryptedPassword = passwordEncoder.encode(citizen.getPassword());
        citizen.setPassword(encryptedPassword);
        return citizenRepository.save(citizen);
    }

    @Override
    public Optional<Citizen> getCitizenByLoginId(String email, String password) {
        Optional<Citizen> citizen = Optional.ofNullable(citizenRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Citizen not found")));
        if (!passwordEncoder.matches(password, citizen.get().getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return citizen;
    }

    @Override
    public List<PoliceStation> getAllPoliceStation() {
        return policeStationRepository.findAll();
    }

    @Override
    public List<PoliceOfficer> getAllPoliceOfficers() {
        return policeOfficerRepository.findAll();
    }

    @Override
    public Optional<Admin> getAdminByLoginId(String email, String password) {
        Optional<Admin> admin = Optional.ofNullable(adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found")));
        if (!passwordEncoder.matches(password, admin.get().getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return admin;
    }
}
