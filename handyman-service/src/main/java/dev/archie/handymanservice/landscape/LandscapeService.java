package dev.archie.handymanservice.landscape;

import dev.archie.handymanservice.handyman.exception.UnableToConnectToInnerService;
import dev.archie.handymanservice.landscape.dto.CreatingUserDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LandscapeService {

    private final UserClient userClient;

    public User createUser(CreatingUserDto creatingUserDto) {
        try {
            return userClient.create(creatingUserDto);
        } catch (FeignException.FeignClientException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        }
        catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }


    public User update(UUID id, CreatingUserDto creatingUserDto) {
        try {
            return userClient.update(id, creatingUserDto);
        } catch (FeignException.FeignClientException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        }catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }

    public void delete(UUID id) {
        try {
            userClient.delete(id);
        }catch (FeignException.FeignClientException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        } catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }

    public User getById(UUID id) {
        try {
            return userClient.getById(id);
        }catch (FeignException.FeignClientException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        } catch (Exception e) {
            throw new UnableToConnectToInnerService();
        }
    }
}
