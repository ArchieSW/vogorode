package dev.archie.landscapeservice.actuator.liveness;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Used to respond to a service liveness response
 */
@RestController
@RequestMapping("/system/liveness")
@RequiredArgsConstructor
public class LivenessController {

    /**
     * @return HttpStatus.ok() if service alive
     */
    @GetMapping
    public ResponseEntity<?> getLiveness() {
        return ResponseEntity.ok().build();
    }
}
