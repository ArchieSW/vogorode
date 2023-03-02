package dev.archie.rancherservice.system;

import com.google.protobuf.Empty;
import dev.archie.rancherservice.ReadinessResponse;
import dev.archie.rancherservice.StatusServiceGrpc.StatusServiceImplBase;
import dev.archie.rancherservice.VersionResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.info.BuildProperties;

@GrpcService
@RequiredArgsConstructor
public class StatusService extends StatusServiceImplBase {

    private final BuildProperties buildProperties;
    private final SystemService systemService;

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
        ReadinessResponse ok = ReadinessResponse.newBuilder()
            .setStatus(systemService.getReadiness().getValue())
            .build();
        responseObserver.onNext(ok);
        responseObserver.onCompleted();
    }
}
