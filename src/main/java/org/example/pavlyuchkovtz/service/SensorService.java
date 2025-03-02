package org.example.pavlyuchkovtz.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.pavlyuchkovtz.dto.SensorDto;
import org.example.pavlyuchkovtz.entity.Sensor;
import org.example.pavlyuchkovtz.exception.NotFoundException;
import org.example.pavlyuchkovtz.mapper.SensorMapper;
import org.example.pavlyuchkovtz.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
    private final SensorMapper sensorMapper;

    public List<SensorDto> getAllSensors() {
        return sensorRepository.findAll()
                .stream()
                .map(sensorMapper::toDto)
                .collect(Collectors.toList());
    }

    public SensorDto getSensorById(Long id) {
        Optional<Sensor> foundSensor = sensorRepository.findById(id);
        if (foundSensor.isEmpty()) throw new NotFoundException("Сенсор с таким ID не найден");
        return sensorMapper.toDto(foundSensor.get());
    }

    public List<SensorDto> searchSensors(String name, String model) {
        List<Sensor> sensors = sensorRepository.findByNameAndModelContaining(name, model);
        if (sensors.isEmpty()) {
            throw new NotFoundException("Нет подходящего по запросу сенсора");
        }
        return sensors.stream()
                .map(sensorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createSensor(SensorDto sensorDto) {
        Sensor sensor = sensorMapper.toEntity(sensorDto);
        Sensor savedSensor = sensorRepository.save(sensor);
        sensorMapper.toDto(savedSensor);
    }

    @Transactional
    public void updateSensor(Long id, SensorDto sensorDto) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сенсор с таким ID не найден"));
        sensorMapper.updateEntityFromDto(sensorDto, sensor);
        Sensor updatedSensor = sensorRepository.save(sensor);
        sensorMapper.toDto(updatedSensor);
    }

    @Transactional
    public void deleteSensor(Long id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сенсор с таким ID не найден"));
        sensorRepository.delete(sensor);
    }
}
