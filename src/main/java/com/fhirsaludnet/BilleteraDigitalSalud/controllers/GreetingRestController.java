package com.fhirsaludnet.BilleteraDigitalSalud.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingRestController {

    @GetMapping({"/greeting/{name}", "/g/{name}"})
    public String greeting(@PathVariable String name){
        System.out.println("Solicitud ejecutada!");
        return "Hola " + name + ", bienvenido a la Billetera Digital de Salud";
    }
}
