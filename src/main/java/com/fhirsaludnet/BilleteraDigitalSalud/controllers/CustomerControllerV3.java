package com.fhirsaludnet.BilleteraDigitalSalud.controllers;


import com.fhirsaludnet.BilleteraDigitalSalud.configurations.ExternalizedConfigurations;
import com.fhirsaludnet.BilleteraDigitalSalud.domain.Customer;
import com.fhirsaludnet.BilleteraDigitalSalud.service.CustomerService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v3/customers")
public class CustomerControllerV3 {

    @Autowired
    @Lazy   /// // Se creará solo cuando se use por primera vez
    @Qualifier("jsonResourceService")  //// Especificamos cuál inyectar con @Qualifier
    private CustomerService customerService;


    @Autowired
    private ExternalizedConfigurations externalizedConfigurations;


    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){

        System.out.println(externalizedConfigurations.toString());
        List<Customer> customers = customerService.getCustomer();
        return customers.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(customers);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String id){
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
