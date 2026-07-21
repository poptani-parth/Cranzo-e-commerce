package com.e_commerce.Cranzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CranzoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CranzoApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("parth@123"));
	}
}
