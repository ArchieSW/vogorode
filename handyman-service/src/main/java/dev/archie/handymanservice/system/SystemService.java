package dev.archie.handymanservice.system;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Map;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

    private final ManagedChannel channel;
    @Value("${spring.application.name}")
    private String serviceName;

    public SystemService(GrpcServerProperties grpcProperties) {
        this.channel = ManagedChannelBuilder
            .forAddress(grpcProperties.getAddress(), grpcProperties.getPort())
            .usePlaintext()
            .build();
    }

    /**
     * Returns whether the service is ready to accept requests.
     *
     * @return { <b><i>SERVICE_NAME</i></b> : "OK" }
     */
    public Map<String, String> getReadiness() {
        String name = channel.getState(channel.isShutdown()).name();
        return Map.of(serviceName, name);
    }

}