package com.unir.noticias.sujeto;

import com.unir.noticias.modelo.Noticia;
import com.unir.noticias.observador.Observador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sujeto concreto: cada reportero mantiene su propia lista de observadores
 * suscritos y, al publicar una noticia, los notifica a todos (difusion).
 */
public class Reportero implements Sujeto {

    private final String nombre;
    private final List<Observador> observadores = new ArrayList<>();
    private Noticia ultimaNoticia;

    public Reportero(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void registrar(Observador observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    @Override
    public void eliminar(Observador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificar() {
        // Copia defensiva: un observador podria darse de baja durante la notificacion.
        for (Observador o : new ArrayList<>(observadores)) {
            try {
                o.actualizar(ultimaNoticia);
            } catch (RuntimeException e) {
                // Aislamiento de fallos: un canal defectuoso no impide avisar a los demas
                System.err.println("[ERROR] " + o.getNombre() + " fallo: " + e.getMessage());
            }
        }
    }

    /** Cambio de estado del sujeto: nueva noticia -> dispara la notificacion. */
    public void publicarNoticia(String titular) {
        this.ultimaNoticia = new Noticia(nombre, titular);
        notificar();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Observador> getObservadores() {
        return Collections.unmodifiableList(observadores);
    }
}
