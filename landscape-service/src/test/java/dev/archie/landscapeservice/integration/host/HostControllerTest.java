package dev.archie.landscapeservice.integration.host;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.protobuf.Empty;
import dev.archie.landscapeservice.StatusServiceGrpc.StatusServiceBlockingStub;
import dev.archie.landscapeservice.host.StatusService;
import dev.archie.landscapeservice.host.responses.HostStatusResponse;
import dev.archie.landscapeservice.integration.common.AbstractIntegrationTest;
import dev.archie.landscapeservice.integration.common.TestConstants;
import io.grpc.Channel;
import java.util.List;
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
            TestConstants.HANDYMAN_VERSION);
        Mockito.when(handymanMock.getReadiness(Empty.getDefaultInstance()))
            .thenReturn(TestConstants.HANDYMAN_READINESS);
        Mockito.when(handymanMock.getChannel())
            .thenReturn(channelMockHandyman);
        Mockito.when(handymanMock.getChannel().authority())
            .thenReturn(TestConstants.HANDYMAN_HOST);

        Mockito.when(rancherMock.getVersion(Empty.getDefaultInstance()))
            .thenReturn(TestConstants.RANCHER_VERSION);
        Mockito.when(rancherMock.getReadiness(Empty.getDefaultInstance()))
            .thenReturn(TestConstants.RANCHER_READINESS);
        Mockito.when(rancherMock.getChannel())
            .thenReturn(channelMockRancher);
        Mockito.when(rancherMock.getChannel().authority())
            .thenReturn(TestConstants.RANCHER_HOST);

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

        Map<String, List<HostStatusResponse>> expected = Map.of(
            TestConstants.HANDYMAN_SERVICE_NAME,
            List.of(TestConstants.HANDYMAN_HOST_STATUS),
            TestConstants.RANCHER_SERVICE_NAME,
            List.of(TestConstants.RANCHER_HOST_STATUS)
        );

        Map<String, List<HostStatusResponse>> actual = objectMapper.readValue(responseJson,
            new TypeReference<>() {
            });

        Assertions.assertEquals(expected, actual);
    }

}
