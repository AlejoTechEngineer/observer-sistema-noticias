package com.unir.noticias.sinpatron;

/** Reportero 4: conoce y llama directamente a las salidas concretas (acoplamiento fuerte). */
public class Reportero4 {
    private final Web web = new Web();
    private final TVChannel tv = new TVChannel();

    public void publicarNoticia(String titular) {
        System.out.println("Esperando noticia: Reportero 4 - " + titular);
        web.publicar(titular);
        tv.emitir(titular);
        System.out.println();
    }
}
