package com.fiap.tc;

import com.fiap.tc.infrastructure.core.security.property.OriginApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableConfigurationProperties(OriginApiProperty.class)
@EnableScheduling
public class TcAuthBackendApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(TcAuthBackendApiApplication.class, args);
    }

}
