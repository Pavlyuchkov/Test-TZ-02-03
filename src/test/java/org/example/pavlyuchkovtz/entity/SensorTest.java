package org.example.pavlyuchkovtz.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.pavlyuchkovtz.enums.Type;
import org.example.pavlyuchkovtz.enums.Unit;
import org.example.pavlyuchkovtz.util.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SensorTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testValidSensor_Ok() {
        Sensor sensor = new Sensor();
        sensor.setName("Сенсор 1");
        sensor.setModel("Модель 1");
        sensor.setType(Type.TEMPERATURE);
        sensor.setUnit(Unit.CELSIUS);
        sensor.setLocation("Кухня");
        sensor.setDescription("Описание первого сенсора");
        sensor.setRange(new Range(0, 100));
        
        Set<ConstraintViolation<Sensor>> violations = validator.validate(sensor);
        assertTrue(violations.isEmpty(), "Неожиданное количество ошибок валидации");
    }

    @Test
    public void testSensorNameValidation() {
        Sensor sensor = new Sensor();
        sensor.setName("Се");
        sensor.setModel("Модель1");
        sensor.setType(Type.TEMPERATURE);
        sensor.setUnit(Unit.CELSIUS);
        sensor.setLocation("Кухня");
        sensor.setDescription("Описание первого сенсора");
        sensor.setRange(new Range(0, 100));

        Set<ConstraintViolation<Sensor>> violations = validator.validate(sensor);
        assertFalse(violations.isEmpty(), "Ошибки валидации должны быть");
        assertEquals(1, violations.size(), "Количество ошибок должно быть равно 1");
        
        ConstraintViolation<Sensor> violation = violations.iterator().next();
        assertEquals("Название датчика должно быть от 3 до 30 символов", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }

    @Test
    public void testSensorModelValidation() {
        Sensor sensor = new Sensor();
        sensor.setName("Сенсор");
        sensor.setModel("ОченьДлинноеИмяМоделиБольше15Символов");
        sensor.setType(Type.TEMPERATURE);
        sensor.setUnit(Unit.CELSIUS);
        sensor.setLocation("Кухня");
        sensor.setDescription("Описание первого сенсора");
        sensor.setRange(new Range(0, 100));

        Set<ConstraintViolation<Sensor>> violations = validator.validate(sensor);
        assertFalse(violations.isEmpty(), "Ошибки валидации должны быть");
        assertEquals(1, violations.size(), "Количество ошибок должно быть равно 1");

        ConstraintViolation<Sensor> violation = violations.iterator().next();
        assertEquals("Название модели не должно превышать 15 символов", violation.getMessage());
        assertEquals("model", violation.getPropertyPath().toString());
    }

    @Test
    public void testValidRangeValidation() {
        Sensor sensor = new Sensor();
        sensor.setName("Сенсор");
        sensor.setModel("Модель1");
        sensor.setType(Type.TEMPERATURE);
        sensor.setUnit(Unit.CELSIUS);
        sensor.setLocation("Кухня");
        sensor.setDescription("Описание первого сенсора");
        sensor.setRange(new Range(100, 50));

        Set<ConstraintViolation<Sensor>> violations = validator.validate(sensor);
        assertFalse(violations.isEmpty(), "Ошибки валидации должны быть");
        assertEquals(1, violations.size(), "Количество ошибок должно быть равно 1");

        // Проверяем, что ошибка для поля 'range'
        ConstraintViolation<Sensor> violation = violations.iterator().next();
        assertEquals("Поле 'Радиус от' должно быть меньше, чем поле 'Радиус до'", violation.getMessage());
        assertEquals("validRange", violation.getPropertyPath().toString());
    }
}
