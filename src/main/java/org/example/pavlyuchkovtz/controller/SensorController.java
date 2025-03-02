package org.example.pavlyuchkovtz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pavlyuchkovtz.dto.SensorDto;
import org.example.pavlyuchkovtz.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@Validated
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('Administrator', 'Viewer')")
    public ResponseEntity<List<SensorDto>> getAllSensors() {
        List<SensorDto> sensors = sensorService.getAllSensors();
        return ResponseEntity.ok(sensors);
        }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrator', 'Viewer')")
    public ResponseEntity<SensorDto> getSensorById(@PathVariable Long id) {
        SensorDto sensorDto = sensorService.getSensorById(id);
        return ResponseEntity.ok(sensorDto);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('Administrator', 'Viewer')")
    public ResponseEntity<List<SensorDto>> searchSensors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String model) {
        List<SensorDto> sensors = sensorService.searchSensors(name, model);
        return ResponseEntity.ok(sensors);
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<String> createSensor(@Valid @RequestBody SensorDto sensorDto) {
        sensorService.createSensor(sensorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Сенсор успешно добавлен!");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<String> updateSensor(@PathVariable Long id,
                                               @Valid @RequestBody SensorDto sensorDto) {
        sensorService.updateSensor(id, sensorDto);
        return ResponseEntity.ok("Данные сенсора успешно изменены!");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<String> deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.ok("Сенсор успешно удален!");
    }
}