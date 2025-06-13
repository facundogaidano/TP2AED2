package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BloqueTests {

  @Test
  void testEliminarMayorValor() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 200),
      new Transaccion(3, 3, 4, 300)
    };
    Bloque bloque = new Bloque(1, transacciones);
    Transaccion mayorValor = bloque.eliminarMayorValor();
    
    assertEquals(300, mayorValor.monto(), "El monto de la transacción eliminada debería ser 300");
    assertEquals(2, bloque.getTransacciones().length, "Deberían quedar 2 transacciones en el bloque");
    assertEquals(-1, mayorValor.getIndexEnBloque(), "El índice de la transacción eliminada debe ser -1");
  }

  @Test
  void testGetTransacciones() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 200)
    };
    Bloque bloque = new Bloque(1, transacciones);
    Transaccion[] result = bloque.getTransacciones();
    
    assertEquals(2, result.length, "Debería haber 2 transacciones en el bloque");
    assertEquals(1, result[0].id(), "La primera transacción debería tener ID 1");
    assertEquals(2, result[1].id(), "La segunda transacción debería tener ID 2");
  }

  @Test
  void testMontoMedio() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 200),
      new Transaccion(3, 3, 4, 300)
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    assertEquals(200, bloque.montoMedio(), "El monto medio debería ser 200");
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
    
    assertEquals(300, mayorValor.monto(), "La transacción de mayor valor debería tener monto 300");
    assertEquals(3, mayorValor.id(), "La transacción de mayor valor debería tener ID 3");
  }

  @Test
  void testBloqueVacio() {
    Transaccion[] transacciones = {};
    Bloque bloque = new Bloque(1, transacciones);
    
    assertEquals(0, bloque.getTransacciones().length, "Bloque vacío debe tener 0 transacciones");
    assertNull(bloque.transaccionMayorValor(), "Bloque vacío debe devolver null al consultar mayor valor");
    assertNull(bloque.eliminarMayorValor(), "Bloque vacío debe devolver null al eliminar mayor valor");
    assertEquals(0, bloque.montoMedio(), "Bloque vacío debe tener monto medio 0");
  }

  @Test
  void testBloqueUnaTransaccion() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100)
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    assertEquals(1, bloque.getTransacciones().length, "Debe tener exactamente 1 transacción");
    assertEquals(100, bloque.montoMedio(), "Monto medio debe ser igual al único monto");
    
    Transaccion tx = bloque.eliminarMayorValor();
    assertEquals(100, tx.monto(), "Debe eliminar la única transacción");
    assertEquals(0, bloque.getTransacciones().length, "Debe quedar vacío tras eliminar");
  }

  @Test
  void testTransaccionesIguales() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 100),
      new Transaccion(3, 3, 4, 100)
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    // Con montos iguales, el heap prioriza por ID (el mayor primero)
    Transaccion mayor = bloque.transaccionMayorValor();
    assertEquals(3, mayor.id(), "Con montos iguales, debe seleccionar el de mayor ID");
    
    Transaccion eliminada = bloque.eliminarMayorValor();
    assertEquals(3, eliminada.id(), "Debe eliminar la de mayor ID");
    assertEquals(2, bloque.getTransacciones().length, "Deben quedar 2 transacciones");
  }

  @Test
  void testTransaccionesCreacion() {
    Transaccion[] transacciones = {
      new Transaccion(1, 0, 2, 100),  // Creación
      new Transaccion(2, 0, 3, 200),  // Creación
      new Transaccion(3, 1, 4, 50)    // Normal
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    assertEquals(50, bloque.montoMedio(), "Monto medio solo considera transacciones no-creación");
    
    // Las de creación también participan en el heap por monto
    Transaccion mayor = bloque.transaccionMayorValor();
    assertEquals(200, mayor.monto(), "La de mayor monto es de creación");
    assertEquals(0, mayor.id_comprador(), "Confirmar que es transacción de creación");
  }

  @Test
  void testMultiplesEliminaciones() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 300),
      new Transaccion(3, 3, 4, 200),
      new Transaccion(4, 4, 5, 400)
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    // Primera eliminación
    Transaccion primera = bloque.eliminarMayorValor();
    assertEquals(400, primera.monto(), "Debe eliminar la de monto 400");
    
    // Segunda eliminación
    Transaccion segunda = bloque.eliminarMayorValor();
    assertEquals(300, segunda.monto(), "Debe eliminar la de monto 300");
    
    // Verificar que el orden se mantiene
    Transaccion[] restantes = bloque.getTransacciones();
    assertEquals(2, restantes.length, "Deben quedar 2 transacciones");
    assertEquals(1, restantes[0].id(), "La primera debe ser ID 1");
    assertEquals(3, restantes[1].id(), "La segunda debe ser ID 3");
  }

  @Test
  void testIndicesActualizados() {
    Transaccion[] transacciones = {
      new Transaccion(1, 1, 2, 100),
      new Transaccion(2, 2, 3, 300),
      new Transaccion(3, 3, 4, 200)
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    // Eliminar transacción del medio
    bloque.eliminarMayorValor(); // Elimina ID 2 con monto 300
    
    // Verificar índices actualizados
    Transaccion[] restantes = bloque.getTransacciones();
    assertEquals(0, restantes[0].getIndexEnBloque(), "Primera transacción debe tener índice 0");
    assertEquals(1, restantes[1].getIndexEnBloque(), "Segunda transacción debe tener índice 1");
  }

  @Test
  void testEliminarMayorValorEsOlogN() {
    int[] casos = {1000, 10000, 100000};
    long[] tiempos = new long[casos.length];
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Transaccion[] transacciones = new Transaccion[n];
      for (int i = 0; i < n; i++) {
        transacciones[i] = new Transaccion(i + 1, 1, 2, i + 1);
      }
      Bloque bloque = new Bloque(1, transacciones);
      
      long start = System.nanoTime();
      bloque.eliminarMayorValor();
      long end = System.nanoTime();
      
      tiempos[idx] = end - start;
      System.out.printf("eliminarMayorValor() con %d transacciones -> %d ns%n", n, tiempos[idx]);
    }
    
    // Verificar que los ratios de crecimiento son sublineales (aproximadamente log)
    if (casos.length > 1) {
      // Si el ratio es menor que el ratio de tamaños, es sublineal
      double ratio1 = (double)tiempos[1] / tiempos[0];
      double ratioTamano1 = (double)casos[1] / casos[0];
      assertTrue(ratio1 < ratioTamano1, "La complejidad debe ser sublineal");
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
      long start = System.nanoTime();
      Transaccion[] arrayTrx = bloque.getTransacciones();
      long end = System.nanoTime();
      System.out.println("getTransacciones() con " + n + " transacciones -> " + (end - start) + "ms");
      assert arrayTrx.length == n : "La cantidad de transacciones devueltas debería ser " + n;
    }
  }
}
