package com.fhirsaludnet.BilleteraDigitalSalud.controllers;

import com.fhirsaludnet.BilleteraDigitalSalud.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1") // Unificación de Rutas
// Manejado por server.servlet.context-path=/sistema/api/v1 de versiones

public class CustomerControllerV1 {

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
    public ResponseEntity<List<Customer>> getAllCustomers() {
        //* Devuelve la lista de clientes serializada en formato JSON
         return ResponseEntity.ok(customers) ;
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
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        // Buscar el cliente por su ID
        var customer = customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        if(customer.isEmpty()){
            // Si el cliente no está presente, devuelve un 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente con id " + id + " no existe");
        }

        // Si el cliente está presente, devuelve un 200
        return ResponseEntity.ok(customer.get());
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
    public ResponseEntity<?> getCustomerByName(@PathVariable String name) {
        //* Devuelve el cliente con el nombre especificado
        var customer = customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst();

        if(customer.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe");
        }
        // Si el cliente no está presente, devuelve un 404
        return ResponseEntity.ok(customer.get());

    }

    @PostMapping("/customers")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        // verificar que Username no exista en la lista
        var existingCustomer = customers.stream()
                .filter(c -> c.getUsername().equals(customer.getUsername()))
                .findFirst();

        if (existingCustomer.isPresent()) {
            // Si el cliente ya existe, retorna un 409 personalizado
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El cliente con UserName:" + customer.getUsername() + " ya existe");
        }
        //* Agrega un nuevo cliente a la lista
        customers.add(customer);
        // Crear una respuesta con el código 201 PERSONALIZADO
        //return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado exitosamente:"+ customer.getUsername());

        // Construir la URI para individualizar el recursos creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUri();
        return ResponseEntity.created(location).body(customer);

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
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        //* Actualiza los datos de un cliente
        var existingCustomer = customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        if (existingCustomer.isEmpty()) {
            // Si no se encuentra el cliente, retorna un 404ResponseEntity Personalizado
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe " + id);
            // Retorno ResponseEntity simplificado
            return ResponseEntity.notFound().build();
        }
        // Si el cliente existe, actualiza sus datos
        var customerToUpdate = existingCustomer.get(); // Extraemos el valor del Optional
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setUsername(customer.getUsername());
        customerToUpdate.setPassword(customer.getPassword());
        // Retorna un mensaje con el código 204 ResponseEntity simplificado
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina un cliente de la lista.
     * Este método elimina el cliente identificado por el {@code id} proporcionado
     * de la lista de clientes. Si el cliente no existe, no se realiza ninguna acción.
     *
     * @param id El identificador único del cliente a eliminar.
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        //* Elimina un cliente de la lista
        boolean borroConExito = customers.removeIf(c -> c.getId().equals(id));
        if(!borroConExito){
            // Si no se encuentra el cliente, retorna un 404 personalizado
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente con Id: " + id + "  no existe");
            // Retorno 404 ResponseEntity simplificado
            return ResponseEntity.notFound().build();
        }
        // Retorna un mensaje con el código 204 con contenido personalizado
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente fue eliminado exitosamente");
        // Retorna un mensaje con el código 204 ResponseEntity simplificado
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<?> patchCustomer(@PathVariable String id, @RequestBody Customer customer) {
        //* Actualiza los datos de un cliente
        var existingCustomer = customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        if (existingCustomer.isEmpty()) {
            // Si no se encuentra el cliente, retorna un 404 personalizado
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe");
            // Retorno ResponseEntity simplificado
            return ResponseEntity.notFound().build();
           }
        // Si el cliente existe, actualiza sus datos
        var customerToUpdate = existingCustomer.get(); // Extraemos el valor del Optional

        if (customer.getName() != null) {
            customerToUpdate.setName(customer.getName());
        }
        if (customer.getUsername() != null) {
            customerToUpdate.setUsername(customer.getUsername());
        }
        if (customer.getPassword() != null) {
            customerToUpdate.setPassword(customer.getPassword());
        }
        return ResponseEntity.noContent().build();
    }

}
