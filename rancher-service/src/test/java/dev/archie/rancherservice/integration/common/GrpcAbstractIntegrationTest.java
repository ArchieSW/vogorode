package dev.archie.rancherservice.integration.common;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(properties = {
    "grpc.server.inProcessName=test",
    "grpc.server.port=-1",
    "grpc.client.inProcess.address=in-process:test"
})
@DirtiesContext
public class GrpcAbstractIntegrationTest {

}
