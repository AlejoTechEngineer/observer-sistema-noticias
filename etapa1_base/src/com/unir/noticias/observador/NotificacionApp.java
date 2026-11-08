package com.unir.noticias.observador;

import com.unir.noticias.modelo.Noticia;

/** Observador concreto 3: envia una notificacion push a la app movil. */
public class NotificacionApp implements Observador {

    @Override
    public void actualizar(Noticia noticia) {
        System.out.println("App - " + noticia.titular());
    }

    @Override
    public String getNombre() {
        return "App";
    }
}
