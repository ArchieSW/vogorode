package dev.archie.rancherservice.system;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "[rancher-service][SystemService]")
public class SystemService {

    private static final String STATUS_MALFUNCTION = "MALFUNCTION";
    private final ManagedChannel channel;
    @Value("${spring.application.name}")
    private String serviceName;
    private String readinessStatus;

    public SystemService(GrpcServerProperties grpcProperties) {
        this.channel = ManagedChannelBuilder
            .forAddress(grpcProperties.getAddress(), grpcProperties.getPort())
            .usePlaintext()
            .build();
        readinessStatus = channel.getState(channel.isShutdown()).name();
    }

    /**
     * Returns whether the service is ready to accept requests.
     *
     * @return { <b><i>SERVICE_NAME</i></b> : <b>STATE</b> }
     */
    public Entry<String, String> getReadiness() {
        if (!readinessStatus.equals(STATUS_MALFUNCTION)) {
            readinessStatus = channel.getState(channel.isShutdown()).name();
        }
        return Map.entry(serviceName, readinessStatus);
    }

    /**
     * Set readiness status to "Malfunction" if parameter is true
     *
     * @param isMalfunction flag for case of malfunction
     */
    public void setReadinessStatus(Boolean isMalfunction) {
        if (isMalfunction) {
            readinessStatus = STATUS_MALFUNCTION;
            return;
        }
        readinessStatus = channel.getState(channel.isShutdown()).name();
        log.info("Readiness status was set manually");
    }

}
