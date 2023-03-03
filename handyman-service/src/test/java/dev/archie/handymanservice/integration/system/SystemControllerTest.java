package dev.archie.handymanservice.integration.system;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.archie.handymanservice.integration.common.AbstractIntegrationTest;
import dev.archie.handymanservice.integration.common.TestConstants;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
public class SystemControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getReadinessShouldReturnOneOfReadinessStatuses() throws Exception {
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.get("/system/readiness"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8);

        Map<String, String> response = objectMapper.readValue(responseSerialized,
            new TypeReference<>() {
            });

        String readiness = response.get(TestConstants.SERVICE_NAME);
        Assertions.assertTrue(
            Arrays.asList(TestConstants.READINESS_VALUES).contains(readiness));
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

    @Test
    void setMalfunctionShouldSetStatusToMalfunctionIfTrue() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/system/forceMalfunction").param("shouldSet", "true"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());

        String responseSerialized = mockMvc.perform(
                MockMvcRequestBuilders.get("/system/readiness"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8);

        Map<String, String> actual = objectMapper.readValue(responseSerialized,
            new TypeReference<>() {
            });
        Map<String, String> expected = Map.of(TestConstants.SERVICE_NAME, "MALFUNCTION");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void setMalfunctionShouldNOTSetStatusToMalfunctionIfFalse() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/system/forceMalfunction").param("shouldSet", "false"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());

        String responseSerialized = mockMvc.perform(
                MockMvcRequestBuilders.get("/system/readiness"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8);

        Map<String, String> actual = objectMapper.readValue(responseSerialized,
            new TypeReference<>() {
            });

        Assertions.assertNotEquals("MALFUNCTION", actual.get(TestConstants.SERVICE_NAME));
    }
}
