package dev.archie.stress.tester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.archie.stress.tester.cllents.AccountClient;
import dev.archie.stress.tester.cllents.FieldClient;
import dev.archie.stress.tester.cllents.GardenerServiceClient;
import dev.archie.stress.tester.cllents.HandymanUserClient;
import dev.archie.stress.tester.cllents.UserServiceClient;
import dev.archie.stress.tester.dto.CreatingAccountDto;
import dev.archie.stress.tester.dto.CreatingFieldDto;
import dev.archie.stress.tester.dto.CreatingGardenerDto;
import dev.archie.stress.tester.dto.CreatingHandymanUserDto;
import dev.archie.stress.tester.entities.Gardener;
import dev.archie.stress.tester.entities.HandymanUser;
import dev.archie.stress.tester.entities.Page;
import dev.archie.stress.tester.entities.PaymentSystem;
import dev.archie.stress.tester.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Random;

@EnableFeignClients
@SpringBootApplication
@Slf4j
public class StressTester implements ApplicationRunner {
    public static final String USER_TO_GARDENER = "userToGardener";
    private static final String PLOT_FOR_GARDENER = "plotForGardener";
    private static final String USER_TO_HANDYMAN = "userToHandyman";
    private static final String ACCOUNT_FOR_HANDYMAN = "accountForHandyman";
    public static final int PAGE_SIZE = 100;

    public final GardenerServiceClient gardenerServiceClient;
    public final UserServiceClient userServiceClient;
    private final HandymanUserClient handymanUserClient;
    private final AccountClient accountClient;
    public final String testingArea;
    private final FieldClient fieldClient;

    public StressTester(GardenerServiceClient gardenerServiceClient,
                        UserServiceClient userServiceClient,
                        @Value("${TESTING_WITH_GARDENERS}")
                        String testingArea,
                        FieldClient fieldClient,
                        HandymanUserClient handymanUserClient,
                        AccountClient accountClient) {
        this.gardenerServiceClient = gardenerServiceClient;
        this.userServiceClient = userServiceClient;
        this.testingArea = testingArea;
        this.fieldClient = fieldClient;
        this.handymanUserClient = handymanUserClient;
        this.accountClient = accountClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(StressTester.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        switch (testingArea) {
            case USER_TO_GARDENER -> testingGardeners();
            case PLOT_FOR_GARDENER -> testingFields();
            case USER_TO_HANDYMAN -> testingHandymen();
            case ACCOUNT_FOR_HANDYMAN -> testingAccounts();
            default -> throw new RuntimeException("Expected testing area in application properties");
        }
    }

    private void testingGardeners() {
        Page<User> allRanchers = userServiceClient.getAllRanchers(PAGE_SIZE, 0);
        for (int i = 1; i < allRanchers.getTotalPages(); i++) {
            int finalI = i;
            allRanchers.getContent().stream()
                    .map(StressTester::mapUserToGardenerDto)
                    .forEach(gardenerDto -> {
                        try {
                            gardenerServiceClient.createGardener(gardenerDto);
                        } catch (Exception e) {
                            log.error("Failed in " + finalI + " page: " + e.getMessage());
                        }
                    });
            log.info("Processed " + i + " pages");
            allRanchers = userServiceClient.getAllRanchers(PAGE_SIZE, i);
        }
    }

    private void testingFields() {
        Page<Gardener> allGardeners = gardenerServiceClient.getAll(PAGE_SIZE, 0);
        GeometryFactory geometryFactory = new GeometryFactory();
        for (int i = 1; i < allGardeners.getTotalPages(); i++) {
            int finalI = i;
            allGardeners.getContent().stream()
                    .map((gardener) -> {
                                Coordinate start = new Coordinate(Math.random() * 100, Math.random() * 100);
                                return CreatingFieldDto.builder()
                                        .address("address")
                                        .gardenerId(gardener.getId())
                                        .longitude(Math.random() * 100)
                                        .latitude(Math.random() * 100)
                                        .area(geometryFactory.createPolygon(new Coordinate[]{
                                                start,
                                                new Coordinate(Math.random() * 100, Math.random() * 100),
                                                new Coordinate(Math.random() * 100, Math.random() * 100),
                                                start
                                        }))
                                        .build();
                            }
                    )
                    .forEach(creatingFieldDto -> {
                        try {
                            fieldClient.create(creatingFieldDto, creatingFieldDto.getGardenerId());
                        } catch (Exception e) {
                            log.error("Error in page " + finalI + " " + e.getMessage());
                        }
                    });
            log.info("Processed " + i + " pages");
            allGardeners = gardenerServiceClient.getAll(PAGE_SIZE, i);
        }
    }

    private void testingHandymen() {
        Page<User> allHandymen = userServiceClient.getAllHandymen(PAGE_SIZE, 180);
        for (int i = 181; i < allHandymen.getTotalPages(); i++) {
            int finalI = i;
            allHandymen.getContent().stream()
                    .map(StressTester::mapUserToHandymanDto)
                    .forEach(creatingHandymanDto -> {
                        try {
                            handymanUserClient.create(creatingHandymanDto);
                        } catch(Exception e){
                            log.error("Failed in " + finalI + " page: " + e.getMessage());
                        }
                    });
            log.info("Processed " + i + " pages");
            allHandymen = userServiceClient.getAllHandymen(PAGE_SIZE, i);
        }
    }

    private void testingAccounts() {
        Page<HandymanUser> handymen = handymanUserClient.getAll(PAGE_SIZE, 0);
        for (int i = 1; i < handymen.getTotalPages(); i++) {
            for (HandymanUser handymanUser : handymen.content) {
                CreatingAccountDto creatingAccountDto = CreatingAccountDto.builder()
                        .paymentSystem(PaymentSystem.MASTERCARD)
                        .cardNumber(generateRandomCardNumber())
                        .bankName("StonksBank")
                        .build();
                try {
                    accountClient.create(creatingAccountDto, handymanUser.getId());
                } catch (Exception e) {
                    log.error("Failed in " + i + " page: " + e.getMessage());
                }
            }
            log.info("Processed " + i + " pages");
            handymen = handymanUserClient.getAll(PAGE_SIZE, i);
        }
    }

    private static CreatingGardenerDto mapUserToGardenerDto(User user) {
        return CreatingGardenerDto.builder()
                .phone(user.getPhoneNumber())
                .email(user.getEmail())
                .firstName(user.getLogin().split(" ")[0])
                .lastName(user.getLogin().split(" ").length > 1 ? user.getLogin().split(" ")[1] : " ")
                .build();
    }

    private static CreatingHandymanUserDto mapUserToHandymanDto(User user) {
        return CreatingHandymanUserDto.builder()
                .firstName(user.getLogin().split(" ")[0])
                .lastName(user.getLogin().split(" ").length > 1 ? user.getLogin().split(" ")[1] : " ")
                .email(user.getEmail())
                .phone(user.getPhoneNumber())
                .build();
    }

    public static String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append(random.nextInt(10));
            }
            if (i < 3) {
                sb.append("-");
            }
        }
        return sb.toString();
    }
}
