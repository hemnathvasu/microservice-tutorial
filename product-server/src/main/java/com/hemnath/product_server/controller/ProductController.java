package com.hemnath.product_server.controller;

import com.hemnath.product_server.entity.Product;
import com.hemnath.product_server.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    //Create a product
    @PostMapping("/save")
    public Product saveProduct(@RequestBody Product product){
        return productRepository.save(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> fetchProductById(@PathVariable Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found wiht ID: "+id));

        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product){
        return productRepository.save(product);
    }
}
