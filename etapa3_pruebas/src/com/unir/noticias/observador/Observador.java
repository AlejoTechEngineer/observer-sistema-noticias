package com.unir.noticias.observador;

import com.unir.noticias.modelo.Noticia;

/** Interfaz Observador: contrato que cumple cualquier canal que desee recibir noticias. */
public interface Observador {

    /** Metodo de actualizacion invocado por el sujeto cuando publica una noticia. */
    void actualizar(Noticia noticia);

    /** Nombre del canal (Web, TV, App); util para trazas y para la suscripcion. */
    String getNombre();
}
