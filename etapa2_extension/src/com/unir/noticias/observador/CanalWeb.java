package com.unir.noticias.observador;

import com.unir.noticias.modelo.Noticia;

/** Observador concreto 1: publica la noticia en la pagina web. */
public class CanalWeb implements Observador {

    @Override
    public void actualizar(Noticia noticia) {
        System.out.println("Web - " + noticia.titular());
    }

    @Override
    public String getNombre() {
        return "Web";
    }
}
