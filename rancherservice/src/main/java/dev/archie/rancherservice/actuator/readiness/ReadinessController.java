package dev.archie.rancherservice.actuator.readiness;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ReadinessController - used to respond to a service readiness request
 */
@RestController
@RequestMapping("/system/readiness")
@RequiredArgsConstructor
public class ReadinessController {

    @Autowired
    private final ApplicationContext context;

    /**
     * Returns whether the service is ready to accept requests.
     *
     * @return { <b><i>SERVICE_NAME</i></b> : "OK" }
     */
    @GetMapping
    public Map<String, String> getReadiness() {
        BuildProperties buildProperties = context.getBean(BuildProperties.class);
        return Map.of(buildProperties.getName(), "OK");
    }

}
