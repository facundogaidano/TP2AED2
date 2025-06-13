package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BloqueTests {

  @Test
  void testBloqueVacio() {
    // Caso borde: Bloque sin transacciones
    Transaccion[] transacciones = {};
    Bloque bloque = new Bloque(1, transacciones);
    
    assertEquals(0, bloque.getTransacciones().length, "Bloque vacío debe tener 0 transacciones");
    assertNull(bloque.transaccionMayorValor(), "Bloque vacío debe devolver null al consultar mayor valor");
    assertNull(bloque.eliminarMayorValor(), "Bloque vacío debe devolver null al eliminar mayor valor");
    assertEquals(0, bloque.montoMedio(), "Bloque vacío debe tener monto medio 0");
  }
  
  @Test
  void testBloqueUnicaTransaccion() {
    // Caso borde: Bloque con una única transacción
    Transaccion[] transacciones = {
      new Transaccion(0, 1, 2, 100)
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    assertEquals(1, bloque.getTransacciones().length, "Bloque debe contener 1 transacción");
    Transaccion tx = bloque.transaccionMayorValor();
    assertNotNull(tx, "Debe existir una transacción de mayor valor");
    assertEquals(100, tx.monto(), "El monto debe ser 100");
    
    Transaccion eliminada = bloque.eliminarMayorValor();
    assertEquals(100, eliminada.monto(), "La transacción eliminada debe tener monto 100");
    assertEquals(0, bloque.getTransacciones().length, "Bloque debe quedar vacío");
  }
  
  @Test
  void testTransaccionesConMismoMonto() {
    // Caso borde: Todas las transacciones tienen el mismo monto
    Transaccion[] transacciones = {
      new Transaccion(0, 1, 2, 100),
      new Transaccion(1, 2, 3, 100),
      new Transaccion(2, 3, 4, 100)
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    Transaccion mayor = bloque.transaccionMayorValor();
    assertEquals(2, mayor.id(), "Con montos iguales, debe seleccionar el de menor ID");
    
    Transaccion eliminada = bloque.eliminarMayorValor();
    assertEquals(2, eliminada.id(), "Primera eliminación debe ser ID 2");
    eliminada = bloque.eliminarMayorValor();
    assertEquals(1, eliminada.id(), "Segunda eliminación debe ser ID 1");
    eliminada = bloque.eliminarMayorValor();
    assertEquals(0, eliminada.id(), "Tercera eliminación debe ser ID 0");
  }
  
  @Test
  void testTransaccionesCreacion() {
    // Caso borde: Transacciones de creación (id_comprador=0)
    Transaccion[] transacciones = {
      new Transaccion(0, 0, 1, 150), // Transacción de creación
      new Transaccion(2, 3, 4, 100)  // Transacción normal
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    // El promedio solo considera transacciones normales
    assertEquals(100, bloque.montoMedio(), "Promedio solo debe considerar transacciones normales");
    
    // La transacción de mayor valor puede ser de creación
    Transaccion mayor = bloque.transaccionMayorValor();
    assertEquals(150, mayor.monto(), "Mayor valor debe ser 250");
    assertEquals(0, mayor.id_comprador(), "Debe ser transacción de creación");
    
    // Al eliminar una transacción de creación, no afecta el monto promedio
    bloque.eliminarMayorValor();
    assertEquals(100, bloque.montoMedio(), "Promedio debe mantenerse igual");
  }
  
  @Test
  void testMontosNegativos() {
    // Caso borde: Transacciones con montos negativos
    Transaccion[] transacciones = {
      new Transaccion(0, 1, 2, -50),
      new Transaccion(1, 2, 3, 100),
      new Transaccion(2, 3, 4, -100)
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    // El promedio debe considerar valores negativos
    assertEquals(-50/3, bloque.montoMedio(), "Promedio debe considerar valores negativos");
    
    // La transacción mayor debe ser la de valor positivo
    assertEquals(100, bloque.transaccionMayorValor().monto(), "Mayor valor debe ser positivo");
  }
  
  @Test
  void testBloqueSecuencial() {
    Transaccion[] transacciones = {
      new Transaccion(0, 1, 2, 150),
      new Transaccion(1, 2, 3, 250),
      new Transaccion(2, 3, 4, 350),
      new Transaccion(3, 4, 5, 200)
    };
    Bloque bloque = new Bloque(1, transacciones);
    
    assertEquals(4, bloque.getTransacciones().length, "Debe haber 4 transacciones");
    assertEquals(237, bloque.montoMedio(), "Monto medio debe ser (150+250+350+200)/4=237.5 ≈ 237");
    
    // Eliminar transacciones en orden de mayor a menor
    assertEquals(350, bloque.eliminarMayorValor().monto(), "Primera eliminación debe ser 350");
    assertEquals(250, bloque.eliminarMayorValor().monto(), "Segunda eliminación debe ser 250");
    assertEquals(200, bloque.eliminarMayorValor().monto(), "Tercera eliminación debe ser 200");
    assertEquals(150, bloque.eliminarMayorValor().monto(), "Cuarta eliminación debe ser 150");
    
    // Bloque ahora vacío
    assertNull(bloque.eliminarMayorValor(), "Bloque vacío debe devolver null");
  }
  
  @Test
  void testComplejidadGetTransacciones() {
    // Prueba de complejidad O(n) para getTransacciones()
    int[] tamanios = {1000, 10000, 100000};
    
    for (int i = 0; i < tamanios.length; i++) {
      int n = tamanios[i];
      Transaccion[] transacciones = new Transaccion[n];
      for (int j = 0; j < n; j++) {
        transacciones[j] = new Transaccion(j, 1, 2, 100);
      }
      
      Bloque bloque = new Bloque(1, transacciones);
      
      long inicio = System.nanoTime();
      Transaccion[] resultado = bloque.getTransacciones();
      long fin = System.nanoTime();
      
      System.out.println("getTransacciones() con " + n + " transacciones -> " + (fin - inicio) + "ns");
      assertEquals(n, resultado.length);
    }
  }
  
  @Test
  void testComplejidadMontoMedio() {
    // Prueba de complejidad O(1) para montoMedio()
    int[] tamanios = {1000, 10000, 100000};
    
    for (int i = 0; i < tamanios.length; i++) {
      int n = tamanios[i];
      Transaccion[] transacciones = new Transaccion[n];
      for (int j = 0; j < n; j++) {
        transacciones[j] = new Transaccion(j, 1, 2, 100);
      }
      
      Bloque bloque = new Bloque(1, transacciones);
      
      long inicio = System.nanoTime();
      for (int k = 0; k < 1000; k++) {
        bloque.montoMedio();
      }
      long fin = System.nanoTime();
      
      System.out.println("montoMedio() x1.000 con " + n + " transacciones -> " + ((fin - inicio) / 1000) + "ns");
      assertEquals(100, bloque.montoMedio(), "Monto medio debe ser 100");
    }
  }
  
  @Test
  void testComplejidadTransaccionMayorValor() {
    // Prueba de complejidad O(1) para transaccionMayorValor()
    int[] tamanios = {1000, 10000, 100000};
    
    for (int i = 0; i < tamanios.length; i++) {
      int n = tamanios[i];
      Transaccion[] transacciones = new Transaccion[n];
      for (int j = 0; j < n; j++) {
        transacciones[j] = new Transaccion(j, 1, 2, j);
      }
      
      Bloque bloque = new Bloque(1, transacciones);
      
      long inicio = System.nanoTime();
      for (int k = 0; k < 1000; k++) {
        bloque.transaccionMayorValor();
      }
      long fin = System.nanoTime();
      
      System.out.println("transaccionMayorValor() x1.000 con " + n + " transacciones -> " + ((fin - inicio) / 1000) + "ns");
      assertEquals(n-1, bloque.transaccionMayorValor().monto(), "Mayor valor debe ser n-1");
    }
  }
  
  @Test
  void testComplejidadEliminarMayorValor() {
    // Prueba de complejidad O(log n) para eliminarMayorValor()
    int[] tamanios = {1000, 10000, 100000};
    
    for (int i = 0; i < tamanios.length; i++) {
      int n = tamanios[i];
      Transaccion[] transacciones = new Transaccion[n];
      for (int j = 0; j < n; j++) {
        transacciones[j] = new Transaccion(j, 1, 2, n - j);
      }
      
      Bloque bloque = new Bloque(1, transacciones);
      
      long inicio = System.nanoTime();
      Transaccion maxima = bloque.eliminarMayorValor();
      long fin = System.nanoTime();
      
      System.out.println("eliminarMayorValor() con " + n + " transacciones -> " + (fin - inicio) + "ns");
      assertEquals(n, maxima.monto(), "Debe eliminar la de mayor monto");
    }
  }
  
  @Test
  void testConstruccionBloque() {
    // Verificar que la construcción del bloque es O(n)
    int[] tamanios = {1000, 10000, 100000};
    
    for (int i = 0; i < tamanios.length; i++) {
      int n = tamanios[i];
      Transaccion[] transacciones = new Transaccion[n];
      for (int j = 0; j < n; j++) {
        transacciones[j] = new Transaccion(j, 1, 2, 100);
      }
      
      long inicio = System.nanoTime();
      Bloque bloque = new Bloque(1, transacciones);
      long fin = System.nanoTime();
      
      System.out.println("Construcción de bloque con " + n + " transacciones -> " + (fin - inicio) + "ns");
      assertEquals(n, bloque.getTransacciones().length, "Debe contener todas las transacciones");
    }
  }
}