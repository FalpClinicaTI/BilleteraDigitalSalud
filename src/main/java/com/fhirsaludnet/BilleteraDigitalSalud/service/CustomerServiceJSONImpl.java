package com.fhirsaludnet.BilleteraDigitalSalud.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhirsaludnet.BilleteraDigitalSalud.domain.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

@Primary
@Service("jsonResourceService")

public class CustomerServiceJSONImpl implements CustomerService {

    @Override
    public List<Customer> getCustomer() {
        return getCustomers();
    }


    private List<Customer> getCustomers() {
        try{
            // Leer el archivo JSON y convertirlo en una lista de clientes
            return new ObjectMapper()
                    .readValue(
                            this.getClass()
                                    .getResourceAsStream("/json/customer.json"),  // dentro de resources
                            new TypeReference<List<Customer>>() {}
                    );

        }
        catch (IOException e){
            //throw new RuntimeException(e);
            // En caso de error, retornar una lista vacía en lugar de null
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Customer customer = getCustomers().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        return customer != null ? Optional.of(customer) : Optional.empty();
    }

    @Override
    public Optional<Customer> getCustomerByName(String name) {
        return Optional.empty();
    }

    @Override
    public void addCustomer(Customer customer) {

    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public void deleteCustomer(String id) {

    }

    @Override
    public void patchCustomer(Customer customer) {

    }
}
