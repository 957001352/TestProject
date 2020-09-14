package com.dhlk.mqtt.subscribe.dhlk_mqtt_subscribe;

import com.dhlk.mqtt.subscribe.utils.HttpClientMqtt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.dhlk.mqtt.subscribe")
@SpringBootApplication
public class DhlkMqttSubscribeApplication {
	public static void main(String[] args) {
		SpringApplication.run(DhlkMqttSubscribeApplication.class, args);
//		HttpClientMqtt client = new HttpClientMqtt();
//		client.start();
	}

}
