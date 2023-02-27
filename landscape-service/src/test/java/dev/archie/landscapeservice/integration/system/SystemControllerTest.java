package dev.archie.landscapeservice.integration.system;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.archie.landscapeservice.integration.common.AbstractIntegrationTest;
import dev.archie.landscapeservice.integration.common.TestConstants;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
public class SystemControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getReadinessShouldReturnServiceNameAndOk() throws Exception {
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.get("/system/readiness"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8);

        HashMap<String, String> response = objectMapper.readValue(responseSerialized,
            new TypeReference<>() {
            });

        Map<String, String> expected = Map.of(TestConstants.SERVICE_NAME, "OK");
        Assertions.assertEquals(expected, response);
    }


    @Test
    void getLivenessShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/system/liveness"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getInfoShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/info"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
