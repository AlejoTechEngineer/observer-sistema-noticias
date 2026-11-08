package com.unir.noticias.observador;

import com.unir.noticias.modelo.Noticia;

/** Observador concreto 4 (evolucion): envia la noticia por correo electronico. */
public class CanalEmail implements Observador {

    @Override
    public void actualizar(Noticia noticia) {
        System.out.println("Email - " + noticia.titular());
    }

    @Override
    public String getNombre() {
        return "Email";
    }
}
