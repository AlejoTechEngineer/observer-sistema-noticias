package com.unir.noticias;

import com.unir.noticias.observador.CanalEmail;
import com.unir.noticias.observador.CanalTV;
import com.unir.noticias.observador.CanalWeb;
import com.unir.noticias.observador.NotificacionApp;
import com.unir.noticias.observador.Observador;
import com.unir.noticias.sujeto.Reportero;

import java.util.List;
import java.util.Scanner;

/**
 * Simulacion del sistema de noticias con el patron Observer.
 *  - Sin argumentos  : modo interactivo (entrada por teclado).
 *  - Argumento --demo: escenario de ejemplo del enunciado.
 */
public class Main {

    public static void main(String[] args) {
        // 3 observadores concretos (tipos de salida)
        Observador web = new CanalWeb();
        Observador tv = new CanalTV();
        Observador app = new NotificacionApp();
        Observador email = new CanalEmail();          // EVOLUCION: nuevo canal

        // 4 sujetos concretos (reporteros)
        Reportero r1 = new Reportero("Reportero 1");
        Reportero r2 = new Reportero("Reportero 2");
        Reportero r3 = new Reportero("Reportero 3");
        Reportero r4 = new Reportero("Reportero 4");

        // Suscripciones: cada canal decide que reporteros le interesan
        for (Observador o : List.of(web, tv, app)) r1.registrar(o); // R1 -> Web, TV, App
        r2.registrar(tv);  r2.registrar(app);                       // R2 -> TV, App
        r3.registrar(web);                                          // R3 -> Web
        r4.registrar(web); r4.registrar(tv);                        // R4 -> Web, TV
        r1.registrar(email); r4.registrar(email);                   // EVOLUCION: R1 y R4 -> Email

        List<Reportero> reporteros = List.of(r1, r2, r3, r4);

        if (args.length > 0 && args[0].equals("--demo")) {
            demo(r1, r2, r3, r4);
        } else {
            interactivo(reporteros);
        }
    }

    private static void demo(Reportero r1, Reportero r2, Reportero r3, Reportero r4) {
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

    private static void interactivo(List<Reportero> reporteros) {
        Scanner in = new Scanner(System.in);
        System.out.println("=== Sistema de noticias (Patron Observer) ===");
        for (int i = 0; i < reporteros.size(); i++) {
            Reportero r = reporteros.get(i);
            StringBuilder canales = new StringBuilder();
            for (Observador o : r.getObservadores()) canales.append(o.getNombre()).append(' ');
            System.out.println((i + 1) + ") " + r.getNombre() + "  [suscritos: " + canales.toString().trim() + "]");
        }
        System.out.println("Escriba: <n reportero> <titular>   (ej. 1 La bolsa ha caído)   |   'salir' para terminar");

        while (true) {
            System.out.print("> ");
            if (!in.hasNextLine()) break;
            String linea = in.nextLine().trim();
            if (linea.equalsIgnoreCase("salir")) break;
            if (linea.isEmpty()) continue;

            String[] partes = linea.split("\\s+", 2);
            try {
                int idx = Integer.parseInt(partes[0]) - 1;
                if (partes.length < 2 || idx < 0 || idx >= reporteros.size()) throw new IllegalArgumentException();
                emitir(reporteros.get(idx), partes[1]);
            } catch (IllegalArgumentException e) {
                System.out.println("Entrada no valida. Formato: <1-4> <titular>");
            }
        }
        System.out.println("Fin de la simulacion.");
    }
}
