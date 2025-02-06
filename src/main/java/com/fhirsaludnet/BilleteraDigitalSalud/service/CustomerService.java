package com.fhirsaludnet.BilleteraDigitalSalud.service;

import com.fhirsaludnet.BilleteraDigitalSalud.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    public List<Customer> getCustomer();
    public Optional<Customer> getCustomerById(String id);
    public Optional<Customer> getCustomerByName(String name);
    public void addCustomer(Customer customer);
    public void updateCustomer(Customer customer);
    public void deleteCustomer(String id);
    public void patchCustomer(Customer customer);
}
