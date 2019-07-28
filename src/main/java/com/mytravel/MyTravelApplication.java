package com.mytravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.mytravel"})
@EntityScan("com.mytravel.model")
public class MyTravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTravelApplication.class, args);
	}

}
