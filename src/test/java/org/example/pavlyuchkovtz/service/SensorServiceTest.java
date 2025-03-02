package org.example.pavlyuchkovtz.service;

import org.example.pavlyuchkovtz.dto.SensorDto;
import org.example.pavlyuchkovtz.entity.Sensor;
import org.example.pavlyuchkovtz.exception.NotFoundException;
import org.example.pavlyuchkovtz.mapper.SensorMapper;
import org.example.pavlyuchkovtz.repository.SensorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private SensorMapper sensorMapper;

    @InjectMocks
    private SensorService sensorService;

    private Sensor sensor;
    private SensorDto sensorDto;

    @BeforeEach
    void setUp() {
        sensor = new Sensor();
        sensor.setId(1L);
        sensor.setName("TEST");
        sensor.setModel("YY-90");

        sensorDto = new SensorDto();
        sensorDto.setId(1L);
        sensorDto.setName("TEST");
        sensorDto.setModel("YY-90");
    }

    @Test
    void getAllSensors() {
        when(sensorRepository.findAll()).thenReturn(Collections.singletonList(sensor));
        when(sensorMapper.toDto(sensor)).thenReturn(sensorDto);

        List<SensorDto> result = sensorService.getAllSensors();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("TEST", result.get(0).getName());
        verify(sensorRepository, times(1)).findAll();
    }

    @Test
    void getSensorById_Found() {
        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));
        when(sensorMapper.toDto(sensor)).thenReturn(sensorDto);

        SensorDto result = sensorService.getSensorById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Optional.of(1L), Optional.of(result.getId()));
    }

    @Test
    void getSensorById_NotFound() {
        when(sensorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sensorService.getSensorById(1L));
    }

    @Test
    void createSensor_Ok() {
        when(sensorMapper.toEntity(sensorDto)).thenReturn(sensor);
        when(sensorRepository.save(sensor)).thenReturn(sensor);
        when(sensorMapper.toDto(sensor)).thenReturn(sensorDto);

        sensorService.createSensor(sensorDto);

        verify(sensorRepository, times(1)).save(sensor);
    }

    @Test
    void updateSensor_Ok() {
        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));
        doNothing().when(sensorMapper).updateEntityFromDto(sensorDto, sensor);
        when(sensorRepository.save(sensor)).thenReturn(sensor);
        when(sensorMapper.toDto(sensor)).thenReturn(sensorDto);

        sensorService.updateSensor(1L, sensorDto);

        verify(sensorRepository, times(1)).save(sensor);
    }

    @Test
    void deleteSensor_Ok() {
        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));
        doNothing().when(sensorRepository).delete(sensor);

        sensorService.deleteSensor(1L);

        verify(sensorRepository, times(1)).delete(sensor);
    }
}
