package com.example.automata.controller;

// IMPORTAR LOS 3 SERVICIOS CON LÓGICA NFA A DFA
import com.example.automata.service.Ejercicio1Service;
import com.example.automata.service.Ejercicio2Service;
import com.example.automata.service.Ejercicio3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/automata")
@CrossOrigin(origins = "http://localhost:4200")
public class AutomataController {

    // INYECCIÓN DE LOS 3 SERVICIOS
    @Autowired
    private Ejercicio1Service ejercicio1Service; // Tabla 1: K, G, X, F

    @Autowired
    private Ejercicio2Service ejercicio2Service; // Tabla 2: H, S, C

    @Autowired
    private Ejercicio3Service ejercicio3Service; // Tabla 3: S, A, R

    // Endpoint para la Tabla 1 (Alfabeto: K, G, X, F)
    // Utiliza NFA a DFA para validar la cadena
    @PostMapping("/tabla1")
    public ResponseEntity<String> validarTabla1(@RequestBody String cadena) {
        boolean esValida = ejercicio1Service.validar(cadena);
        return esValida ? ResponseEntity.ok("Aceptada por Tabla 1") 
                        : ResponseEntity.badRequest().body("Rechazada");
    }

    // Endpoint para la Tabla 2 (Alfabeto: H, S, C)
    // Utiliza NFA a DFA para validar la cadena
    @PostMapping("/tabla2")
    public ResponseEntity<String> validarTabla2(@RequestBody String cadena) {
        boolean esValida = ejercicio2Service.validarCadena(cadena);
        return esValida ? ResponseEntity.ok("Aceptada por Tabla 2") 
                        : ResponseEntity.badRequest().body("Rechazada");
    }

    // Endpoint para la Tabla 3 (Alfabeto: S, A, R)
    // Utiliza NFA a DFA para validar la cadena
    @PostMapping("/tabla3")
    public ResponseEntity<String> validarTabla3(@RequestBody String cadena) {
        boolean esValida = ejercicio3Service.validarCadena(cadena);
        return esValida ? ResponseEntity.ok("Aceptada por Tabla 3") 
                        : ResponseEntity.badRequest().body("Rechazada");
    }
}