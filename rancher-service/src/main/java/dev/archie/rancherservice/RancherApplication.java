package dev.archie.rancherservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RancherApplication {

	public static void main(String[] args) {
		SpringApplication.run(RancherApplication.class, args);
	}

}
