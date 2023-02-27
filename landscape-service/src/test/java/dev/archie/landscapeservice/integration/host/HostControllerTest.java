package dev.archie.landscapeservice.integration.host;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.protobuf.Empty;
import dev.archie.landscapeservice.StatusServiceGrpc.StatusServiceBlockingStub;
import dev.archie.landscapeservice.host.StatusService;
import dev.archie.landscapeservice.host.dto.HostStatusDto;
import dev.archie.landscapeservice.integration.common.AbstractIntegrationTest;
import dev.archie.landscapeservice.integration.common.ServiceConstants;
import io.grpc.Channel;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
public class HostControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StatusService statusService;

    @BeforeEach
    void setUp() {
        StatusServiceBlockingStub handymanMock = Mockito.mock(StatusServiceBlockingStub.class);
        StatusServiceBlockingStub rancherMock = Mockito.mock(StatusServiceBlockingStub.class);
        Channel channelMockHandyman = Mockito.mock(Channel.class);
        Channel channelMockRancher = Mockito.mock(Channel.class);

        Mockito.when(handymanMock.getVersion(Empty.getDefaultInstance())).thenReturn(
            ServiceConstants.HANDYMAN_VERSION);
        Mockito.when(handymanMock.getReadiness(Empty.getDefaultInstance()))
            .thenReturn(ServiceConstants.HANDYMAN_READINESS);
        Mockito.when(handymanMock.getChannel())
            .thenReturn(channelMockHandyman);
        Mockito.when(handymanMock.getChannel().authority())
            .thenReturn(ServiceConstants.HANDYMAN_HOST);

        Mockito.when(rancherMock.getVersion(Empty.getDefaultInstance()))
            .thenReturn(ServiceConstants.RANCHER_VERSION);
        Mockito.when(rancherMock.getReadiness(Empty.getDefaultInstance()))
            .thenReturn(ServiceConstants.RANCHER_READINESS);
        Mockito.when(rancherMock.getChannel())
            .thenReturn(channelMockRancher);
        Mockito.when(rancherMock.getChannel().authority())
            .thenReturn(ServiceConstants.RANCHER_HOST);

        statusService.setHandymanServiceStub(handymanMock);
        statusService.setRancherServiceStub(rancherMock);
    }

    @Test
    void getStatusesShouldReturnRightStatuses() throws Exception {
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/host/statuses"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Map<String, HostStatusDto> expected = Map.of(
            ServiceConstants.HANDYMAN_SERVICE_NAME, ServiceConstants.HANDYMAN_HOST_STATUS,
            ServiceConstants.RANCHER_SERVICE_NAME, ServiceConstants.RANCHER_HOST_STATUS
        );

        Map<String, HostStatusDto> actual = objectMapper.readValue(responseJson,
            new TypeReference<>() {
            });

        Assertions.assertEquals(expected, actual);
    }

}
