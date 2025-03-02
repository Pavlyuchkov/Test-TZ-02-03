package org.example.pavlyuchkovtz.mapper;

import org.example.pavlyuchkovtz.dto.SensorDto;
import org.example.pavlyuchkovtz.entity.Sensor;
import org.example.pavlyuchkovtz.enums.Type;
import org.example.pavlyuchkovtz.enums.Unit;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SensorMapper {

    @Mapping(source = "range.from", target = "range.rangeFrom")
    @Mapping(source = "range.to", target = "range.rangeTo")
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToSensorType")
    @Mapping(source = "unit", target = "unit", qualifiedByName = "stringToSensorUnit")
    Sensor toEntity(SensorDto sensorDto);

    @Mapping(source = "range.rangeFrom", target = "range.from")
    @Mapping(source = "range.rangeTo", target = "range.to")
    SensorDto toDto(Sensor sensor);

    @Mapping(source = "range.from", target = "range.rangeFrom")
    @Mapping(source = "range.to", target = "range.rangeTo")
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToSensorType")
    @Mapping(source = "unit", target = "unit", qualifiedByName = "stringToSensorUnit")
    void updateEntityFromDto(SensorDto sensorDto, @MappingTarget Sensor sensor);

    @Named("stringToSensorType")
    default Type stringToSensorType(String type) {
        return type != null ? Type.valueOf(type.toUpperCase()) : null;
    }

    @Named("stringToSensorUnit")
    default Unit stringToSensorUnit(String unit) {
        return unit != null ? Unit.valueOf(unit.toUpperCase()) : null;
    }
}
