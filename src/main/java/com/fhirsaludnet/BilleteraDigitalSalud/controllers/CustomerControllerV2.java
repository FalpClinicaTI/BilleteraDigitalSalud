package com.fhirsaludnet.BilleteraDigitalSalud.controllers;

import com.fhirsaludnet.BilleteraDigitalSalud.domain.Customer;
import com.fhirsaludnet.BilleteraDigitalSalud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v2/customers")
public class CustomerControllerV2 {

    // Inyección de dependencias @Autowired IoC (Inversion of Control)
    // Inyección por campo
    @Autowired
    private CustomerService customerService;

    /**
     * Obtiene la lista completa de clientes.
     * Este método devuelve la lista de todos los clientes en formato JSON.
     * Si no hay clientes, se devolverá una lista vacía.
     *
     * @return Una lista de objetos {@link Customer}.
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getCustomer(); // ✅ Almacenar en variable
        return customers.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(customers);
    }

    /**
     * Obtiene un cliente por su identificador único.
     * Este método devuelve el cliente identificado por el {@code id} proporcionado.
     * Si el cliente no existe, se puede lanzar una excepción o retornar un mensaje adecuado.
     *
     * @param id El identificador único del cliente a buscar.
     * @return El objeto {@link Customer} con el identificador proporcionado.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") String id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)  // Si hay cliente, devuelve ResponseEntity.ok(cliente)
                .orElseGet(() -> ResponseEntity.noContent().build());  // Si está vacío, devuelve 204 No Content
    }

    /**
     * Obtiene un cliente por su nombre.
     * Este método devuelve el cliente identificado por el {@code name} proporcionado.
     * Si el cliente no existe, se puede lanzar una excepción o retornar un mensaje adecuado.
     *
     * @param name El nombre del cliente a buscar.
     * @return El objeto {@link Customer} con el nombre proporcionado.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCustomerByName(@PathVariable("name") String name) {
        return customerService.getCustomerByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    /**
     * Agrega un nuevo objeto {@code Customer} a la lista de clientes.
     * Este endpoint responde a solicitudes HTTP POST en la ruta base de la clase controladora.
     *
     * @param customer El objeto {@code Customer} a agregar.
     * @return El objeto {@code Customer} agregado.
     */
    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            customerService.addCustomer(customer);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(customer.getId())
                    .toUri();
            return ResponseEntity.created(location).body(customer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Actualiza un cliente existente.
     * Este método actualiza los datos de un cliente existente.
     * Si el cliente no existe, se puede lanzar una excepción o retornar un mensaje adecuado.
     *
     * @param customer El objeto {@link Customer} con los datos actualizados.
     * @return Un objeto {@link ResponseEntity} con el código de estado HTTP correspondiente.
     */
    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        try {
            customerService.updateCustomer(customer);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<?> patchCustomer(@RequestBody Customer customer) {
        try {
            customerService.patchCustomer(customer);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
