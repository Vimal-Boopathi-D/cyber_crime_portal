package com.cyber.portal.bootstrap;

import com.cyber.portal.citizenManagement.entity.Admin;
import com.cyber.portal.citizenManagement.entity.PoliceOfficer;
import com.cyber.portal.citizenManagement.repository.AdminRepository;
import com.cyber.portal.citizenManagement.repository.PoliceOfficerRepository;
import com.cyber.portal.sharedResources.enums.Gender;
import com.cyber.portal.sharedResources.enums.State;
import com.cyber.portal.sharedResources.enums.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PoliceOfficerDataLoader {
    private final PoliceOfficerRepository policeOfficerRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadMockPoliceOfficers() {

        if (policeOfficerRepository.count() > 0) {
            return;
        }

        List<PoliceOfficer> officers = List.of(

                createOfficer("OFF-001", "Inspector Ramesh", "Inspector", State.ANDHRA_PRADESH),
                createOfficer("OFF-002", "Sub-Inspector Naidu", "Sub-Inspector", State.ANDHRA_PRADESH),

                createOfficer("OFF-003", "Inspector Tenzin", "Inspector", State.ARUNACHAL_PRADESH),
                createOfficer("OFF-004", "Sub-Inspector Dorjee", "Sub-Inspector", State.ARUNACHAL_PRADESH),

                createOfficer("OFF-005", "Inspector Das", "Inspector", State.ASSAM),
                createOfficer("OFF-006", "Sub-Inspector Bora", "Sub-Inspector", State.ASSAM),

                createOfficer("OFF-007", "Inspector Kumar", "Inspector", State.BIHAR),
                createOfficer("OFF-008", "Sub-Inspector Singh", "Sub-Inspector", State.BIHAR),

                createOfficer("OFF-009", "Inspector Verma", "Inspector", State.CHHATTISGARH),
                createOfficer("OFF-010", "Sub-Inspector Patel", "Sub-Inspector", State.CHHATTISGARH),

                createOfficer("OFF-011", "Inspector Fernandes", "Inspector", State.GOA),
                createOfficer("OFF-012", "Sub-Inspector D'Souza", "Sub-Inspector", State.GOA),

                createOfficer("OFF-013", "Inspector Shah", "Inspector", State.GUJARAT),
                createOfficer("OFF-014", "Sub-Inspector Mehta", "Sub-Inspector", State.GUJARAT),

                createOfficer("OFF-015", "Inspector Yadav", "Inspector", State.HARYANA),
                createOfficer("OFF-016", "Sub-Inspector Malik", "Sub-Inspector", State.HARYANA),

                createOfficer("OFF-017", "Inspector Thakur", "Inspector", State.HIMACHAL_PRADESH),
                createOfficer("OFF-018", "Sub-Inspector Negi", "Sub-Inspector", State.HIMACHAL_PRADESH),

                createOfficer("OFF-019", "Inspector Soren", "Inspector", State.JHARKHAND),
                createOfficer("OFF-020", "Sub-Inspector Munda", "Sub-Inspector", State.JHARKHAND),

                createOfficer("OFF-021", "Inspector Rao", "Inspector", State.KARNATAKA),
                createOfficer("OFF-022", "Sub-Inspector Shetty", "Sub-Inspector", State.KARNATAKA),

                createOfficer("OFF-023", "Inspector Nair", "Inspector", State.KERALA),
                createOfficer("OFF-024", "Sub-Inspector Menon", "Sub-Inspector", State.KERALA),

                createOfficer("OFF-025", "Inspector Chauhan", "Inspector", State.MADHYA_PRADESH),
                createOfficer("OFF-026", "Sub-Inspector Rathore", "Sub-Inspector", State.MADHYA_PRADESH),

                createOfficer("OFF-027", "Inspector Patil", "Inspector", State.MAHARASHTRA),
                createOfficer("OFF-028", "Sub-Inspector Deshmukh", "Sub-Inspector", State.MAHARASHTRA),

                createOfficer("OFF-029", "Inspector Singh", "Inspector", State.MANIPUR),
                createOfficer("OFF-030", "Sub-Inspector Devi", "Sub-Inspector", State.MANIPUR),

                createOfficer("OFF-031", "Inspector Sangma", "Inspector", State.MEGHALAYA),
                createOfficer("OFF-032", "Sub-Inspector Marak", "Sub-Inspector", State.MEGHALAYA),

                createOfficer("OFF-033", "Inspector Lalruatkima", "Inspector", State.MIZORAM),
                createOfficer("OFF-034", "Sub-Inspector Chhangte", "Sub-Inspector", State.MIZORAM),

                createOfficer("OFF-035", "Inspector Jamir", "Inspector", State.NAGALAND),
                createOfficer("OFF-036", "Sub-Inspector Ao", "Sub-Inspector", State.NAGALAND),

                createOfficer("OFF-037", "Inspector Mohanty", "Inspector", State.ODISHA),
                createOfficer("OFF-038", "Sub-Inspector Behera", "Sub-Inspector", State.ODISHA),

                createOfficer("OFF-039", "Inspector Gill", "Inspector", State.PUNJAB),
                createOfficer("OFF-040", "Sub-Inspector Sandhu", "Sub-Inspector", State.PUNJAB),

                createOfficer("OFF-041", "Inspector Shekhawat", "Inspector", State.RAJASTHAN),
                createOfficer("OFF-042", "Sub-Inspector Rathod", "Sub-Inspector", State.RAJASTHAN),

                createOfficer("OFF-043", "Inspector Tamang", "Inspector", State.SIKKIM),
                createOfficer("OFF-044", "Sub-Inspector Lepcha", "Sub-Inspector", State.SIKKIM),

                createOfficer("OFF-045", "Inspector Kumar", "Inspector", State.TAMIL_NADU),
                createOfficer("OFF-046", "Sub-Inspector Raj", "Sub-Inspector", State.TAMIL_NADU),

                createOfficer("OFF-047", "Inspector Reddy", "Inspector", State.TELANGANA),
                createOfficer("OFF-048", "Sub-Inspector Rao", "Sub-Inspector", State.TELANGANA),

                createOfficer("OFF-049", "Inspector Debnath", "Inspector", State.TRIPURA),
                createOfficer("OFF-050", "Sub-Inspector Chakma", "Sub-Inspector", State.TRIPURA),

                createOfficer("OFF-051", "Inspector Mishra", "Inspector", State.UTTAR_PRADESH),
                createOfficer("OFF-052", "Sub-Inspector Pandey", "Sub-Inspector", State.UTTAR_PRADESH),

                createOfficer("OFF-053", "Inspector Rawat", "Inspector", State.UTTARAKHAND),
                createOfficer("OFF-054", "Sub-Inspector Bisht", "Sub-Inspector", State.UTTARAKHAND),

                createOfficer("OFF-055", "Inspector Banerjee", "Inspector", State.WEST_BENGAL),
                createOfficer("OFF-056", "Sub-Inspector Ghosh", "Sub-Inspector", State.WEST_BENGAL),

                createOfficer("OFF-057", "Inspector Sharma", "Inspector", State.DELHI),
                createOfficer("OFF-058", "Sub-Inspector Verma", "Sub-Inspector", State.DELHI)
        );

        policeOfficerRepository.saveAll(officers);
    }

    private PoliceOfficer createOfficer(
            String code,
            String name,
            String rank,
            State state
    ) {
        return PoliceOfficer.builder()
                .officerCode(code)
                .name(name)
                .rank(rank)
                .state(state)
                .build();
    }

    @PostConstruct
    public void createPoliceAdmin() {

        // prevent duplicate insertion on every restart
        if (adminRepository.existsByMobileNo("9025873326")) {
            return;
        }

        Admin admin = Admin.builder()
                .mobileNo("9025873326")
                .name("Sudha Rani")
                .email("officer@gov.in")
                .password(passwordEncoder.encode("Officer@123"))
                .gender(Gender.FEMALE)
                .role(UserRole.POLICE_ADMIN)
                .build();

        adminRepository.save(admin);
    }
}
