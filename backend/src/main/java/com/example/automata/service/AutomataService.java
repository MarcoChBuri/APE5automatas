package com.example.automata.service;

import org.springframework.stereotype.Service;

@Service
public class AutomataService {

    public boolean validarTabla1(String cadena) {
        if (cadena == null) return false;
        String estado = "q0";
        for (char c : cadena.toUpperCase().trim().toCharArray()) {
            estado = switch (estado) {
                case "q0" -> (c == 'K') ? "q1" : "error";
                case "q1" -> (c == 'G') ? "q2" : "error";
                case "q2" -> (c == 'X') ? "q2" : (c == 'F') ? "q4" : "error";
                case "q4" -> (c == 'F') ? "q4" : "error";
                default -> "error";
            };
            if (estado.equals("error")) return false;
        }
        return estado.equals("q4");
    }

    public boolean validarTabla2(String cadena) {
        if (cadena == null) return false;
        String estado = "q0";
        for (char c : cadena.toUpperCase().trim().toCharArray()) {
            estado = switch (estado) {
                case "q0" -> (c == 'H') ? "q1" : "error";
                case "q1" -> (c == 'S') ? "q1q2" : "error";
                case "q1q2" -> (c == 'S') ? "q1q2" : (c == 'C') ? "q3" : "error";
                case "q3" -> "error";
                default -> "error";
            };
            if (estado.equals("error")) return false;
        }
        return estado.equals("q3");
    }

    public boolean validarTabla3(String cadena) {
        if (cadena == null) return false;
        String estado = "q0";
        for (char c : cadena.toUpperCase().trim().toCharArray()) {
            estado = switch (estado) {
                case "q0" -> (c == 'S') ? "q1" : "error";
                case "q1" -> (c == 'A') ? "q1q2" : "error";
                case "q1q2" -> (c == 'A') ? "q1q2" : (c == 'R') ? "q3" : "error";
                case "q3" -> "error";
                default -> "error";
            };
            if (estado.equals("error")) return false;
        }
        return estado.equals("q3");
    }
}