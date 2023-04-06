package dev.archie.landscapeservice.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.archie.landscapeservice.integration.common.AbstractIntegrationTest;
import dev.archie.landscapeservice.user.User;
import dev.archie.landscapeservice.user.dto.CreatingUserDto;
import dev.archie.landscapeservice.user.type.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void getUserTypesShouldReturnUserTypes() throws Exception {
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.get("/users/types"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        List<UserType> actual = objectMapper.readValue(responseSerialized, new TypeReference<>() {
        });
        Assertions.assertEquals(UserConstants.USER_TYPES, actual);
    }

    @ParameterizedTest
    @MethodSource("mockUsersInDB")
    @Order(2)
    void getUserShouldReturnUserFromDb(User expectedUser) throws Exception {
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.get("/users/" + expectedUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        User actualUser = objectMapper.readValue(responseSerialized, User.class);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    public static Stream<Arguments> mockUsersInDB() {
        return Stream.of(
                Arguments.of(UserConstants.FIRST_MOCK_USER_IN_DB),
                Arguments.of(UserConstants.SECOND_MOCK_USER_IN_DB)
        );
    }

    @Test
    @Order(3)
    void postUserShouldSaveNewUser() throws Exception {
        String serializedDtoToSave = objectMapper.writeValueAsString(UserConstants.USER_DTO_TO_SAVE);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedDtoToSave))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        User actualUser = objectMapper.readValue(responseSerialized, User.class);
        Assertions.assertEquals(actualUser.getUserType(), UserConstants.RANCHER);
        Assertions.assertEquals(actualUser.getLogin(), UserConstants.USER_DTO_TO_SAVE.getLogin());
        Assertions.assertEquals(actualUser.getEmail(), UserConstants.USER_DTO_TO_SAVE.getEmail());
        Assertions.assertEquals(actualUser.getPhoneNumber(), UserConstants.USER_DTO_TO_SAVE.getPhoneNumber());
    }

    @Test
    @Order(4)
    void postUserShouldNotCreateUserWithExistsEmail() throws Exception {
        String serializedDtoToSave = objectMapper.writeValueAsString(UserConstants.USER_DTO_WITH_EXISTING_EMAIL);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedDtoToSave))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Thread.sleep(2000);
    }

    @Test
    @Order(5)
    void putUserShouldNotUpdateUserFieldsWithExistingEmail() throws Exception {
        User currentUserInDB = UserConstants.FIRST_MOCK_USER_IN_DB;
        CreatingUserDto updatedUserDto = UserConstants.USER_DTO_TO_SAVE;
        updatedUserDto.setEmail(UserConstants.SECOND_MOCK_USER_IN_DB.getEmail());
        String updatedUserSerialized = objectMapper.writeValueAsString(updatedUserDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/" + currentUserInDB.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserSerialized))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(6)
    void putUserShouldUpdateUserFields() throws Exception {
        User currentUserInDB = UserConstants.SECOND_MOCK_USER_IN_DB;
        CreatingUserDto updatedUserDto = UserConstants.USER_DTO_TO_SAVE;
        updatedUserDto.setEmail("user2@mail.ru");
        String updatedUserSerialized = objectMapper.writeValueAsString(updatedUserDto);
        String responseSerialized = mockMvc.perform(MockMvcRequestBuilders.put("/users/" + currentUserInDB.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserSerialized))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        User actualUpdatedUser = objectMapper.readValue(responseSerialized, User.class);
        Assertions.assertEquals(currentUserInDB.getId(), actualUpdatedUser.getId());
        Assertions.assertEquals(updatedUserDto.getPhoneNumber(), actualUpdatedUser.getPhoneNumber());
        Assertions.assertEquals(updatedUserDto.getLogin(), actualUpdatedUser.getLogin());
        Assertions.assertEquals(updatedUserDto.getEmail(), actualUpdatedUser.getEmail());
        Assertions.assertEquals(updatedUserDto.getUserTypeId(), actualUpdatedUser.getUserType().getId());
    }

    @Test
    @Order(7)
    void deleteUserShouldDeleteExistingUserFromDb() throws Exception {
        User currentUserInDB = UserConstants.FIRST_MOCK_USER_IN_DB;
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + currentUserInDB.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + currentUserInDB.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(8)
    void deleteUserShouldNotDeleteNonExistingUserFromDb() throws Exception {
        User currentUserInDB = UserConstants.FIRST_MOCK_USER_IN_DB.toBuilder().build();
        currentUserInDB.setId(UUID.fromString("ab5b3831-ef5a-45e1-9fbe-0f3e9bbff7f1"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + currentUserInDB.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
