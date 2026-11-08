package com.unir.noticias;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** Alternativa: el JDK ya trae un mecanismo Observer (java.beans.PropertyChangeSupport). */
public class Reportero {
    private final String nombre;
    private final PropertyChangeSupport soporte = new PropertyChangeSupport(this);

    public Reportero(String nombre) { this.nombre = nombre; }

    public void suscribir(PropertyChangeListener oyente) { soporte.addPropertyChangeListener(oyente); }
    public void baja(PropertyChangeListener oyente)      { soporte.removePropertyChangeListener(oyente); }

    public void publicarNoticia(String titular) {
        // oldValue = null evita que dos titulares iguales consecutivos se descarten como "sin cambio"
        soporte.firePropertyChange(new PropertyChangeEvent(this, "noticia", null, titular));
    }

    public String getNombre() { return nombre; }
}
