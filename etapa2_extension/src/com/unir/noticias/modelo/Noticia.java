package com.unir.noticias.modelo;

/**
 * Noticia inmutable que viaja del sujeto (reportero) hacia los observadores.
 * Se usa un record (Java 16+) para evitar codigo repetitivo.
 */
public record Noticia(String reportero, String titular) {
    @Override
    public String toString() {
        return reportero + " - " + titular;
    }
}
