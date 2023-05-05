package dev.archie.handymanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HandymanApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandymanApplication.class, args);
	}

}
