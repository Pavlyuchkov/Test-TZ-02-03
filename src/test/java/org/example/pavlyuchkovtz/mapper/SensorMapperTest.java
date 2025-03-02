package org.example.pavlyuchkovtz.mapper;

import org.example.pavlyuchkovtz.dto.RangeDto;
import org.example.pavlyuchkovtz.dto.SensorDto;
import org.example.pavlyuchkovtz.entity.Sensor;
import org.example.pavlyuchkovtz.enums.Type;
import org.example.pavlyuchkovtz.enums.Unit;
import org.example.pavlyuchkovtz.util.Range;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SensorMapperTest {

    private final SensorMapper sensorMapper = Mappers.getMapper(SensorMapper.class);

    @Test
    public void testToEntity() {
        SensorDto sensorDto = new SensorDto();
        sensorDto.setName("Термометр");
        sensorDto.setModel("ТТ-10");
        sensorDto.setType("TEMPERATURE");
        sensorDto.setUnit("CELSIUS");
        sensorDto.setLocation("Кухня");
        sensorDto.setDescription("Сенсор температуры");
        sensorDto.setRange(new RangeDto(0, 100));

        Sensor sensor = sensorMapper.toEntity(sensorDto);

        assertEquals(sensorDto.getName(), sensor.getName());
        assertEquals(sensorDto.getModel(), sensor.getModel());
        assertEquals(Type.TEMPERATURE, sensor.getType());
        assertEquals(Unit.CELSIUS, sensor.getUnit());
        assertEquals(sensorDto.getLocation(), sensor.getLocation());
        assertEquals(sensorDto.getDescription(), sensor.getDescription());
        assertEquals(0, sensor.getRange().getRangeFrom());
        assertEquals(100, sensor.getRange().getRangeTo());
    }

    @Test
    public void testToDto() {
        Sensor sensor = new Sensor();
        sensor.setName("Термометр");
        sensor.setModel("ТТ-10");
        sensor.setType(Type.TEMPERATURE);
        sensor.setUnit(Unit.CELSIUS);
        sensor.setLocation("Кухня");
        sensor.setDescription("Сенсор температуры");
        sensor.setRange(new Range(0, 100));

        SensorDto sensorDto = sensorMapper.toDto(sensor);

        assertEquals(sensor.getName(), sensorDto.getName());
        assertEquals(sensor.getModel(), sensorDto.getModel());
        assertEquals("TEMPERATURE", sensorDto.getType());
        assertEquals("CELSIUS", sensorDto.getUnit());
        assertEquals(sensor.getLocation(), sensorDto.getLocation());
        assertEquals(sensor.getDescription(), sensorDto.getDescription());
        assertEquals(0, sensorDto.getRange().getFrom());
        assertEquals(100, sensorDto.getRange().getTo());
    }

    @Test
    public void testUpdateEntityFromDto() {
        Sensor sensor = new Sensor();
        SensorDto sensorDto = new SensorDto();
        sensorDto.setName("Обновленный сенсор");
        sensorDto.setModel("РР-50");
        sensorDto.setType("HUMIDITY");
        sensorDto.setUnit("PERCENT");
        sensorDto.setLocation("Ванная");
        sensorDto.setDescription("Сенсор влажности");
        sensorDto.setRange(new RangeDto(10, 90));

        sensorMapper.updateEntityFromDto(sensorDto, sensor);

        assertEquals(sensorDto.getName(), sensor.getName());
        assertEquals(sensorDto.getModel(), sensor.getModel());
        assertEquals(Type.HUMIDITY, sensor.getType());
        assertEquals(Unit.PERCENT, sensor.getUnit());
        assertEquals(sensorDto.getLocation(), sensor.getLocation());
        assertEquals(sensorDto.getDescription(), sensor.getDescription());
        assertEquals(10, sensor.getRange().getRangeFrom());
        assertEquals(90, sensor.getRange().getRangeTo());
    }
}