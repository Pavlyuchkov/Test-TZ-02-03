package org.example.pavlyuchkovtz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pavlyuchkovtz.dto.SensorDto;
import org.example.pavlyuchkovtz.dto.RangeDto;
import org.example.pavlyuchkovtz.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SensorControllerTest {

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private SensorController sensorController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sensorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllSensors() throws Exception {
        List<SensorDto> sensorDtos = Arrays.asList(
                new SensorDto(1L, "Первый сенсор", "Model X", new RangeDto(1, 10), "TEMPERATURE", "CELSIUS", "kitchen", "Первое описание"),
                new SensorDto(2L, "Второй сенсор", "Model Y", new RangeDto(5, 15), "PRESSURE", "BAR", "room", "Второе описание")
        );
        when(sensorService.getAllSensors()).thenReturn(sensorDtos);
        
        mockMvc.perform(get("/api/sensors")
                        .header("Authorization", "какой-то токен"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Первый сенсор"))
                .andExpect(jsonPath("$[1].name").value("Второй сенсор"))
                .andReturn();
    }

    @Test
    public void testGetSensorById() throws Exception {
        SensorDto sensorDto = new SensorDto(1L, "Первый сенсор", "Model X", new RangeDto(1, 10), "TEMPERATURE", "CELSIUS", "kitchen", "Первое описание");
        when(sensorService.getSensorById(1L)).thenReturn(sensorDto);
        
        mockMvc.perform(get("/api/sensors/{id}", 1L)
                        .header("Authorization", "Bearer some_token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Первый сенсор"))
                .andExpect(jsonPath("$.model").value("Model X"))
                .andExpect(jsonPath("$.type").value("TEMPERATURE"))
                .andExpect(jsonPath("$.unit").value("CELSIUS"));
    }

    @Test
    public void testSearchSensors() throws Exception {
        List<SensorDto> sensorDtos = Arrays.asList(
                new SensorDto(1L, "Первый сенсор", "Model X", new RangeDto(1, 10), "TEMPERATURE", "CELSIUS", "kitchen", "Первое описание"),
                new SensorDto(2L, "Второй сенсор", "Model Y", new RangeDto(5, 15), "PRESSURE", "BAR", "room", "Второе описание")
        );
        when(sensorService.searchSensors("Sensor", "Model")).thenReturn(sensorDtos);
        
        mockMvc.perform(get("/api/sensors/search")
                        .param("name", "Sensor")
                        .param("model", "Model")
                        .header("Authorization", "Bearer some_token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Первый сенсор"))
                .andExpect(jsonPath("$[1].name").value("Второй сенсор"));
    }

    @Test
    public void testCreateSensor() throws Exception {
        SensorDto sensorDto = new SensorDto(1L, "Первый сенсор", "Model X", new RangeDto(1, 10), "TEMPERATURE", "CELSIUS", "kitchen", "Первое описание");
        
        mockMvc.perform(post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorDto))
                        .header("Authorization", "Bearer some_token"))
                .andExpect(status().isCreated());
        
        verify(sensorService, times(1)).createSensor(sensorDto);
    }

    @Test
    public void testUpdateSensor() throws Exception {
        SensorDto sensorDto = new SensorDto(1L, "Первый сенсор", "Model X", new RangeDto(1, 10), "TEMPERATURE", "CELSIUS", "kitchen", "Первое описание");
        
        mockMvc.perform(put("/api/sensors/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorDto))
                        .header("Authorization", "Bearer some_token"))
                .andExpect(status().isOk());
        
        verify(sensorService, times(1)).updateSensor(1L, sensorDto);
    }

    @Test
    public void testDeleteSensor() throws Exception {
        mockMvc.perform(delete("/api/sensors/{id}", 1L)
                        .header("Authorization", "Bearer some_token"))
                .andExpect(status().isOk());
        
        verify(sensorService, times(1)).deleteSensor(1L);
    }
}
