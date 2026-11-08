package com.unir.noticias;

import java.beans.PropertyChangeListener;

public class Main {
    public static void main(String[] args) {
        // Los canales son simples lambdas (PropertyChangeListener es una interfaz funcional)
        PropertyChangeListener web = e -> System.out.println("Web - " + e.getNewValue());
        PropertyChangeListener tv  = e -> System.out.println("TV - " + e.getNewValue());
        PropertyChangeListener app = e -> System.out.println("App - " + e.getNewValue());

        Reportero r1 = new Reportero("Reportero 1"), r2 = new Reportero("Reportero 2");
        Reportero r3 = new Reportero("Reportero 3"), r4 = new Reportero("Reportero 4");
        r1.suscribir(web); r1.suscribir(tv); r1.suscribir(app);
        r2.suscribir(tv);  r2.suscribir(app);
        r3.suscribir(web);
        r4.suscribir(web); r4.suscribir(tv);

        emitir(r1, "La bolsa ha caído");
        emitir(r3, "El Real Madrid de nuevo gana la Champions");
        emitir(r2, "Nueva actualización de la app disponible");
        emitir(r4, "Lluvias intensas en Bogotá esta semana");
    }

    private static void emitir(Reportero r, String titular) {
        System.out.println("Esperando noticia: " + r.getNombre() + " - " + titular);
        r.publicarNoticia(titular);
        System.out.println();
    }
}
