package dev.archie.landscapeservice.host;

import com.google.protobuf.Empty;
import dev.archie.landscapeservice.ReadinessResponse;
import dev.archie.landscapeservice.StatusServiceGrpc.StatusServiceBlockingStub;
import dev.archie.landscapeservice.VersionResponse;
import dev.archie.landscapeservice.host.responses.HostStatusResponse;
import java.util.Map;
import lombok.Setter;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@Setter
public class StatusService {

    private static final String HANDYMAN_SERVICE_NAME = "handyman-service";
    private static final String RANCHER_SERVICE_NAME = "rancher-service";

    @GrpcClient(HANDYMAN_SERVICE_NAME)
    private StatusServiceBlockingStub handymanServiceStub;
    @GrpcClient(RANCHER_SERVICE_NAME)
    private StatusServiceBlockingStub rancherServiceStub;

    /**
     * <b>getStatuses</b> used to get build information about all grpc clients
     *
     * @return Map with client name as key and build info as value
     */
    public Map<String, HostStatusResponse> getStatuses() {
        HostStatusResponse handymanStatus = getHostStatus(HANDYMAN_SERVICE_NAME, handymanServiceStub);
        HostStatusResponse rancherStatus = getHostStatus(RANCHER_SERVICE_NAME, rancherServiceStub);
        return Map.of(
            HANDYMAN_SERVICE_NAME, handymanStatus,
            RANCHER_SERVICE_NAME, rancherStatus
        );
    }

    private HostStatusResponse getHostStatus(String serviceName, StatusServiceBlockingStub service) {
        VersionResponse buildInfo = service.getVersion(Empty.getDefaultInstance());
        ReadinessResponse readiness = service.getReadiness(Empty.getDefaultInstance());
        return HostStatusResponse.builder()
            .version(buildInfo.getVersion())
            .status(readiness.getStatus())
            .group(buildInfo.getGroup())
            .artifact(buildInfo.getArtifact())
            .host(service.getChannel().authority())
            .name(serviceName)
            .build();
    }
}
