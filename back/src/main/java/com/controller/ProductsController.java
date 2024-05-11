package com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.modeles.ProductCatalogue;
import com.modeles.Products;

@RestController
public class ProductsController {
	@Autowired

	private ProductCatalogue productCatalogue;

	@CrossOrigin
	@GetMapping("/products")
	public Iterable<Products> getProducts() {
		return productCatalogue.findAll();
	}

	@CrossOrigin
	@PostMapping("/products")
	public @ResponseBody Products postProducts(@RequestBody Products products) {
		return productCatalogue.save(products);
	}

	@CrossOrigin
	@GetMapping("/products/{id}")
	public @ResponseBody Optional<Products> getProductById(@PathVariable(value = "id") int id) {
		return productCatalogue.findById(id);
	}

	@CrossOrigin
	@PatchMapping("/products/{id}")
	public @ResponseBody Products updateProducts(@PathVariable(value = "id") int id, @RequestBody Products products) {
		if(productCatalogue.existsById(id)){
			productCatalogue.deleteById(id);
			return productCatalogue.save(products);
		}
		return new Products();
	}

	@CrossOrigin
	@DeleteMapping("/products/{id}")
	public @ResponseBody Optional<Products> removeProducts(@PathVariable(value = "id") int id) {
		Optional<Products> prod = productCatalogue.findById(id);
		productCatalogue.deleteById(id);
		return prod;
	}

}
