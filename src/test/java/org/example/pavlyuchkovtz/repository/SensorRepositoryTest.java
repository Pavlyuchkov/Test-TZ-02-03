package org.example.pavlyuchkovtz.repository;

import jakarta.transaction.Transactional;
import org.example.pavlyuchkovtz.entity.Sensor;
import org.example.pavlyuchkovtz.util.Range;
import org.example.pavlyuchkovtz.enums.Type;
import org.example.pavlyuchkovtz.enums.Unit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SensorRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    @Autowired
    private SensorRepository sensorRepository;

    @BeforeAll
    public static void setUp() {
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
        System.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");
    }

    @Test
    @Transactional
    public void findByNameAndModelPartly_Ok() {
        Sensor sensor1 = new Sensor();
        sensor1.setName("Сенсор раз");
        sensor1.setModel("Модель один");
        sensor1.setType(Type.TEMPERATURE);
        sensor1.setUnit(Unit.CELSIUS);
        sensor1.setRange(new Range(1, 10));
        sensorRepository.save(sensor1);

        Sensor sensor2 = new Sensor();
        sensor2.setName("Сенсор два");
        sensor2.setModel("Модель два");
        sensor2.setType(Type.PRESSURE);
        sensor2.setUnit(Unit.BAR);
        sensor2.setRange(new Range(5, 15));
        sensorRepository.save(sensor2);

        List<Sensor> result = sensorRepository.findByNameAndModelContaining("Сенсор", "Модель");

        assertEquals(2, result.size());
        assertEquals("Сенсор раз", result.get(0).getName());
        assertEquals("Сенсор два", result.get(1).getName());
    }

    @Test
    @Transactional
    public void findByNameAndModelPartly_NotFound() {
        List<Sensor> result = sensorRepository.findByNameAndModelContaining("привет", "привет");
        assertEquals(0, result.size());
    }
}