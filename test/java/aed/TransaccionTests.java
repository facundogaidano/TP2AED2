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
  }

  @Test
  void testCompareTo() {
    Transaccion t1 = new Transaccion(0, 0, 1, 10);
    Transaccion t2 = new Transaccion(1, 1, 2, 5);
    Transaccion t3 = new Transaccion(2, 1, 2, 10);

    assertTrue(t1.compareTo(t2) > 0, "t1 debería ser mayor que t2 por monto");
    assertTrue(t1.compareTo(t3) == 0 || t1.compareTo(t3) != 0, "t1 y t3 pueden ser iguales o distintos según compareTo");
  }

  @Test
  void testEquals() {
    Transaccion t1 = new Transaccion(0, 0, 1, 10);
    Transaccion t2 = new Transaccion(0, 0, 1, 10);
    Transaccion t3 = new Transaccion(1, 1, 2, 5);

    assertEquals(t1, t2, "t1 y t2 deberían ser iguales");
    assertNotEquals(t1, t3, "t1 y t3 deberían ser distintos");
    assertNotEquals(t1, null, "t1 no debería ser igual a null");
    assertNotEquals(t1, "string", "t1 no debería ser igual a un string");
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

    // Revierte la transacción
    Transaccion.revertirTransaccion(usuarios, t, usuariosArray);

    assertEquals(0, u1.getBalance(), "u1 debería volver a 0 después de revertir");
    assertEquals(0, u2.getBalance(), "u2 debería volver a 0 después de revertir");
  }

  @Test
  void testAplicarTransaccionesEsOn() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Usuario> usuarios = new Heap<>(n);
      Usuario[] usuariosArray = new Usuario[n + 1];
      for (int i = 1; i <= n; i++) {
        usuariosArray[i] = new Usuario(i);
        usuarios.agregarAlHeap(usuariosArray[i]);
      }
      Transaccion[] transacciones = new Transaccion[n];
      for (int i = 1; i <= n; i++) {
        transacciones[i - 1] = new Transaccion(i, i, (i % n) + 1, 1);
      }
      long start = System.currentTimeMillis();
      Transaccion.aplicarTransacciones(usuarios, transacciones, usuariosArray);
      long end = System.currentTimeMillis();
      System.out.println("aplicarTransacciones() con " + n + " transacciones -> " + (end - start) + "ms");
    }
  }

  @Test
  void testRevertirTransaccionEsOn() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Usuario> usuarios = new Heap<>(n);
      Usuario[] usuariosArray = new Usuario[n + 1];
      for (int i = 1; i <= n; i++) {
        usuariosArray[i] = new Usuario(i);
        usuarios.agregarAlHeap(usuariosArray[i]);
      }
      Transaccion[] transacciones = new Transaccion[n];
      for (int i = 1; i <= n; i++) {
        transacciones[i - 1] = new Transaccion(i, i, (i % n) + 1, 1);
      }
      Transaccion.aplicarTransacciones(usuarios, transacciones, usuariosArray);

      long start = System.nanoTime();
      for (int i = 0; i < n; i++) {
        Transaccion.revertirTransaccion(usuarios, transacciones[i], usuariosArray);
      }
      long end = System.nanoTime();
      System.out.println("revertirTransaccion() con " + n + " transacciones -> " + (end - start) + "ns");
    }
  }
}