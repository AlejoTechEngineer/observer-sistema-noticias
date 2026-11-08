package com.unir.noticias.sujeto;

import com.unir.noticias.observador.Observador;

/** Interfaz Sujeto: gestiona la lista de observadores y la notificacion. */
public interface Sujeto {
    void registrar(Observador observador);
    void eliminar(Observador observador);
    void notificar();
}
