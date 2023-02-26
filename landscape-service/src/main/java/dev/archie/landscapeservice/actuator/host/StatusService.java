package dev.archie.landscapeservice.actuator.host;

import com.google.protobuf.Empty;
import dev.archie.landscapeservice.StatusServiceGrpc.StatusServiceBlockingStub;
import dev.archie.landscapeservice.VersionResponse;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class StatusService {

    private static final String HANDYMAN_SERVICE_NAME = "handymanservice";
    private static final String RANCHER_SERVICE_NAME = "rancherservice";

    @GrpcClient("handymanservice")
    private StatusServiceBlockingStub handymanServiceStub;

    @GrpcClient("rancherservice")
    private StatusServiceBlockingStub rancherServiceStub;

    public Map<String, VersionResponse> getStatuses() {
        return Map.of(
            HANDYMAN_SERVICE_NAME, handymanServiceStub.getVersion(Empty.getDefaultInstance()),
            RANCHER_SERVICE_NAME, rancherServiceStub.getVersion(Empty.getDefaultInstance())
        );
    }
}
