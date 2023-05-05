package dev.archie.rancherservice.rancher;

import dev.archie.rancherservice.landscape.CreatingUserDto;
import dev.archie.rancherservice.landscape.LandscapeService;
import dev.archie.rancherservice.landscape.User;
import dev.archie.rancherservice.rancher.dto.CreatingProfileDto;
import dev.archie.rancherservice.rancher.exception.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RancherService {

    private static final long RANCHER_USER_TYPE = 1;

    private final LandscapeService landscapeService;

    private final RancherRepository rancherRepository;

    /**
     * @param creatingProfileDto rancher to create
     * @return created rancher's profile
     */
    public Profile create(CreatingProfileDto creatingProfileDto) {
        CreatingUserDto creatingUserDto = mapCreatingProfileDtoToUserOne(creatingProfileDto);
        User user = landscapeService.createUser(creatingUserDto);
        Plot plot = mapProfileToPlot(creatingProfileDto, user);
        rancherRepository.insert(plot);
        return new Profile(plot, user);
    }

    /**
     * @param id of existing rancher
     * @return found profile
     */
    public Profile getProfileById(String id) {
        Plot plot = rancherRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
        User rancher = landscapeService.getById(plot.getInnerId());
        return new Profile(plot, rancher);
    }

    /**
     * @param id of rancher's plot
     * @return found plot
     */
    public Plot getPlotById(String id) {
        return rancherRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
    }

    /**
     * @param id of existing rancher
     * @param creatingProfileDto new fields. New email should not exist
     * @return updated profile
     */
    public Profile update(String id, CreatingProfileDto creatingProfileDto) {
        Plot plot = getPlotById(id);
        CreatingUserDto creatingUserDto = mapCreatingProfileDtoToUserOne(creatingProfileDto);
        User user = landscapeService.update(plot.getInnerId(), creatingUserDto);
        Plot updatedPlot = mapProfileToPlot(creatingProfileDto, user);
        updatedPlot.setId(plot.getId());
        rancherRepository.save(updatedPlot);
        return new Profile(updatedPlot, user);
    }

    /**
     * @param id of existing user
     */
    public void delete(String id) {
        Plot plot = getPlotById(id);
        landscapeService.delete(plot.getInnerId());
        rancherRepository.delete(plot);
    }

    private static CreatingUserDto mapCreatingProfileDtoToUserOne(CreatingProfileDto creatingProfileDto) {
        return CreatingUserDto.builder()
                .userTypeId(RANCHER_USER_TYPE)
                .phoneNumber(creatingProfileDto.getPhoneNumber())
                .login(creatingProfileDto.getLogin())
                .longitude(creatingProfileDto.getLongitude())
                .latitude(creatingProfileDto.getLatitude())
                .email(creatingProfileDto.getEmail())
                .build();
    }

    private static Plot mapProfileToPlot(CreatingProfileDto creatingProfileDto, User user) {
        return Plot.builder()
                .jobs(creatingProfileDto.getJobs())
                .innerId(user.getId())
                .longitude(user.getLongitude())
                .latitude(user.getLatitude())
                .area(creatingProfileDto.getArea())
                .build();
    }
}
