package com.unir.noticias.sinpatron;

/** Reportero 2: conoce y llama directamente a las salidas concretas (acoplamiento fuerte). */
public class Reportero2 {
    private final TVChannel tv = new TVChannel();
    private final AppMovil app = new AppMovil();

    public void publicarNoticia(String titular) {
        System.out.println("Esperando noticia: Reportero 2 - " + titular);
        tv.emitir(titular);
        app.notificar(titular);
        System.out.println();
    }
}
