package dev.archie.handymanservice.landscape;

import dev.archie.handymanservice.handyman.exception.UnableToConnectToInnerService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LandscapeService {

    private final LandscapeClient landscapeClient;

    public User createUser(CreatingUserDto creatingUserDto) {
        try {
            return landscapeClient.create(creatingUserDto);
        } catch (FeignException.FeignClientException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        }
        catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }


    public User update(UUID id, CreatingUserDto creatingUserDto) {
        try {
            return landscapeClient.update(id, creatingUserDto);
        } catch (FeignException.FeignClientException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        }catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }

    public void delete(UUID id) {
        try {
            landscapeClient.delete(id);
        }catch (FeignException.FeignClientException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        } catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }

    public User getById(UUID id) {
        try {
            return landscapeClient.getById(id);
        }catch (FeignException.FeignClientException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        } catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }
}
