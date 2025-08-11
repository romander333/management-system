package com.romander.managementsystem;

import org.springframework.boot.SpringApplication;

public class TestManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.from(ManagementSystemApplication::main)
                .with(TestcontainersConfiguration.class).run(args);
    }

}
