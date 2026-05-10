package com.example.automata.service;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class Ejercicio1Service {

    // Representación del AFN: Mapa<Estado, Mapa<Simbolo, Set<Destinos>>>
    private final Map<String, Map<Character, Set<String>>> afn = new HashMap<>();
    private final String estadoInicialAFN = "q0";
    private final String estadoFinalAFN = "q4";

    // El AFD resultante: Mapa<NombreEstadoAFD, Mapa<Simbolo, NombreEstadoAFD>>
    private final Map<String, Map<Character, String>> afd = new HashMap<>();
    private final Set<String> estadosFinalesAFD = new HashSet<>();
    private String estadoInicialAFD;

    public Ejercicio1Service() {
        inicializarAFN();
        convertirAFNaAFD();
    }

    private void inicializarAFN() {
        afn.put("q0", Map.of('K', Set.of("q1")));
        afn.put("q1", Map.of('G', Set.of("q2")));
        afn.put("q2", Map.of('X', Set.of("q2", "q3"), 'F', Set.of("q4")));
        afn.put("q3", Map.of('F', Set.of("q4")));
        afn.put("q4", new HashMap<>());
    }

    private void convertirAFNaAFD() {
        // Cola para estados pendientes de procesar
        Queue<Set<String>> pendientes = new LinkedList<>();
        // Mapeo de Set de estados AFN a un nombre único (A, B, C...)
        Map<Set<String>, String> nombresEstados = new HashMap<>();
        char etiqueta = 'A';

        Set<String> inicial = Set.of(estadoInicialAFN);
        pendientes.add(inicial);
        nombresEstados.put(inicial, String.valueOf(etiqueta++));
        estadoInicialAFD = nombresEstados.get(inicial);

        while (!pendientes.isEmpty()) {
            Set<String> actualSet = pendientes.poll();
            String nombreActual = nombresEstados.get(actualSet);
            
            // Revisar si este nuevo estado de AFD es final
            if (actualSet.contains(estadoFinalAFN)) {
                estadosFinalesAFD.add(nombreActual);
            }

            // Para cada símbolo posible en el alfabeto {K, G, X, F}
            for (char simbolo : new char[]{'K', 'G', 'X', 'F'}) {
                Set<String> destinosUnion = new HashSet<>();
                
                // Aplicar el algoritmo de subconjuntos: unión de destinos
                for (String subEstado : actualSet) {
                    if (afn.containsKey(subEstado) && afn.get(subEstado).containsKey(simbolo)) {
                        destinosUnion.addAll(afn.get(subEstado).get(simbolo));
                    }
                }

                if (!destinosUnion.isEmpty()) {
                    if (!nombresEstados.containsKey(destinosUnion)) {
                        nombresEstados.put(destinosUnion, String.valueOf(etiqueta++));
                        pendientes.add(destinosUnion);
                    }
                    
                    afd.computeIfAbsent(nombreActual, k -> new HashMap<>())
                       .put(simbolo, nombresEstados.get(destinosUnion));
                }
            }
        }
    }

    public boolean validar(String cadena) {
        String actual = estadoInicialAFD;
        for (char c : cadena.toUpperCase().toCharArray()) {
            if (afd.containsKey(actual) && afd.get(actual).containsKey(c)) {
                actual = afd.get(actual).get(c);
            } else {
                return false;
            }
        }
        return estadosFinalesAFD.contains(actual);
    }
}