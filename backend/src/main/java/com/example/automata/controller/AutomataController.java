package com.example.automata.controller;

import com.example.automata.service.AutomataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/automata")
@CrossOrigin(origins = "http://localhost:4200")
public class AutomataController {

    @Autowired
    private AutomataService automataService;

    // Endpoint para la Tabla 1 (Alfabeto: K, G, X, F)
    @PostMapping("/tabla1")
    public ResponseEntity<String> validarTabla1(@RequestBody String cadena) {
        boolean esValida = automataService.validarTabla1(cadena);
        return esValida ? ResponseEntity.ok("Aceptada por Tabla 1") 
                        : ResponseEntity.badRequest().body("Rechazada");
    }

    // Endpoint para la Tabla 2 (Alfabeto: H, S, C)
    @PostMapping("/tabla2")
    public ResponseEntity<String> validarTabla2(@RequestBody String cadena) {
        boolean esValida = automataService.validarTabla2(cadena);
        return esValida ? ResponseEntity.ok("Aceptada por Tabla 2") 
                        : ResponseEntity.badRequest().body("Rechazada");
    }

    // Endpoint para la Tabla 3 (Alfabeto: S, A, R)
    @PostMapping("/tabla3")
    public ResponseEntity<String> validarTabla3(@RequestBody String cadena) {
        boolean esValida = automataService.validarTabla3(cadena);
        return esValida ? ResponseEntity.ok("Aceptada por Tabla 3") 
                        : ResponseEntity.badRequest().body("Rechazada");
    }
}