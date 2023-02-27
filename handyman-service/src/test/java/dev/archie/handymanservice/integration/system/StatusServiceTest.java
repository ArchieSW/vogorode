package dev.archie.handymanservice.integration.system;

import com.google.protobuf.Empty;
import dev.archie.handymanservice.ReadinessResponse;
import dev.archie.handymanservice.StatusServiceGrpc.StatusServiceBlockingStub;
import dev.archie.handymanservice.VersionResponse;
import dev.archie.handymanservice.integration.common.GrpcAbstractIntegrationTest;
import dev.archie.handymanservice.integration.common.TestConstants;
import java.util.Arrays;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

public class StatusServiceTest extends GrpcAbstractIntegrationTest {

    @GrpcClient("inProcess")
    private StatusServiceBlockingStub statusServiceStub;

    @Test
    @DirtiesContext
    void getVersionShouldReturnVersionWithNonNullProperties() {
        VersionResponse actual = statusServiceStub.getVersion(Empty.getDefaultInstance());
        actual.getAllFields().forEach((key, value) -> Assertions.assertNotNull(value));
    }

    @Test
    @DirtiesContext
    void getReadinessShouldReturnOneOfReadinessStatuses() {
        ReadinessResponse actual = statusServiceStub.getReadiness(Empty.getDefaultInstance());
        Assertions.assertTrue(
            Arrays.asList(TestConstants.READINESS_VALUES).contains(actual.getStatus()));
    }

}
