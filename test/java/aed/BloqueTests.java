package aed;

import org.junit.jupiter.api.Test;

public class BloqueTests {
  @Test
  void testCantidad() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 200),
      new Transaccion(3, 3, 4, 300)
    };
    Bloque bloque = new Bloque(1, transacciones);
    assert bloque.cantidad() == 3 : "La cantidad de transacciones debería ser 3";
  }

  @Test
  void testEliminarMayorValor() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 200),
      new Transaccion(3, 3, 4, 300)
    };
    Bloque bloque = new Bloque(1, transacciones);
    Transaccion mayorValor = bloque.eliminarMayorValor();
    assert mayorValor.monto() == 300 : "El monto de la transacción eliminada debería ser 300";
    assert bloque.cantidad() == 2 : "La cantidad de transacciones después de eliminar debería ser 2";
  }

  @Test
  void testGetIdBloque() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 200)
    };
    Bloque bloque = new Bloque(5, transacciones);
    assert bloque.getIdBloque() == 5 : "El ID del bloque debería ser 5";
  }

  @Test
  void testGetTransacciones() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 200)
    };
    Bloque bloque = new Bloque(1, transacciones);
    Transaccion[] result = bloque.getTransacciones();
    assert result.length == 2 : "Debería haber 2 transacciones en el bloque";
    assert result[0].id() == 1 : "La primera transacción debería tener ID 1";
    assert result[1].id() == 2 : "La segunda transacción debería tener ID 2";
  }

  @Test
  void testMontoMedio() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 200),
      new Transaccion(3, 3, 4, 300)
    };
    Bloque bloque = new Bloque(1, transacciones);
    int montoMedio = bloque.montoMedio();
    assert montoMedio == 200 : "El monto medio debería ser 200";
  }

  @Test
  void testTransaccionMayorValor() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 200),
      new Transaccion(3, 3, 4, 300)
    };
    Bloque bloque = new Bloque(1, transacciones);
    Transaccion mayorValor = bloque.transaccionMayorValor();
    assert mayorValor.monto() == 300 : "La transacción de mayor valor debería tener monto 300";
  }

  @Test
  void testConstruccionBloqueEsOn() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Transaccion[] transacciones = new Transaccion[n];
      for (int i = 0; i < n; i++) {
        transacciones[i] = new Transaccion(i + 1, 1, 2, i + 1);
      }
      long start = System.currentTimeMillis();
      Bloque bloque = new Bloque(1, transacciones);
      long end = System.currentTimeMillis();
      System.out.println("Construcción de Bloque con " + n + " transacciones -> " + (end - start) + "ms");
      assert bloque.cantidad() == n : "La cantidad de transacciones debería ser " + n;
    }
  }

  @Test
  void testEliminarMayorValorEsOlogN() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Transaccion[] transacciones = new Transaccion[n];
      for (int i = 0; i < n; i++) {
        transacciones[i] = new Transaccion(i + 1, 1, 2, i + 1);
      }
      Bloque bloque = new Bloque(1, transacciones);
      long start = System.currentTimeMillis();
      bloque.eliminarMayorValor();
      long end = System.currentTimeMillis();
      System.out.println("eliminarMayorValor() con " + n + " transacciones -> " + (end - start) + "ms");
    }
  }

  @Test
  void testGetTransaccionesEsOn() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Transaccion[] transacciones = new Transaccion[n];
      for (int i = 0; i < n; i++) {
        transacciones[i] = new Transaccion(i + 1, 1, 2, i + 1);
      }
      Bloque bloque = new Bloque(1, transacciones);
      long start = System.currentTimeMillis();
      Transaccion[] arrayTrx = bloque.getTransacciones();
      long end = System.currentTimeMillis();
      System.out.println("getTransacciones() con " + n + " transacciones -> " + (end - start) + "ms");
      assert arrayTrx.length == n : "La cantidad de transacciones devueltas debería ser " + n;
    }
  }
}
