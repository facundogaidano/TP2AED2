package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class HeapTests {
  @Test
  void testHeapVacio() {
      Heap<Integer> heap = new Heap<>(10);
      assert heap.getLongitud() == 0 : "La longitud del heap vacío debería ser 0";
      try {
          heap.getMaximo();
          assert false : "Debería lanzar una excepción al obtener el máximo de un heap vacío";
      } catch (IllegalStateException e) {
          // Al ser un Heap vacío me va a dar error si no hago un try catch
      }
      try {
          heap.sacarMaximo();
          assert false : "Debería lanzar una excepción al sacar el máximo de un heap vacío";
      } catch (IllegalStateException e) {
          // Al ser un Heap vacío me va a dar error si no hago un try catch
      }
  }

  // Pruebas de complejidad y rendimiento.

  @Test
  void testAgregarAlHeapEsOLogN() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      long start = System.currentTimeMillis();
      for (int i = 0; i < n; i++) heap.agregarAlHeap(i);
      long end = System.currentTimeMillis();
      System.out.println("agregarAlHeap() " + n + " elementos ->  " + (end - start) + "ms");
    }
  }

  @Test
  void testSacarTodoEsONLogN() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      for (int i = 0; i < n; i++) heap.agregarAlHeap(i);
      long start = System.currentTimeMillis();
      for (int i = 0; i < n; i++) heap.sacarMaximo();
      long end = System.currentTimeMillis();
      System.out.println("sacarMaximo() " + n + " veces ->  " + (end - start) + "ms");
    }
  }

  @Test
  void testGetLongitudEsO1() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      for (int i = 0; i < n; i++) heap.agregarAlHeap(i);
      long start = System.nanoTime();
      int longitud = heap.getLongitud();
      for (int i = 0; i < 10000000; i++) {
        assert heap.getLongitud() == longitud : "La longitud del heap cambió inesperadamente";
      }
      long end = System.nanoTime();
      System.out.println("getLongitud() del heap con " + n + " elementos -> " + (end - start) + "ns");
      assert longitud == n : "La longitud del heap debería ser " + n;
    }
  }

  @Test
  void testGetMaximoEsO1() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      for (int i = 0; i < n; i++) heap.agregarAlHeap(i);
      int maxEsperado = heap.getMaximo();
      long start = System.nanoTime();
      for (int i = 0; i < 10000000; i++) {
        assert heap.getMaximo() == maxEsperado : "El máximo del heap cambió inesperadamente";
      }
      long end = System.nanoTime();
      System.out.println("getMaximo() del heap con " + n + " elementos -> " + (end - start) + "ns");
      assert maxEsperado == n - 1 : "El máximo del heap debería ser " + (n - 1);
    }
  }

  @Test
  void testEliminarPorHandleEsOLogN() { // eliminarPorHandle es O(log n) porque hace HeapifyUp o HeapifyDown - En este caso, siempre hace HeapifyDown porque eliminamos la raíz.
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      for (int i = 0; i < n; i++) heap.agregarAlHeap(i);
      long start = System.currentTimeMillis();
      for (int i = 0; i < n; i++) {
        Heap<Integer>.Handle handle = heap.new Handle(0);
        heap.eliminarPorHandle(handle);
      }
      long end = System.currentTimeMillis();
      System.out.println("eliminarPorHandle() " + n + " veces -> " + (end - start) + "ms");
    }
  }

  @Test
  void testConsultarPorHandleEsO1() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      for (int i = 0; i < n; i++) heap.agregarAlHeap(i);
      Heap<Integer>.Handle handle = heap.new Handle(n / 2); // Elegimos un handle al elemento del medio
      long start = System.nanoTime();
      long suma = 0; // Hay overflow en int, así que usamos long
      for (int i = 0; i < 10000000; i++) {
        suma += heap.getPorHandle(handle);
      }
      long end = System.nanoTime();
      System.out.println("getPorHandle() con " + n + " elementos -> " + (end - start) + "ns");
      assert suma > 0;
    }
  }

  // Pruebas de agregar Trasacciones al heap.

  @Test
  void testAgregarTransaccionAlHeap() {
    Heap<Transaccion> heap = new Heap<>(0);
    Transaccion t1 = new Transaccion(0, 0, 1, 1);
    Transaccion t2 = new Transaccion(1, 1, 2, 2);
    Transaccion t3 = new Transaccion(2, 2, 3, 3);
    heap.agregarAlHeap(t1);
    heap.agregarAlHeap(t2);
    heap.agregarAlHeap(t3);
    assert heap.getLongitud() == 3 : "La longitud del heap debería ser 3";
  }

  @Test
  void testMaximoTransaccion() {
    Heap<Transaccion> heap = new Heap<>(10);
    Transaccion t1 = new Transaccion(0, 0, 1, 1);
    Transaccion t2 = new Transaccion(1, 1, 2, 2);
    Transaccion t3 = new Transaccion(2, 2, 3, 3);
    Transaccion t4 = new Transaccion(3, 3, 4, 4);
    Transaccion t5 = new Transaccion(4, 4, 5, 5);
    Transaccion t6 = new Transaccion(5, 5, 6, 13);
    Transaccion t7 = new Transaccion(6, 6, 7, 7);
    Transaccion t8 = new Transaccion(7, 7, 8, 8);
    Transaccion t9 = new Transaccion(8, 8, 9, 2);
    Transaccion t10 = new Transaccion(9, 9, 10, 1);
    Transaccion t11 = new Transaccion(10, 10, 11, 1);
    heap.agregarAlHeap(t1);
    heap.agregarAlHeap(t2);
    heap.agregarAlHeap(t3);
    heap.agregarAlHeap(t4);
    heap.agregarAlHeap(t5);
    heap.agregarAlHeap(t6);
    heap.agregarAlHeap(t7);
    heap.agregarAlHeap(t8);
    heap.agregarAlHeap(t9);
    heap.agregarAlHeap(t10);
    heap.agregarAlHeap(t11);
    Transaccion max = heap.getMaximo();
    assert max.equals(t6) : "El máximo debería ser t6";
    assert heap.getLongitud() == 11 : "La longitud del heap debería ser 11";
  }

  @Test
  void testEliminarPorHandleTransaccion() {
    Heap<Transaccion> heap = new Heap<>(10);
    Transaccion t1 = new Transaccion(0, 0, 1, 1);
    Transaccion t2 = new Transaccion(1, 1, 2, 2);
    Transaccion t3 = new Transaccion(2, 2, 3, 3);
    heap.agregarAlHeap(t1);
    heap.agregarAlHeap(t2);
    heap.agregarAlHeap(t3);
    assert heap.getLongitud() == 3 : "La longitud del heap debería ser 3";
    Transaccion primerMax = heap.getMaximo();
    assert primerMax.equals(t3) : "El máximo debería ser t3";
    Heap<Transaccion>.Handle handle = heap.new Handle(0);
    heap.eliminarPorHandle(handle);
    Transaccion segundoMax = heap.getMaximo();
    assert segundoMax.equals(t2) : "El máximo después de eliminar debería ser t2";
    assert heap.getLongitud() == 2 : "La longitud del heap debería ser 2 después de eliminar";
  }

  @Test
  void testSacarMaximoTransaccion() {
    Heap<Transaccion> heap = new Heap<>(10);
    Transaccion t1 = new Transaccion(0, 0, 1, 1);
    Transaccion t2 = new Transaccion(1, 1, 2, 2);
    Transaccion t3 = new Transaccion(2, 2, 3, 3);
    heap.agregarAlHeap(t1);
    heap.agregarAlHeap(t2);
    heap.agregarAlHeap(t3);
    heap.sacarMaximo();
    heap.sacarMaximo();
    Transaccion max = heap.getMaximo();
    assert max.equals(t1) : "El máximo debería ser t1";
    assert heap.getLongitud() == 1 : "La longitud del heap debería ser 1 después de sacar dos elementos";
  }

  @Test
  void testConsultarPorHandleTransaccion() {
    Heap<Transaccion> heap = new Heap<>(5);
    Transaccion t1 = new Transaccion(0, 0, 1, 1);
    Transaccion t2 = new Transaccion(1, 1, 2, 2);
    Transaccion t3 = new Transaccion(2, 2, 3, 3);
    Transaccion t4 = new Transaccion(3, 3, 4, 4);
    Transaccion t5 = new Transaccion(4, 4, 5, 5);
    Transaccion[] transacciones = {t1, t2, t3, t4, t5};
    for (int i = 0; i < transacciones.length; i++) {
      Transaccion t = transacciones[i];
      heap.agregarAlHeap(t);
    }

    for (int i = 0; i < heap.getLongitud(); i++) {
      Heap<Transaccion>.Handle handle = heap.new Handle(i);
      Transaccion consultada = heap.getPorHandle(handle);
      boolean encontrada = false;
      for (int j = 0; j < transacciones.length; j++) {
        Transaccion t = transacciones[j];
        if (consultada.equals(t)) {
          encontrada = true;
          break;
        }
      }
      assert encontrada : "La transacción consultada por handle no coincide con ninguna transacción original";
    }
    assert heap.getLongitud() == 5 : "La longitud del heap debería seguir siendo 5 después de consultar por handle";
  }

  // Pruebas de Usuario en Heap

  @Test
  void testAgregarUsuarioAlHeap() {
    Heap<Usuario> heap = new Heap<>(5);
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(3);
    heap.agregarAlHeap(u1);
    heap.agregarAlHeap(u2);
    heap.agregarAlHeap(u3);
    assertEquals(3, heap.getLongitud());
  }

  @Test
  void testMaximoUsuarioPorBalance() {
    Heap<Usuario> heap = new Heap<>(3);
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(3);
    u2.agregarBalance(10);
    u3.agregarBalance(5);
    heap.agregarAlHeap(u1);
    heap.agregarAlHeap(u2);
    heap.agregarAlHeap(u3);
    Usuario max = heap.getMaximo();
    assertEquals(u2, max, "El usuario con mayor balance debe ser el máximo");
  }

  @Test
  void testMaximoUsuarioPorIdEnEmpate() {
    Heap<Usuario> heap = new Heap<>(2);
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    u1.agregarBalance(10);
    u2.agregarBalance(10);
    heap.agregarAlHeap(u2);
    heap.agregarAlHeap(u1);
    Usuario max = heap.getMaximo();
    assertEquals(u1, max, "En empate de balance, el usuario de menor id debe ser el máximo");
  }

  @Test
  void testActualizarBalanceYHeap() {
    Heap<Usuario> heap = new Heap<>(2);
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    heap.agregarAlHeap(u1);
    heap.agregarAlHeap(u2);
    u2.agregarBalance(20);
    assertEquals(20, u2.getBalance());
  }

  @Test
  void testAgregarUsuarioAlHeapEsOLogN() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Usuario> heap = new Heap<>(n);
      long start = System.currentTimeMillis();
      for (int i = 0; i < n; i++) heap.agregarAlHeap(new Usuario(i));
      long end = System.currentTimeMillis();
      System.out.println("agregarAlHeap() " + n + " usuarios -> " + (end - start) + "ms");
    }
  }

  @Test
  void testSacarMaximoUsuarioEsOLogN() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Usuario> heap = new Heap<>(n);
      for (int i = 0; i < n; i++) heap.agregarAlHeap(new Usuario(i));
      long start = System.currentTimeMillis();
      for (int i = 0; i < n; i++) heap.sacarMaximo();
      long end = System.currentTimeMillis();
      System.out.println("sacarMaximo() usuario " + n + " veces -> " + (end - start) + "ms");
    }
  }

  @Test
  void testGetMaximoUsuarioEsO1() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
        Heap<Usuario> heap = new Heap<>(n);
        for (int i = 0; i < n; i++) heap.agregarAlHeap(new Usuario(i));
        long start = System.nanoTime();
        Usuario max = null;
        for (int i = 0; i < 1000000; i++) max = heap.getMaximo();
        long end = System.nanoTime();
        System.out.println("getMaximo() usuario con " + n + " elementos -> " + (end - start) + "ns");
        assertNotNull(max);
    }
  }


}
