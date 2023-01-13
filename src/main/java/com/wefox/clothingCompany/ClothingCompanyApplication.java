package com.wefox.clothingCompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ClothingCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothingCompanyApplication.class, args);
	}

}
