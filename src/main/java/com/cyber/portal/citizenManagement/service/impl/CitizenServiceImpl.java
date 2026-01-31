package com.cyber.portal.citizenManagement.service.impl;

import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.citizenManagement.repository.CitizenRepository;
import com.cyber.portal.citizenManagement.service.CitizenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {
    private final CitizenRepository citizenRepository;

    @Override
    public Citizen registerCitizen(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    @Override
    public Optional<Citizen> getCitizenByLoginId(String loginId) {
        return citizenRepository.findByLoginId(loginId);
    }
}
