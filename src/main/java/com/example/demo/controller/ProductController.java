package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.nio.file.ProviderNotFoundException;
import java.util.List;

@RestController
public class ProductController {
   private final ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    List<Product> all(){
        return productRepository.findAll();
    }

    @PostMapping("/products")
    Product newProduct(@RequestBody Product newProduct){
        return productRepository.save( newProduct );
    }

    @GetMapping("/products/{id}")
    Product one(@PathVariable Long id){
        return productRepository.findById( id ).orElseThrow(()-> new ProviderNotFoundException() );
    }

    @PutMapping("/products/{id}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id){
        return productRepository.findById( id ).map( product -> {
            product.setName( newProduct.getName() );
            product.setPrice( newProduct.getPrice() );
            product.setDescription(newProduct.getDescription());
            product.setManufacturer( newProduct.getManufacturer() );

            return productRepository.save( product );
        } ).orElseGet( ()->{
            newProduct.setId( id );
            return productRepository.save( newProduct );
        } );
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id){
        productRepository.deleteById( id );
    }

}
