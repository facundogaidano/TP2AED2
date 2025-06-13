package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TransaccionTests {

  @Test
  void testAplicarTransacciones() {
    Heap<Usuario> usuarios = new Heap<>(3);
    Usuario[] usuariosArray = new Usuario[3];
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    usuarios.agregarAlHeap(u1);
    usuarios.agregarAlHeap(u2);
    usuariosArray[1] = u1;
    usuariosArray[2] = u2;

    Transaccion[] transacciones = {
      new Transaccion(0, 0, 1, 10), // creación: suma 10 a u1
      new Transaccion(1, 1, 2, 5)   // u1 paga 5 a u2
    };

    Transaccion.aplicarTransacciones(usuarios, transacciones, usuariosArray);

    assertEquals(5, u1.getBalance(), "u1 debería tener 5");
    assertEquals(5, u2.getBalance(), "u2 debería tener 5");
    
    // Verificar que el heap mantiene el orden correcto
    assertEquals(u1, usuarios.getMaximo(), "u1 y u2 tienen el mismo balance, pero u1 tiene menor ID");
  }
  
  @Test
  void testAplicarTransaccionesRepetidas() {
    Heap<Usuario> usuarios = new Heap<>(3);
    Usuario[] usuariosArray = new Usuario[3];
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    usuarios.agregarAlHeap(u1);
    usuarios.agregarAlHeap(u2);
    usuariosArray[1] = u1;
    usuariosArray[2] = u2;

    // Múltiples transacciones entre los mismos usuarios
    Transaccion[] transacciones = {
      new Transaccion(0, 0, 1, 100),  // creación: suma 100 a u1
      new Transaccion(1, 1, 2, 30),   // u1 paga 30 a u2
      new Transaccion(2, 1, 2, 20),   // u1 paga 20 a u2
      new Transaccion(3, 2, 1, 15)    // u2 paga 15 a u1
    };

    Transaccion.aplicarTransacciones(usuarios, transacciones, usuariosArray);

    assertEquals(65, u1.getBalance(), "u1 debería tener 65 (100-30-20+15)");
    assertEquals(35, u2.getBalance(), "u2 debería tener 35 (30+20-15)");
  }

  @Test
  void testAplicarTransaccionesVacio() {
    Heap<Usuario> usuarios = new Heap<>(3);
    Usuario[] usuariosArray = new Usuario[3];
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    usuarios.agregarAlHeap(u1);
    usuarios.agregarAlHeap(u2);
    usuariosArray[1] = u1;
    usuariosArray[2] = u2;

    Transaccion[] transacciones = {};

    Transaccion.aplicarTransacciones(usuarios, transacciones, usuariosArray);

    assertEquals(0, u1.getBalance(), "u1 no debería cambiar");
    assertEquals(0, u2.getBalance(), "u2 no debería cambiar");
  }
  
  @Test
  void testAplicarTransaccionesCreacion() {
    Heap<Usuario> usuarios = new Heap<>(4);
    Usuario[] usuariosArray = new Usuario[4];
    for (int i = 1; i <= 3; i++) {
      Usuario u = new Usuario(i);
      usuarios.agregarAlHeap(u);
      usuariosArray[i] = u;
    }

    Transaccion[] transacciones = {
      new Transaccion(0, 0, 1, 10),  // creación para u1
      new Transaccion(1, 0, 2, 20),  // creación para u2
      new Transaccion(2, 0, 3, 30)   // creación para u3
    };

    Transaccion.aplicarTransacciones(usuarios, transacciones, usuariosArray);

    assertEquals(10, usuariosArray[1].getBalance(), "u1 debería tener 10");
    assertEquals(20, usuariosArray[2].getBalance(), "u2 debería tener 20");
    assertEquals(30, usuariosArray[3].getBalance(), "u3 debería tener 30");
    assertEquals(usuariosArray[3], usuarios.getMaximo(), "u3 debería ser el máximo");
  }

  @Test
  void testCompareTo() {
    Transaccion t1 = new Transaccion(0, 0, 1, 10);
    Transaccion t2 = new Transaccion(1, 1, 2, 5);
    Transaccion t3 = new Transaccion(2, 1, 2, 10);
    Transaccion t4 = new Transaccion(3, 1, 2, 10);

    assertTrue(t1.compareTo(t2) > 0, "t1 debería ser mayor que t2 por monto");
    assertTrue(t3.compareTo(t4) < 0, "t3 debería ser menor que t4 con mismo monto pero menor ID");
    
    // Prueba reflexiva
    assertEquals(0, t1.compareTo(t1), "Una transacción debería ser igual a sí misma");
    
    // Prueba simétrica
    assertTrue(t1.compareTo(t2) > 0 && t2.compareTo(t1) < 0, 
              "Si t1 > t2 entonces t2 < t1");
    
    // Prueba transitiva
    Transaccion t5 = new Transaccion(5, 1, 2, 15);
    assertTrue(t5.compareTo(t1) > 0 && t1.compareTo(t2) > 0 && t5.compareTo(t2) > 0,
              "Si t5 > t1 y t1 > t2 entonces t5 > t2");
  }

  @Test
  void testEquals() {
    Transaccion t1 = new Transaccion(0, 0, 1, 10);
    Transaccion t2 = new Transaccion(0, 0, 1, 10);
    Transaccion t3 = new Transaccion(1, 1, 2, 5);
    Transaccion t4 = new Transaccion(0, 0, 2, 10); // Mismo ID pero diferente vendedor
    Transaccion t5 = new Transaccion(0, 1, 1, 10); // Mismo ID pero diferente comprador
    Transaccion t6 = new Transaccion(0, 0, 1, 15); // Mismo ID pero diferente monto

    assertEquals(t1, t2, "t1 y t2 deberían ser iguales (mismos atributos)");
    assertNotEquals(t1, t3, "t1 y t3 deberían ser distintos (todos atributos diferentes)");
    assertNotEquals(t1, t4, "t1 y t4 deberían ser distintos (diferente vendedor)");
    assertNotEquals(t1, t5, "t1 y t5 deberían ser distintos (diferente comprador)");
    assertNotEquals(t1, t6, "t1 y t6 deberían ser distintos (diferente monto)");
    assertNotEquals(t1, null, "t1 no debería ser igual a null");
    assertNotEquals(t1, "string", "t1 no debería ser igual a un string");
    
    // Reflexividad
    assertEquals(t1, t1, "Una transacción debe ser igual a sí misma");
    
    // Simetría
    assertEquals(t1.equals(t2), t2.equals(t1), "Si t1 equals t2 entonces t2 equals t1");
    
    // Transitividad
    Transaccion tCopy1 = new Transaccion(0, 0, 1, 10);
    Transaccion tCopy2 = new Transaccion(0, 0, 1, 10);
    assertTrue(t1.equals(tCopy1) && tCopy1.equals(tCopy2) && t1.equals(tCopy2),
              "Si t1 equals tCopy1 y tCopy1 equals tCopy2 entonces t1 equals tCopy2");
  }

  @Test
  void testRevertirTransaccion() {
    Heap<Usuario> usuarios = new Heap<>(2);
    Usuario[] usuariosArray = new Usuario[3];
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    usuarios.agregarAlHeap(u1);
    usuarios.agregarAlHeap(u2);
    usuariosArray[1] = u1;
    usuariosArray[2] = u2;

    Transaccion t = new Transaccion(1, 1, 2, 10);
    // Aplica la transacción
    Transaccion[] arr = {t};
    Transaccion.aplicarTransacciones(usuarios, arr, usuariosArray);

    assertEquals(-10, u1.getBalance(), "u1 debería tener -10 después de pagar");
    assertEquals(10, u2.getBalance(), "u2 debería tener 10 después de cobrar");
    assertEquals(u2, usuarios.getMaximo(), "u2 debería ser el máximo");

    // Revierte la transacción
    Transaccion.revertirTransaccion(usuarios, t, usuariosArray);

    assertEquals(0, u1.getBalance(), "u1 debería volver a 0 después de revertir");
    assertEquals(0, u2.getBalance(), "u2 debería volver a 0 después de revertir");
    
    // Verificar que el heap mantiene el orden correcto
    Usuario max = usuarios.getMaximo();
    assertTrue(max == u1 || max == u2, "Cualquiera puede ser máximo con mismo balance");
  }
  
  @Test
  void testRevertirTransaccionCreacion() {
    Heap<Usuario> usuarios = new Heap<>(2);
    Usuario[] usuariosArray = new Usuario[3];
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    usuarios.agregarAlHeap(u1);
    usuarios.agregarAlHeap(u2);
    usuariosArray[1] = u1;
    usuariosArray[2] = u2;

    Transaccion t = new Transaccion(1, 0, 2, 10); // Creación para u2
    // Aplica la transacción
    Transaccion[] arr = {t};
    Transaccion.aplicarTransacciones(usuarios, arr, usuariosArray);

    assertEquals(0, u1.getBalance(), "u1 no debería cambiar");
    assertEquals(10, u2.getBalance(), "u2 debería tener 10");
    assertEquals(u2, usuarios.getMaximo(), "u2 debería ser el máximo");

    // Revierte la transacción
    Transaccion.revertirTransaccion(usuarios, t, usuariosArray);

    assertEquals(0, u1.getBalance(), "u1 debería seguir en 0");
    assertEquals(0, u2.getBalance(), "u2 debería volver a 0 después de revertir");
  }
  
  @Test
  void testIndexesEnBloque() {
    Transaccion t = new Transaccion(1, 1, 2, 10);
    assertEquals(-1, t.getIndexEnBloque(), "Inicialmente el índice debe ser -1");
    
    t.setIndexEnBloque(5);
    assertEquals(5, t.getIndexEnBloque(), "El índice debe actualizarse a 5");
    
    t.setIndexEnBloque(0);
    assertEquals(0, t.getIndexEnBloque(), "El índice debe actualizarse a 0");
    
    t.setIndexEnBloque(-1);
    assertEquals(-1, t.getIndexEnBloque(), "El índice debe poder volver a -1");
  }
  
  @Test
  void testGetters() {
    Transaccion t = new Transaccion(42, 1, 2, 100);
    
    assertEquals(42, t.id(), "ID debe ser 42");
    assertEquals(1, t.id_comprador(), "ID de comprador debe ser 1");
    assertEquals(2, t.id_vendedor(), "ID de vendedor debe ser 2");
    assertEquals(100, t.monto(), "Monto debe ser 100");
  }

  @Test
  void testAplicarTransaccionesEsOn() {
    int[] casos = {1000, 10000, 100000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Usuario> usuarios = new Heap<>(n + 1);
      Usuario[] usuariosArray = new Usuario[n + 1];
      for (int i = 1; i <= n; i++) {
        usuariosArray[i] = new Usuario(i);
        usuarios.agregarAlHeap(usuariosArray[i]);
      }
      Transaccion[] transacciones = new Transaccion[n];
      for (int i = 0; i < n; i++) {
        transacciones[i] = new Transaccion(i, i % 10 + 1, ((i+1) % 10) + 1, i + 1);
      }
      long start = System.currentTimeMillis();
      Transaccion.aplicarTransacciones(usuarios, transacciones, usuariosArray);
      long end = System.currentTimeMillis();
      System.out.println("aplicarTransacciones() con " + n + " transacciones -> " + (end - start) + "ms");
    }
  }

  @Test
  void testRevertirTransaccionEsOLogP() {
    int[] casos = {10000};
    for (int idx = 0; idx < casos.length; idx++) {
      int[] usuariosSizes = {100, 1000, 10000, 100000};
      
      for (int p : usuariosSizes) {
        Heap<Usuario> usuarios = new Heap<>(p + 1);
        Usuario[] usuariosArray = new Usuario[p + 1];
        for (int i = 1; i <= p; i++) {
          usuariosArray[i] = new Usuario(i);
          usuarios.agregarAlHeap(usuariosArray[i]);
        }
        Transaccion t = new Transaccion(1, 1, 2, 100);
        Transaccion[] arr = {t};
        Transaccion.aplicarTransacciones(usuarios, arr, usuariosArray);
        
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) { // Hacer 1000 reversiones para medición más precisa
          Transaccion.revertirTransaccion(usuarios, t, usuariosArray);
          Transaccion.aplicarTransacciones(usuarios, arr, usuariosArray);
        }
        long end = System.nanoTime();
        System.out.printf("revertirTransaccion() con P=%d usuarios -> %.2f ns/op%n", 
                          p, (end - start) / 1000.0);
      }
    }
  }
  
  @Test
  void testUsuarioAfectadoMultiplesVeces() {
    Heap<Usuario> usuarios = new Heap<>(3);
    Usuario[] usuariosArray = new Usuario[3];
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    usuarios.agregarAlHeap(u1);
    usuarios.agregarAlHeap(u2);
    usuariosArray[1] = u1;
    usuariosArray[2] = u2;

    // Múltiples transacciones que afectan a los mismos usuarios
    Transaccion[] transacciones = {
      new Transaccion(0, 1, 2, 10),   // u1 paga 10 a u2
      new Transaccion(1, 1, 2, 20),   // u1 paga 20 a u2
      new Transaccion(2, 1, 2, 30),   // u1 paga 30 a u2
    };

    // Custom implementation para contar actualizaciones
    final int[] actualizacionesHeap = {0};
    
    // Crear un spy del heap para contar llamadas a actualizarPosicionUsuario
    Heap<Usuario> spyHeap = new Heap<Usuario>(3) {
      @Override
      public void actualizarPosicionUsuario(Usuario usuario) {
        actualizacionesHeap[0]++;
        super.actualizarPosicionUsuario(usuario);
      }
    };
    spyHeap.agregarAlHeap(u1);
    spyHeap.agregarAlHeap(u2);
    
    Transaccion.aplicarTransacciones(spyHeap, transacciones, usuariosArray);
    
    assertEquals(2, actualizacionesHeap[0], "Debe haber exactamente 2 actualizaciones (una por usuario)");
    assertEquals(-60, u1.getBalance(), "u1 debería tener -60");
    assertEquals(60, u2.getBalance(), "u2 debería tener 60");
  }
}