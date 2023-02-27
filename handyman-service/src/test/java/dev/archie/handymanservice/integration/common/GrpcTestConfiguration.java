package dev.archie.handymanservice.integration.common;

import dev.archie.handymanservice.system.StatusService;
import dev.archie.handymanservice.system.SystemService;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;

@Configuration
@ImportAutoConfiguration({
    GrpcServerAutoConfiguration.class, // Create required server beans
    GrpcServerFactoryAutoConfiguration.class,
    GrpcClientAutoConfiguration.class
}) // Select server implementation
public class GrpcTestConfiguration {

//    @Bean
//    SystemService systemService() {
//        return new SystemService(new GrpcServerProperties());
//    }
//
//
//    @Bean
//    StatusService statusService(SystemService systemService, BuildProperties buildProperties) {
//        return new StatusService(systemService, buildProperties);
//    }
}
