package dev.archie.landscapeservice.actuator.host;

import static dev.archie.landscapeservice.utils.grpc.ProtobufSerialization.toJson;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host")
@RequiredArgsConstructor
public class HostController {

    private final StatusService statusService;

    @GetMapping("/statuses")
    public Map<String, JsonNode> getStatuses() {
        return statusService.getStatuses().entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getKey, entry -> toJson(entry.getValue())));
    }

}