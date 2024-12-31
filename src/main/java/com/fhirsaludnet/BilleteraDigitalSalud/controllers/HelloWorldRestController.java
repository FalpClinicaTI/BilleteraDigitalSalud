package com.fhirsaludnet.BilleteraDigitalSalud.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldRestController {

    @GetMapping({"/hello", "/hw", "hola"})
    public String helloWorld(){
        return "Hola mundo de Spring Boot";
    }
}
