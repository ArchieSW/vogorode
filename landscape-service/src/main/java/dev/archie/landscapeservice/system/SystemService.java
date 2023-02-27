package dev.archie.landscapeservice.system;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

    @Value("${spring.application.name}")
    private String serviceName;

    /**
     * Returns whether the service is ready to accept requests.
     *
     * @return { <b><i>SERVICE_NAME</i></b> : "OK" }
     */
    public Map<String, String> getReadiness() {
        return Map.of(serviceName, "OK");
    }

}
