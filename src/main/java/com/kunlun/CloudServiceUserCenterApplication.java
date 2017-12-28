package com.kunlun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class CloudServiceUserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudServiceUserCenterApplication.class, args);
	}
}
