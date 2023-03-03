package dev.archie.handymanservice.system;

import com.google.protobuf.Empty;
import dev.archie.handymanservice.ReadinessResponse;
import dev.archie.handymanservice.StatusServiceGrpc.StatusServiceImplBase;
import dev.archie.handymanservice.VersionResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.info.BuildProperties;

@GrpcService
@RequiredArgsConstructor
public class StatusService extends StatusServiceImplBase {

    private final SystemService systemService;
    private final BuildProperties buildProperties;

    /**
     * <b>getVersion</b> used to get service build information
     *
     * @param request          empty request
     * @param responseObserver grpc stream observer
     */
    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {
        VersionResponse response = VersionResponse.newBuilder()
            .setVersion(buildProperties.getVersion())
            .setArtifact(buildProperties.getArtifact())
            .setGroup(buildProperties.getGroup())
            .setName(buildProperties.getName())
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * <b>getReadiness</b> used to get service version information
     *
     * @param request          empty request
     * @param responseObserver grpc stream observer
     */
    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        String status = systemService.getReadiness().getValue();
        ReadinessResponse ok = ReadinessResponse
            .newBuilder()
            .setStatus(status)
            .build();
        responseObserver.onNext(ok);
        responseObserver.onCompleted();
    }
}
