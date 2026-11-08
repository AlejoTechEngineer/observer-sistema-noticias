package com.unir.noticias.observador;

import com.unir.noticias.modelo.Noticia;

/** Observador concreto 2: emite la noticia en el canal de television. */
public class CanalTV implements Observador {

    @Override
    public void actualizar(Noticia noticia) {
        System.out.println("TV - " + noticia.titular());
    }

    @Override
    public String getNombre() {
        return "TV";
    }
}
