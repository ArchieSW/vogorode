package dev.archie.rancherservice.integration.actuator.liveness;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.archie.rancherservice.integration.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
class LivenessControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getLivenessShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/system/liveness"))
            .andExpect(status().isOk());
    }
}