package com.unir.noticias.sinpatron;

/** Reportero 1: conoce y llama directamente a las salidas concretas (acoplamiento fuerte). */
public class Reportero1 {
    private final Web web = new Web();
    private final TVChannel tv = new TVChannel();
    private final AppMovil app = new AppMovil();

    public void publicarNoticia(String titular) {
        System.out.println("Esperando noticia: Reportero 1 - " + titular);
        web.publicar(titular);
        tv.emitir(titular);
        app.notificar(titular);
        System.out.println();
    }
}
