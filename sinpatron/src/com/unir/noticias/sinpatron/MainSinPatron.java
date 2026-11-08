package com.unir.noticias.sinpatron;

public class MainSinPatron {
    public static void main(String[] args) {
        new Reportero1().publicarNoticia("La bolsa ha caído");
        new Reportero3().publicarNoticia("El Real Madrid de nuevo gana la Champions");
        new Reportero2().publicarNoticia("Nueva actualización de la app disponible");
        new Reportero4().publicarNoticia("Lluvias intensas en Bogotá esta semana");
    }
}
