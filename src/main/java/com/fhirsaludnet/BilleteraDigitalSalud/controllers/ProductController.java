package com.fhirsaludnet.BilleteraDigitalSalud.controllers;


import com.fhirsaludnet.BilleteraDigitalSalud.domain.Product;
import com.fhirsaludnet.BilleteraDigitalSalud.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService = new ProductServiceImpl();

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if( products == null || products.isEmpty() ){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if( product == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        if( newProduct == null ){
            return ResponseEntity.badRequest().body("El producto ya existe");
        }

        // capitulo 21 - Uri
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProduct.getId())
                .toUri();

        return ResponseEntity.created(location).body(newProduct);
        //****************************************************************
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        try{
            Product updatedProduct = productService.updateProduct(id, product);
            if( updatedProduct == null ){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        Product deletedProduct = productService.deleteProduct(id);
        if( deletedProduct == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> pathProduct(@PathVariable Integer id, @RequestBody Product product) {
        try{
            Product updatedProduct = productService.pathProduct(id, product);
            if( updatedProduct == null ){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }




    }




}
