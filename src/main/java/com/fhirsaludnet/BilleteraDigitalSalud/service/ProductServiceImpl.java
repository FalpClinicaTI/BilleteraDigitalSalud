package com.fhirsaludnet.BilleteraDigitalSalud.service;

import com.fhirsaludnet.BilleteraDigitalSalud.domain.Product;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProductServiceImpl implements ProductService {

    List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1, "Producto 1", 100.0, 10),
            new Product(2, "Producto 2", 200.0, 20),
            new Product(3, "Producto 3", 300.0, 30),
            new Product(4, "Producto 4", 400.0, 40)
    ));

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(Integer id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Product addProduct(Product product) {
        Product productSeek = getProductById(product.getId());
        if( productSeek != null ){
            return null;
        }
        products.add(product);
        return product;
    }

    @Override
    public Product updateProduct(Integer id, Product product) {
        if(!(id.equals(product.getId()))){
            throw new IllegalArgumentException("El id del producto no coincide con el id del producto a actualizar");
        }
        Product productSeek = getProductById(id);
        if( productSeek == null ){
            return null;
        }
        productSeek.setName(product.getName());
        productSeek.setPrice(product.getPrice());
        productSeek.setStock(product.getStock());
        return productSeek;
    }

    @Override
    public Product deleteProduct(Integer id) {
        Product productSeek = getProductById(id);
        if( productSeek == null ){
            return null;
        }
        products.remove(productSeek);
        return productSeek;
    }

    @Override
    public Product pathProduct(Integer id, Product product) {
        if(!(id.equals(product.getId()))){
            throw new IllegalArgumentException("El id del producto no coincide con el id del producto a actualizar");
        }
        Product productSeek = getProductById(id);
        if( productSeek == null ){
            return null;
        }
        if(product.getName() != null){
            productSeek.setName(product.getName());
        }
        if(product.getPrice() != null){
            productSeek.setPrice(product.getPrice());
        }
        if(product.getStock() != null){
            productSeek.setStock(product.getStock());
        }
        return productSeek;
    }
}
