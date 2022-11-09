package com.kafe.kafe;

import com.kafe.kafe.dto.request.ProductRequestDTO;
import com.kafe.kafe.entity.ProductCategory;
import com.kafe.kafe.entity.Role;
import com.kafe.kafe.entity.User;
import com.kafe.kafe.service.ProductService;
import com.kafe.kafe.service.UserService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class KafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafeApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, ProductService productService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(new User(null, "Maria Fernanda", "mariafmaceab", "sasha", new ArrayList<>()));
			userService.saveUser(new User(null, "Sasha Lulu", "sasha", "sasha", new ArrayList<>()));
			userService.addRoleToUser("mariafmaceab", "ROLE_USER");
			userService.addRoleToUser("mariafmaceab", "ROLE_ADMIN");
			userService.addRoleToUser("sasha", "ROLE_USER");

			productService.saveProduct(
					new ProductRequestDTO("Manta gris", ProductCategory.BLANKET, new BigDecimal("10.0"),
							"Hermosa manta gris, ideal para tu cama",120.0,50.0,
							"https://i.ibb.co/v1kfgvT/IMG-20220614-142601-515.jpg")
			);
			productService.saveProduct(
					new ProductRequestDTO("Tapete negro", ProductCategory.RUG, new BigDecimal("12.0"),
							"Tape negro perfecto para tu habitación",150.0,120.0,
							"https://i.ibb.co/R4XwncV/Screenshot-20220720-110039-Instagram.jpg")
			);
			productService.saveProduct(
					new ProductRequestDTO("Tapete blanco", ProductCategory.RUG, new BigDecimal("12.0"),
							"Hermoso tapete gris perfecto para tu habitación",150.0,120.0,
							"https://i.ibb.co/V3jbvbb/Screenshot-20220720-105951-Instagram.jpg")
			);
			productService.saveProduct(
					new ProductRequestDTO("Tapete redondo blanco", ProductCategory.RUG, new BigDecimal("12.0"),
							"Tapete ideal para poner tus pug",50.0,50.0,
							"https://i.ibb.co/M2tFj6L/Screenshot-20220720-105931-Instagram.jpg")
			);
			productService.saveProduct(
					new ProductRequestDTO("Tapete gris-azul", ProductCategory.RUG, new BigDecimal("12.0"),
							"Tapete gris-azul perfecto para tu sala de estar",150.0,150.0,
							"https://i.ibb.co/drYq28c/Screenshot-20220720-105848-Instagram.jpg")
			);
			productService.saveProduct(
					new ProductRequestDTO("Tapete blanco", ProductCategory.RUG, new BigDecimal("12.0"),
							"Tapete pequeño, elegante y sobrio para tu cuarto",100.0,50.0,
							"https://i.ibb.co/VVdJsjb/Screenshot-20220720-105828-Instagram.jpg")
			);
			productService.saveProduct(
					new ProductRequestDTO("Tapete corazón", ProductCategory.RUG, new BigDecimal("12.0"),
							"Hermoso tapete decorativo rosa",120.0,100.0,
							"https://i.ibb.co/sbLx6Dd/Screenshot-20220720-105724-Instagram.jpg")
			);
			productService.saveProduct(
					new ProductRequestDTO("Tapete café grande", ProductCategory.RUG, new BigDecimal("12.0"),
							"Tapete ideal para tu sala de estar",150.0,180.0,
							"https://i.ibb.co/sgxQD53/Screenshot-20220720-105443-Instagram.jpg")
			);
			productService.saveProduct(
					new ProductRequestDTO("Cojines + manta", ProductCategory.RUG, new BigDecimal("12.0"),
							"Juego de cojines + manta para tu sala de estar",100.0,150.0,
							"https://i.ibb.co/mS7Tpr8/Screenshot-20221030-165528-Instagram.jpg")
			);
		};
	}
}
