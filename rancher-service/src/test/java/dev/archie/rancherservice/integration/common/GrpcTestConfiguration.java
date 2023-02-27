package dev.archie.rancherservice.integration.common;

import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({
    GrpcServerAutoConfiguration.class,
    GrpcServerFactoryAutoConfiguration.class,
    GrpcClientAutoConfiguration.class
})
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
