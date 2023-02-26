package dev.archie.handymanservice.integration.actuator.info;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.archie.handymanservice.integration.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
public class InfoTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getInfoShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/info"))
            .andExpect(status().isOk());
    }

}
