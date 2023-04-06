package dev.archie.rancherservice.integration.rancher;

import dev.archie.rancherservice.integration.common.AbstractIntegrationTest;
import dev.archie.rancherservice.landscape.LandscapeService;
import dev.archie.rancherservice.landscape.User;
import dev.archie.rancherservice.rancher.Plot;
import dev.archie.rancherservice.rancher.Profile;
import dev.archie.rancherservice.rancher.dto.CreatingProfileDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class RancherControllerTest extends AbstractIntegrationTest {


    @MockBean
    private LandscapeService landscapeService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Mockito.when(landscapeService.createUser(Mockito.any())).thenReturn(RancherConstants.MOCK_RESPONSE_USER);
        Mockito.when(landscapeService.getById(Mockito.any())).thenReturn(RancherConstants.MOCK_RESPONSE_USER);
        Mockito.when(landscapeService.update(Mockito.any(), Mockito.any())).thenReturn(RancherConstants.MOCK_RESPONSE_USER);
    }

    @Test
    @Order(1)
    void postRanchersShouldCreateNewUser() throws Exception {
        String creatingProfileDtoSerialized = objectMapper.writeValueAsString(RancherConstants.CREATING_PROFILE_DTO);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.post("/ranchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile actualProfile = objectMapper.readValue(responseSerialized, Profile.class);
        User rancher = actualProfile.getRancher();
        Plot plot = actualProfile.getPlot();
        Assertions.assertEquals(RancherConstants.MOCK_RESPONSE_USER.getLogin(), rancher.getLogin());
        Assertions.assertEquals(RancherConstants.MOCK_RESPONSE_USER.getEmail(), rancher.getEmail());
        Assertions.assertEquals(RancherConstants.MOCK_RESPONSE_USER.getPhoneNumber(), rancher.getPhoneNumber());
        Assertions.assertEquals(RancherConstants.MOCK_RESPONSE_USER.getLatitude(), rancher.getLatitude());
        Assertions.assertEquals(RancherConstants.MOCK_RESPONSE_USER.getLongitude(), rancher.getLongitude());
        Assertions.assertEquals(RancherConstants.CREATING_PROFILE_DTO.getArea(), plot.getArea());
        Assertions.assertEquals(RancherConstants.CREATING_PROFILE_DTO.getJobs(), plot.getJobs());
    }

    @Test
    @Order(2)
    void deleteRanchersShouldDeleteExistingRancher() throws Exception {
        String creatingProfileDtoSerialized = objectMapper.writeValueAsString(RancherConstants.CREATING_PROFILE_DTO);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.post("/ranchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile actualProfile = objectMapper.readValue(responseSerialized, Profile.class);
        Plot plot = actualProfile.getPlot();

        mockMvc.perform(MockMvcRequestBuilders.delete("/ranchers/" + plot.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Mockito.verify(landscapeService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @Order(3)
    void getRanchersShouldReturnExistingUser() throws Exception {
        String creatingProfileDtoSerialized = objectMapper.writeValueAsString(RancherConstants.CREATING_PROFILE_DTO);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.post("/ranchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile createdProfile = objectMapper.readValue(responseSerialized, Profile.class);
        Plot createdPlot = createdProfile.getPlot();

        String responseUserSerialized = mockMvc.perform(MockMvcRequestBuilders.get("/ranchers/" + createdPlot.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        Profile actualProfile = objectMapper.readValue(responseUserSerialized, Profile.class);
        Plot actualPlot = actualProfile.getPlot();
        Assertions.assertEquals(createdPlot, actualPlot);
    }

    @Test
    @Order(4)
    void putRanchersShouldUpdateExistingUser() throws Exception {
        String creatingProfileDtoSerialized = objectMapper.writeValueAsString(RancherConstants.CREATING_PROFILE_DTO);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.post("/ranchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile createdProfile = objectMapper.readValue(responseSerialized, Profile.class);
        Plot createdPlot = createdProfile.getPlot();

        CreatingProfileDto updatingProfileDto = RancherConstants.CREATING_PROFILE_DTO.toBuilder().build();
        updatingProfileDto.setArea(123.0);
        String updatingProfileDtoSerialized = objectMapper.writeValueAsString(updatingProfileDto);

        String updatedProfileSerialized = mockMvc.perform(MockMvcRequestBuilders.put("/ranchers/" + createdPlot.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile updatedProfile = objectMapper.readValue(updatedProfileSerialized, Profile.class);
        Plot updatedPlot = updatedProfile.getPlot();

        Assertions.assertEquals(updatingProfileDto.getArea(), updatedPlot.getArea());
        Assertions.assertEquals(createdPlot.getLongitude(), updatedPlot.getLongitude());
        Assertions.assertEquals(createdPlot.getLatitude(), updatedPlot.getLatitude());
        Assertions.assertEquals(createdPlot.getJobs(), updatedPlot.getJobs());
        Assertions.assertEquals(createdPlot.getId(), updatedPlot.getId());
        Assertions.assertEquals(createdPlot.getInnerId(), updatedPlot.getInnerId());
    }

}
