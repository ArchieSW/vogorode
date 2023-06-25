package dev.archie.rancherservice.landscape;

import dev.archie.rancherservice.landscape.dto.CreatingUserDto;
import dev.archie.rancherservice.rancher.exception.UnableToConnectToInnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LandscapeService {

    private final LandscapeClient landscapeClient;

    public User createUser(CreatingUserDto creatingUserDto) {
        try {
            return landscapeClient.create(creatingUserDto);
        } catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }

    public User getById(UUID id) {
        return landscapeClient.getById(id);
    }


    public User update(UUID id, CreatingUserDto creatingUserDto) {
        try {
            return landscapeClient.update(id, creatingUserDto);
        } catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }

    public void delete(UUID id) {
        try {
            landscapeClient.delete(id);
        } catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }

}
