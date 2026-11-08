package com.unir.noticias;

import com.unir.noticias.modelo.Noticia;
import com.unir.noticias.observador.*;
import com.unir.noticias.sujeto.Reportero;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/** Suite minima de pruebas sin dependencias externas (no requiere JUnit). */
public class PruebasObserver {

    static int total = 0, ok = 0;
    static final List<String> fallos = new ArrayList<>();

    /** Ejecuta la accion capturando System.out y devuelve las lineas impresas. */
    static List<String> capturar(Runnable accion) {
        PrintStream original = System.out;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buf, true));
        try { accion.run(); } finally { System.setOut(original); }
        String s = buf.toString().trim();
        return s.isEmpty() ? List.of() : Arrays.asList(s.split("\\R"));
    }

    static void prueba(String nombre, Supplier<Boolean> cond) {
        total++;
        boolean pasa;
        try { pasa = cond.get(); } catch (Throwable t) { pasa = false; }
        if (pasa) { ok++; System.out.println("  [OK]    " + nombre); }
        else { fallos.add(nombre); System.out.println("  [FALLO] " + nombre); }
    }

    public static void main(String[] args) throws Exception {
        String raiz = args.length > 0 ? args[0] : "src";
        System.out.println("=== Pruebas del patron Observer ===");

        prueba("T01 R1 notifica a Web, TV y App en orden de suscripcion", () -> {
            Reportero r = new Reportero("R");
            r.registrar(new CanalWeb()); r.registrar(new CanalTV()); r.registrar(new NotificacionApp());
            return capturar(() -> r.publicarNoticia("x")).equals(List.of("Web - x", "TV - x", "App - x"));
        });
        prueba("T02 Un reportero solo notifica a sus suscritos (solo Web)", () -> {
            Reportero r = new Reportero("R"); r.registrar(new CanalWeb());
            return capturar(() -> r.publicarNoticia("x")).equals(List.of("Web - x"));
        });
        prueba("T03 Tras eliminar() un canal deja de recibir noticias", () -> {
            Reportero r = new Reportero("R"); Observador tv = new CanalTV();
            r.registrar(new CanalWeb()); r.registrar(tv); r.eliminar(tv);
            return capturar(() -> r.publicarNoticia("x")).equals(List.of("Web - x"));
        });
        prueba("T04 Registrar dos veces el mismo observador no duplica la notificacion", () -> {
            Reportero r = new Reportero("R"); Observador w = new CanalWeb();
            r.registrar(w); r.registrar(w);
            return capturar(() -> r.publicarNoticia("x")).size() == 1;
        });
        prueba("T05 Registrar null se ignora sin lanzar excepcion", () -> {
            Reportero r = new Reportero("R"); r.registrar(null);
            return r.getObservadores().isEmpty();
        });
        prueba("T06 Sin observadores, publicar no falla ni imprime", () -> {
            Reportero r = new Reportero("R");
            return capturar(() -> r.publicarNoticia("x")).isEmpty();
        });
        prueba("T07 Un mismo canal observa a varios reporteros", () -> {
            Observador w = new CanalWeb(); Reportero a = new Reportero("A"), b = new Reportero("B");
            a.registrar(w); b.registrar(w);
            return capturar(() -> { a.publicarNoticia("1"); b.publicarNoticia("2"); }).equals(List.of("Web - 1", "Web - 2"));
        });
        prueba("T08 Baja de un observador durante la notificacion no rompe la iteracion", () -> {
            Reportero r = new Reportero("R");
            Observador autoBaja = new Observador() {
                public void actualizar(Noticia n) { r.eliminar(this); }
                public String getNombre() { return "AutoBaja"; }
            };
            r.registrar(autoBaja); r.registrar(new CanalWeb());
            return capturar(() -> r.publicarNoticia("x")).equals(List.of("Web - x"));
        });
        prueba("T09 Un observador que falla no impide notificar a los demas", () -> {
            Reportero r = new Reportero("R");
            Observador roto = new Observador() {
                public void actualizar(Noticia n) { throw new IllegalStateException("caido"); }
                public String getNombre() { return "Roto"; }
            };
            r.registrar(roto); r.registrar(new CanalWeb());
            PrintStream err = System.err; System.setErr(new PrintStream(new ByteArrayOutputStream()));
            try { return capturar(() -> r.publicarNoticia("x")).equals(List.of("Web - x")); }
            finally { System.setErr(err); }
        });
        prueba("T10 Extensibilidad: un canal nuevo recibe noticias sin tocar Reportero", () -> {
            Reportero r = new Reportero("R"); r.registrar(new CanalEmail());
            return capturar(() -> r.publicarNoticia("x")).equals(List.of("Email - x"));
        });
        prueba("T11 La Noticia es inmutable y conserva reportero y titular", () -> {
            Noticia n = new Noticia("R1", "t");
            return n.reportero().equals("R1") && n.titular().equals("t") && n.toString().equals("R1 - t");
        });
        prueba("T12 ARQUITECTURA: el paquete sujeto no depende de canales concretos", () -> {
            try (Stream<Path> fs = Files.walk(Paths.get(raiz, "com/unir/noticias/sujeto"))) {
                for (Path p : (Iterable<Path>) fs.filter(f -> f.toString().endsWith(".java"))::iterator) {
                    String src = Files.readString(p);
                    for (String c : List.of("CanalWeb", "CanalTV", "NotificacionApp", "CanalEmail"))
                        if (src.contains(c)) return false;
                }
                return true;
            } catch (Exception e) { return false; }
        });

        System.out.println("\nResultado: " + ok + "/" + total + " pruebas superadas");
        if (!fallos.isEmpty()) { System.out.println("Fallos: " + fallos); System.exit(1); }
    }
}
