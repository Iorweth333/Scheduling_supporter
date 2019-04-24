package ioiobagiety;

import ioiobagiety.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class SchedulingSupporterApplication {

    public static void main(String[] args) {

        SpringApplication.run(SchedulingSupporterApplication.class, args);

    }
}
