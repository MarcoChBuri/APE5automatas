package com.example.automata.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class Ejercicio2Service {

    // Estructuras para el AFN (Basado en tu imagen)
    private final Map<String, Map<Character, Set<String>>> afn = new HashMap<>();
    private final String inicialAFN = "q0";
    private final String finalAFN = "q3";

    // Estructuras para el AFD generado
    private final Map<Set<String>, Map<Character, Set<String>>> tablaAFD = new HashMap<>();
    private final Set<Set<String>> estadosFinalesAFD = new HashSet<>();
    private final Set<String> estadoInicialAFD = Set.of(inicialAFN);

    public Ejercicio2Service() {
        definirAFN();
        generarAFD();
    }

    private void definirAFN() {
        // q0 --H--> {q1}
        afn.put("q0", Map.of('H', Set.of("q1")));
        // q1 --S--> {q1, q2}
        afn.put("q1", Map.of('S', Set.of("q1", "q2")));
        // q2 --C--> {q3}
        afn.put("q2", Map.of('C', Set.of("q3")));
        // q3 (Final)
        afn.put("q3", new HashMap<>());
    }

    private void generarAFD() {
        Queue<Set<String>> cola = new LinkedList<>();
        cola.add(estadoInicialAFD);
        
        Set<Set<String>> visitados = new HashSet<>();
        visitados.add(estadoInicialAFD);

        while (!cola.isEmpty()) {
            Set<String> actualSet = cola.poll();
            
            // Marcar como final si contiene q3
            if (actualSet.contains(finalAFN)) {
                estadosFinalesAFD.add(actualSet);
            }

            // Transiciones para cada símbolo del alfabeto {H, S, C}
            for (char simbolo : new char[]{'H', 'S', 'C'}) {
                Set<String> destinoUnion = new HashSet<>();
                
                for (String subEstado : actualSet) {
                    if (afn.get(subEstado).containsKey(simbolo)) {
                        destinoUnion.addAll(afn.get(subEstado).get(simbolo));
                    }
                }

                if (!destinoUnion.isEmpty()) {
                    tablaAFD.computeIfAbsent(actualSet, k -> new HashMap<>())
                            .put(simbolo, destinoUnion);
                    
                    if (!visitados.contains(destinoUnion)) {
                        visitados.add(destinoUnion);
                        cola.add(destinoUnion);
                    }
                }
            }
        }
    }

    public boolean validarCadena(String cadena) {
        Set<String> estadoActual = estadoInicialAFD;
        String input = cadena.toUpperCase();

        for (char c : input.toCharArray()) {
            if (tablaAFD.containsKey(estadoActual) && tablaAFD.get(estadoActual).containsKey(c)) {
                estadoActual = tablaAFD.get(estadoActual).get(c);
            } else {
                return false; // Transición no válida
            }
        }
        return estadosFinalesAFD.contains(estadoActual);
    }
}