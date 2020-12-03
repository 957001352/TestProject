package com.dhlk.energy.subscribe.dhlk_energy_subscribe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.dhlk.energy.subscribe")
@SpringBootApplication
public class DhlkEnergySubscribeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhlkEnergySubscribeApplication.class, args);
    }

}
