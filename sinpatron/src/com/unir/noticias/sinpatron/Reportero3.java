package com.unir.noticias.sinpatron;

/** Reportero 3: conoce y llama directamente a las salidas concretas (acoplamiento fuerte). */
public class Reportero3 {
    private final Web web = new Web();

    public void publicarNoticia(String titular) {
        System.out.println("Esperando noticia: Reportero 3 - " + titular);
        web.publicar(titular);
        System.out.println();
    }
}
