package dev.archie.landscapeservice.host;

import dev.archie.landscapeservice.host.responses.HostStatusResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host")
@RequiredArgsConstructor
public class HostController {

    private final StatusService statusService;

    /**
     * Used to get build information about all grpc clients
     *
     * @return Map with client name as key and build info as value
     */
    @GetMapping("/statuses")
    public Map<String, HostStatusResponse> getStatuses() {
        return statusService.getStatuses();
    }

}
