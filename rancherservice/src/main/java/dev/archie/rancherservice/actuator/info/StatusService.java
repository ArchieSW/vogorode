package dev.archie.rancherservice.actuator.info;

import com.google.protobuf.Empty;
import dev.archie.rancherservice.ReadinessResponse;
import dev.archie.rancherservice.StatusServiceGrpc.StatusServiceImplBase;
import dev.archie.rancherservice.VersionResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;

@GrpcService
public class StatusService extends StatusServiceImplBase {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {
        BuildProperties bean = applicationContext.getBean(BuildProperties.class);
        VersionResponse response = VersionResponse.newBuilder()
            .setVersion(bean.getVersion())
            .setArtifact(bean.getArtifact())
            .setGroup(bean.getGroup())
            .setName(bean.getName())
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        ReadinessResponse ok = ReadinessResponse.newBuilder().setStatus("OK").build();
        responseObserver.onNext(ok);
        responseObserver.onCompleted();
    }
}
