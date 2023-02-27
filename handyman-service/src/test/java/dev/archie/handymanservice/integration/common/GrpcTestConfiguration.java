package dev.archie.handymanservice.integration.common;

import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({
    GrpcServerAutoConfiguration.class, // Create required server beans
    GrpcServerFactoryAutoConfiguration.class,
    GrpcClientAutoConfiguration.class
}) // Select server implementation
public class GrpcTestConfiguration {

}
