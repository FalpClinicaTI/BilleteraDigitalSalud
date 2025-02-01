package com.fhirsaludnet.BilleteraDigitalSalud.service;

import com.fhirsaludnet.BilleteraDigitalSalud.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product addProduct(Product product);
    Product updateProduct(Integer id, Product product);
    Product deleteProduct(Integer id);
    Product pathProduct(Integer id, Product product);
}
