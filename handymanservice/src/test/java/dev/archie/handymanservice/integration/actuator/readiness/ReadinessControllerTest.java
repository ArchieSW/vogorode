package dev.archie.handymanservice.integration.actuator.readiness;

import static dev.archie.handymanservice.integration.common.ServiceConstants.SERVICE_NAME;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.archie.handymanservice.integration.common.AbstractIntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
class ReadinessControllerTest extends AbstractIntegrationTest {


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

        Map<String, String> expected = Map.of(SERVICE_NAME, "OK");
        assertEquals(expected, response);
    }
}