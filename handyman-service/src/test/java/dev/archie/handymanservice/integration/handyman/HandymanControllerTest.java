package dev.archie.handymanservice.integration.handyman;

import dev.archie.handymanservice.handyman.Handyman;
import dev.archie.handymanservice.handyman.Profile;
import dev.archie.handymanservice.handyman.dto.CreatingHandymanDto;
import dev.archie.handymanservice.integration.common.AbstractIntegrationTest;
import dev.archie.handymanservice.landscape.LandscapeService;
import dev.archie.handymanservice.landscape.User;
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
import java.util.List;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class HandymanControllerTest extends AbstractIntegrationTest {

    @MockBean
    private LandscapeService landscapeService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Mockito.when(landscapeService.createUser(Mockito.any())).thenReturn(HandymanConstants.MOCK_RESPONSE_USER);
        Mockito.when(landscapeService.getById(Mockito.any())).thenReturn(HandymanConstants.MOCK_RESPONSE_USER);
        Mockito.when(landscapeService.update(Mockito.any(), Mockito.any())).thenReturn(HandymanConstants.MOCK_RESPONSE_USER);
    }

    @Test
    @Order(1)
    void postHandymenShouldCreateNewHandyman() throws Exception {
        String creatingProfileDtoSerialized = objectMapper.writeValueAsString(HandymanConstants.CREATING_PROFILE_DTO);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.post("/handymen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile actualProfile = objectMapper.readValue(responseSerialized, Profile.class);
        User rancher = actualProfile.getUser();
        Handyman handyman = actualProfile.getHandyman();
        Assertions.assertEquals(HandymanConstants.MOCK_RESPONSE_USER, rancher);
        Assertions.assertEquals(HandymanConstants.MOCK_RESPONSE_USER.getLongitude(), handyman.getLongitude());
        Assertions.assertEquals(HandymanConstants.MOCK_RESPONSE_USER.getLatitude(), handyman.getLatitude());
        Assertions.assertEquals(HandymanConstants.CREATING_PROFILE_DTO.getSkills(), handyman.getSkills());
    }

    @Test
    @Order(2)
    void getHandymenShouldReturnExistingUser() throws Exception {
        String creatingProfileDtoSerialized = objectMapper.writeValueAsString(HandymanConstants.CREATING_PROFILE_DTO);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.post("/handymen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile existingUser = objectMapper.readValue(responseSerialized, Profile.class);
        Handyman existingHandyman = existingUser.getHandyman();


        String responseHandymanSerialized = mockMvc.perform(MockMvcRequestBuilders.get("/handymen/" + existingHandyman.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile handymanSerialized = objectMapper.readValue(responseHandymanSerialized, Profile.class);
        Handyman actualHandyman = handymanSerialized.getHandyman();
        Assertions.assertEquals(existingHandyman, actualHandyman);
    }

    @Test
    @Order(3)
    void putHandymenShouldUpdateUser() throws Exception {
        String creatingProfileDtoSerialized = objectMapper.writeValueAsString(HandymanConstants.CREATING_PROFILE_DTO);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.post("/handymen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile existingUser = objectMapper.readValue(responseSerialized, Profile.class);
        Handyman existingHandyman = existingUser.getHandyman();

        CreatingHandymanDto updatingUserDto = HandymanConstants.CREATING_PROFILE_DTO.toBuilder().build();
        updatingUserDto.setSkills(List.of("skill3"));
        String serializedUpdatingUserDto = objectMapper.writeValueAsString(updatingUserDto);

        String updatedUserSerialized = mockMvc.perform(MockMvcRequestBuilders.put("/handymen/" + existingHandyman.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedUpdatingUserDto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        Profile updatedProfile = objectMapper.readValue(updatedUserSerialized, Profile.class);
        Handyman updatedHandyman = updatedProfile.getHandyman();

        Assertions.assertEquals(updatingUserDto.getSkills(), updatedHandyman.getSkills());
        Assertions.assertEquals(existingHandyman.getInnerId(), updatedHandyman.getInnerId());
        Assertions.assertEquals(existingHandyman.getLongitude(), updatedHandyman.getLongitude());
        Assertions.assertEquals(existingHandyman.getLatitude(), updatedHandyman.getLatitude());
        Assertions.assertEquals(existingHandyman.getId(), updatedHandyman.getId());
    }

    @Test
    @Order(4)
    void deleteHandymenShouldDeleteExistingUser() throws Exception {
        String creatingProfileDtoSerialized = objectMapper.writeValueAsString(HandymanConstants.CREATING_PROFILE_DTO);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.post("/handymen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creatingProfileDtoSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Profile existingUser = objectMapper.readValue(responseSerialized, Profile.class);
        Handyman existingHandyman = existingUser.getHandyman();

        mockMvc.perform(MockMvcRequestBuilders.delete("/handymen/" + existingHandyman.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(landscapeService, Mockito.times(1)).delete(Mockito.any());
    }


}
