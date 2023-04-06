package dev.archie.landscapeservice.integration.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Testcontainers
@SpringBootTest
public class AbstractIntegrationTest {

    public static final String POSTGRE_USERNAME = "postgres";
    public static final String POSTGRE_DATABASE = "postgres";

    @Container
    public static PostgreSQLContainer postgre = new PostgreSQLContainer<>("postgres:latest")
            .withNetworkAliases("postgre")
            .withDatabaseName(POSTGRE_DATABASE)
            .withUsername(POSTGRE_USERNAME)
            .withExposedPorts(5432)
            .withStartupTimeout(Duration.ofMinutes(10))
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(PostgreSQLContainer.class)));

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgre::getJdbcUrl);
        registry.add("spring.datasource.password", postgre::getPassword);
        registry.add("spring.datasource.username", postgre::getUsername);
    }

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
}
