# Actividad No. 2 - Patrones de comportamiento: Sistema de noticias (Observer)
Requiere JDK 16+ (probado con JDK 21). Cada etapa es independiente:
  cd etapa1_base && javac -encoding UTF-8 -d out $(find src -name "*.java")
  java -cp out com.unir.noticias.Main            # interactivo
  java -cp out com.unir.noticias.Main --demo     # escenario de ejemplo
  cd ../etapa3_pruebas && javac -encoding UTF-8 -d out $(find src test -name "*.java")
  java -cp out com.unir.noticias.PruebasObserver src   # 12 pruebas
