package com.fhirsaludnet.BilleteraDigitalSalud.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldRestController {

    @GetMapping({"/hello", "/hw"})
    public String helloWorld(){
        System.out.println("Solicitud ejecutada!");
        return "Hola mundo de Spring Boot";
    }
}
