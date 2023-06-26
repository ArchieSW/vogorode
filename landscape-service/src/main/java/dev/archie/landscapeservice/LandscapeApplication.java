package dev.archie.landscapeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients
public class LandscapeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LandscapeApplication.class, args);
	}

}
