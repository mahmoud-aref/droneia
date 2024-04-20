package com.elmenus.droneia.configs;

import com.elmenus.droneia.DroneiaApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.lang.String.format;

@Testcontainers
@SpringBootTest(classes = DroneiaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIT {

    private static final String DATABASE_NAME = "droneia-db";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASSWORD = "password";

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName(DATABASE_NAME)
            .withUsername(DATABASE_USER)
            .withPassword(DATABASE_PASSWORD);

    static  {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void configureAppProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", BaseIT::getConnectionUrl);
        registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername);
        registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);
    }


    private static String getConnectionUrl() {
        return format("r2dbc:pool:postgresql://%s:%d/%s",
                postgreSQLContainer.getHost(),
                postgreSQLContainer.getFirstMappedPort(),
                postgreSQLContainer.getDatabaseName());
    }


}
