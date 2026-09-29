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
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.database.repository.OrganizationRepository;
import com.toro.backend.infrastructure.database.repository.UserRepository;

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

    
    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Seeder running on startup...");

        cleanUp();
        seedAccounts();
        seedOrganization();

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

    private User getSeedUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalStateException("Seed user not found: " + email));
    }

}
