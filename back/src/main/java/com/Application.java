package com;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeles.Products;
import com.services.ProductsService;
import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner(ProductsService productService) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Map<String, List<Products>>> typeReference = new TypeReference<>() {
			};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/products.json");

			try {
				Map<String, List<Products>> products = mapper.readValue(inputStream, typeReference);
				productService.save(products.get("Data"));
				System.out.println("Products Saved!");
			} catch (IOException e) {
				System.out.println("Unable to save products: " + e.getMessage());
			}
		};
	}

	/*
	 * @Bean
	 * public WebMvcConfigurer corsConfigurer() {
	 * return new WebMvcConfigurer() {
	 * 
	 * @Override
	 * public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("*");
	 * }
	 * };
	 * }
	 */
}
