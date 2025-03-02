package org.example.pavlyuchkovtz.config;

import org.example.pavlyuchkovtz.controller.SensorController;
import org.example.pavlyuchkovtz.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private SensorController sensorController;

    @BeforeEach
    public void setup() {
        initMocks(this);
        given(sensorService.getAllSensors()).willReturn(List.of());
    }

    @Test
    public void testAccessWithAdminUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/sensors")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAccessWithViewerUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/sensors")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("viewer", "viewer")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAccessWithoutAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/sensors"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void testAccessWithWrongCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/sensors")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "wrongpassword")))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}