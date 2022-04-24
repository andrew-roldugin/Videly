package ru.vsu.csf.group7_1.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vsu.csf.group7_1.services.FirebaseInit;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        FirebaseInit.initialize();
    }
}
