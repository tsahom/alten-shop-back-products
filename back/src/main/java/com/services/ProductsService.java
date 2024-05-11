package com.services;

import com.modeles.ProductCatalogue;
import com.modeles.Products;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private ProductCatalogue productCatalogue;

    public ProductsService(ProductCatalogue productCatalogue) {
            this.productCatalogue = productCatalogue;
    }

    public Iterable<Products> list() {
        return productCatalogue.findAll();
    }

    public Products save(Products product) {
        return productCatalogue.save(product);
    }

    public Iterable<Products> save(List<Products> products) {
        return productCatalogue.saveAll(products);
    }

}
