package com.toro.backend.infrastructure.database.seed;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.toro.backend.infrastructure.database.enums.AppRole;
import com.toro.backend.infrastructure.database.enums.OrganizationDepartment;
import com.toro.backend.infrastructure.database.enums.OrganizationRole;
import com.toro.backend.infrastructure.database.enums.OrganizationType;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.infrastructure.database.models.Vessel;
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.database.repository.OrganizationRepository;
import com.toro.backend.infrastructure.database.repository.UserRepository;
import com.toro.backend.infrastructure.database.repository.VesselRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final VesselRepository vesselRepository;

    
    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Seeder running on startup...");

        cleanUp();
        seedAccounts();
        seedOrganization();
        seedVessels();

    }


    // PRIVATE METHODS
    @Transactional
    private void cleanUp() {
        List<String> tableNames = jdbcTemplate.queryForList("""
            SELECT tablename
            FROM pg_tables
            WHERE schemaname = 'public'
            AND tablename NOT IN (
                'flyway_schema_history',
                'databasechangelog',
                'databasechangeloglock'
            )
            """, String.class);

        if (tableNames.isEmpty()) {
            return;
        }

        String tables = tableNames.stream()
            .map(tableName -> "\"" + tableName.replace("\"", "\"\"") + "\"")
            .collect(Collectors.joining(", "));

        jdbcTemplate.execute(
            "TRUNCATE TABLE " + tables + " RESTART IDENTITY CASCADE"
        );

        System.out.println("All tables cleaned and sequences reset.");
    }

    private void seedAccounts() {

        try {
            User adminAccount = User.builder()
                .fullName("System Administrator")
                .email("admin@example.com")
                .phoneNumber("0123456789")
                .passwordHash(passwordEncoder.encode("123456"))
                .isActive(true)
                .role(AppRole.ADMIN)
                .build();

            User managerAccount = User.builder()
                .fullName("System Manager")
                .email("manager@example.com")
                .phoneNumber("0909123123")
                .passwordHash(passwordEncoder.encode("123456"))
                .isActive(true)
                .role(AppRole.USER)
                .build();

            User mainStaffAccount = User.builder()
                .fullName("System Main Staff")
                .email("mainstaff@example.com")
                .phoneNumber("0909111111")
                .passwordHash(passwordEncoder.encode("123456"))
                .isActive(true)
                .role(AppRole.USER)
                .build();

            User secondStaffAccount = User.builder()
                .fullName("System Second Staff")
                .email("secondstaff@example.com")
                .phoneNumber("0909321321")
                .passwordHash(passwordEncoder.encode("123456"))
                .isActive(true)
                .role(AppRole.USER)
                .build();


            List<User> users = List.of(
                adminAccount, 
                managerAccount,
                mainStaffAccount,
                secondStaffAccount);
            userRepository.saveAll(users);

            System.out.println("Users seeded successfully.");
        } catch (Exception ex) {
            System.err.println("Error seeding users: " + ex.getMessage());
        }

    }

    private void seedOrganization() {
        
        try {
            Organization organization = Organization.builder()
                .organizationCode("TORO-SUPPLIER")
                .organizationName("TORO Supplier")
                .organizationType(OrganizationType.SUPPLIER)
                .taxCode("TAX-TORO-SUPPLIER")
                .blockchainWallet("0x0000000000000000000000000000000000000000")
                .build();

            Organization savedOrganization = organizationRepository.save(organization);

            User manager = getSeedUser("manager@example.com");
            User mainStaff = getSeedUser("mainstaff@example.com");
            User secondStaff = getSeedUser("secondstaff@example.com");

            List<OrganizationMember> members = List.of(
                OrganizationMember.builder()
                    .user(manager)
                    .organization(savedOrganization)
                    .organizationRole(OrganizationRole.MANAGER)
                    .organizationDepartment(OrganizationDepartment.SUPPLIER)
                    .build(),
                OrganizationMember.builder()
                    .user(mainStaff)
                    .organization(savedOrganization)
                    .organizationRole(OrganizationRole.MAIN_STAFF)
                    .organizationDepartment(OrganizationDepartment.SUPPLIER)
                    .build(),
                OrganizationMember.builder()
                    .user(secondStaff)
                    .organization(savedOrganization)
                    .organizationRole(OrganizationRole.SECOND_STAFF)
                    .organizationDepartment(OrganizationDepartment.SUPPLIER)
                    .build()
            );

            organizationMemberRepository.saveAll(members);

            System.out.println("Organizations seeded successfully.");
        } catch (Exception ex) {
            System.err.println("Error seeding organizations: " + ex.getMessage());
        }

    }

    private void seedVessels() {

        try {
            List<Vessel> vessels = List.of(
                Vessel.builder()
                    .registrationNumber("VN-BD-0001")
                    .fishingMethod("Longline")
                    .captainName("Nguyen Van An")
                    .fishingLicense("FL-BD-0001")
                    .imoNumber("IMO1000001")
                    .build(),
                Vessel.builder()
                    .registrationNumber("VN-KH-0002")
                    .fishingMethod("Purse seine")
                    .captainName("Tran Minh Bao")
                    .fishingLicense("FL-KH-0002")
                    .imoNumber("IMO1000002")
                    .build(),
                Vessel.builder()
                    .registrationNumber("VN-QN-0003")
                    .fishingMethod("Gillnet")
                    .captainName("Le Quoc Cuong")
                    .fishingLicense("FL-QN-0003")
                    .imoNumber("IMO1000003")
                    .build(),
                Vessel.builder()
                    .registrationNumber("VN-BR-0004")
                    .fishingMethod("Trawl")
                    .captainName("Pham Duc Duy")
                    .fishingLicense("FL-BR-0004")
                    .imoNumber("IMO1000004")
                    .build(),
                Vessel.builder()
                    .registrationNumber("VN-CT-0005")
                    .fishingMethod("Handline")
                    .captainName("Hoang Hai Nam")
                    .fishingLicense("FL-CT-0005")
                    .imoNumber("IMO1000005")
                    .build()
            );

            vesselRepository.saveAll(vessels);

            System.out.println("Vessels seeded successfully.");
        } catch (Exception ex) {
            System.err.println("Error seeding vessels: " + ex.getMessage());
        }

    }

    private User getSeedUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalStateException("Seed user not found: " + email));
    }

}
