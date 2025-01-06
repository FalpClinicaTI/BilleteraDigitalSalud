package com.fhirsaludnet.BilleteraDigitalSalud.controllers;

import com.fhirsaludnet.BilleteraDigitalSalud.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api") // Unificación de Rutas
public class CustomerController {

    private List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer("1", "Juan", "juan", "1234"),
            new Customer("2", "Pedro", "pedro", "1234"),
            new Customer("3", "Maria", "maria", "1234"),
            new Customer("4", "Luis", "luis", "1234")
    ));

    /**
     * Obtiene la lista completa de clientes.
     *
     * Este método devuelve la lista de todos los clientes en formato JSON.
     * Si no hay clientes, se devolverá una lista vacía.
     *
     * @return Una lista de objetos {@link Customer}.
     */
    //@GetMapping("/customers")
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        //* Devuelve la lista de clientes serializada en formato JSON
         return customers;
    }

    /**
     * Obtiene un cliente por su identificador único.
     *
     * Este método devuelve el cliente identificado por el {@code id} proporcionado.
     * Si el cliente no existe, se puede lanzar una excepción o retornar un mensaje adecuado.
     *
     * @param id El identificador único del cliente a buscar.
     * @return El objeto {@link Customer} con el identificador proporcionado.
     */
    @GetMapping("/customers/id/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        //* Devuelve el cliente con el id especificado
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con ID: " + id));
    }

    /**
     * Obtiene un cliente por su nombre.
     *
     * Este método devuelve el cliente identificado por el {@code name} proporcionado.
     * Si el cliente no existe, se puede lanzar una excepción o retornar un mensaje adecuado.
     *
     * @param name El nombre del cliente a buscar.
     * @return El objeto {@link Customer} con el nombre proporcionado.
     */
    @GetMapping("/customers/name/{name}")
    public Customer getCustomerByName(@PathVariable String name) {
        //* Devuelve el cliente con el nombre especificado
        return customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con Nombre: " + name));
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer customer) {
        //* Agrega un nuevo cliente a la lista
        customers.add(customer);
        return customer;
    }

    /**
     * Actualiza los datos de un cliente existente.
     * Este método recibe un objeto {@link Customer} con los nuevos datos y
     * actualiza la información del cliente identificado por el {@code id} proporcionado.
     * Si el cliente no existe, se puede lanzar una excepción o retornar un mensaje adecuado.
     *
     * @param id El identificador único del cliente a actualizar.
     * @param customer El objeto {@link Customer} que contiene los nuevos datos a actualizar.
     * @return El objeto {@link Customer} con los datos actualizados.
     */
    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        //* Actualiza los datos de un cliente
        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setName(customer.getName());
        existingCustomer.setUsername(customer.getUsername());
        existingCustomer.setPassword(customer.getPassword());
        return existingCustomer;
    }

    /**
     * Elimina un cliente de la lista.
     * Este método elimina el cliente identificado por el {@code id} proporcionado
     * de la lista de clientes. Si el cliente no existe, no se realiza ninguna acción.
     *
     * @param id El identificador único del cliente a eliminar.
     */
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable String id) {
        //* Elimina un cliente de la lista
        customers.removeIf(c -> c.getId().equals(id));
    }

    /**
     * Actualiza parcialmente los datos de un cliente existente.
     * Este método recibe un objeto {@link Customer} con los nuevos datos y
     * actualiza parcialmente la información del cliente identificado por el {@code id} proporcionado.
     * Si el cliente no existe, se puede lanzar una excepción o retornar un mensaje adecuado.
     *
     * @param id El identificador único del cliente a actualizar.
     * @param customer El objeto {@link Customer} que contiene los nuevos datos a actualizar.
     * @return El objeto {@link Customer} con los datos actualizados.
     */
    @PatchMapping("/customers/{id}")
    public Customer patchCustomer(@PathVariable String id, @RequestBody Customer customer) {
        //* Actualiza los datos de un cliente
        Customer existingCustomer = getCustomerById(id);
        if (customer.getName() != null) {
            existingCustomer.setName(customer.getName());
        }
        if (customer.getUsername() != null) {
            existingCustomer.setUsername(customer.getUsername());
        }
        if (customer.getPassword() != null) {
            existingCustomer.setPassword(customer.getPassword());
        }
        return existingCustomer;
    }



    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer not found")
    public static class CustomerNotFoundException extends RuntimeException {
        public CustomerNotFoundException(String message) {
            super(message);
        }
    }
}
