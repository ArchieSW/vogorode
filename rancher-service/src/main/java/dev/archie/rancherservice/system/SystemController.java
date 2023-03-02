package dev.archie.rancherservice.system;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {

    private final SystemService systemService;

    /**
     * HttpStatus.ok() if service alive
     */
    @GetMapping("/liveness")
    public void getLiveness() {
    }

    /**
     * Returns whether the service is ready to accept requests.
     *
     * @return { <b><i>SERVICE_NAME</i></b> : "OK" }
     */
    @GetMapping("/readiness")
    public Map<String, String> getReadiness() {
        return systemService.getReadiness();
    }

}
