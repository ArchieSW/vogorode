package dev.archie.landscapeservice;

import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class LandscapeConfiguration {

    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }

}
