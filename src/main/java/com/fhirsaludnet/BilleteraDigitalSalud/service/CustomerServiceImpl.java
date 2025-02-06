package com.fhirsaludnet.BilleteraDigitalSalud.service;
import java.util.*;

import com.fhirsaludnet.BilleteraDigitalSalud.domain.Customer;
import org.springframework.stereotype.Service;

@Service("listResourceService")
public class CustomerServiceImpl implements CustomerService {
    private final List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer("11", "Fernando Edwards", "Fernando", "1234"),
            new Customer("22", "Carlos Montes", "Carlos", "1234"),
            new Customer("33", "Teresa Martinez", "Teresa", "1234"),
            new Customer("44", "Patricio Gallardo", "PatricioGallardo", "1234")
    ));

    @Override
    public List<Customer> getCustomer() {
        return customers;
    }


    @Override
    public Optional<Customer> getCustomerById(String id) {
        return  customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Customer> getCustomerByName(String name) {
        return customers.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst();

    }

    public void addCustomer(Customer customer) {
        var customerExists = customers.stream()
                .anyMatch(c -> c.getId().equals(customer.getId()));
        if (customerExists) {
            throw new IllegalArgumentException("El cliente ya existe");
        }
        customers.add(customer);
    }

    public void updateCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customers.stream()
                .filter(c -> c.getId().equals(customer.getId()))
                .findFirst();

        if (existingCustomer.isPresent()) {
            Customer c = existingCustomer.get();
            c.setName(customer.getName());
            c.setUsername(customer.getUsername());
            c.setPassword(customer.getPassword());
        } else {
            //NoSuchElementException: Cuando se busca un elemento en una colección y no existe.
            throw new NoSuchElementException("El cliente no existe");
        }
    }

    @Override
    public void deleteCustomer(String id) {
        Optional<Customer> customerExiste = getCustomerById(id);
        if (customerExiste.isPresent()) {
            customers.remove(customerExiste.get());
        } else {
            throw new NoSuchElementException("El cliente con ID " + id + " no existe.");
        }
    }

    @Override
    public void patchCustomer(Customer customer) {
        Optional<Customer> optionalCustomer = getCustomerById(customer.getId());
        optionalCustomer.ifPresentOrElse(existingCustomer -> {
            if (customer.getName() != null) {
                existingCustomer.setName(customer.getName());
            }
            if (customer.getUsername() != null) {
                existingCustomer.setUsername(customer.getUsername());
            }
            if (customer.getPassword() != null) {
                existingCustomer.setPassword(customer.getPassword());
            }
        }, () -> {
            throw new NoSuchElementException("El cliente con ID " + customer.getId() + " no existe.");
        });
    }


}
