package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class HeapTests {
  @Test
  void testConstructorVacio() {
    Heap<Integer> heap = new Heap<>(10);
    assertEquals(0, heap.getLongitud(), "El heap debe estar vacío al inicializarse");
    assertNull(heap.getMaximo(), "El máximo de un heap vacío debe ser null");
  }

  @Test
  void testConstructorConArrayList() {
    ArrayList<Integer> elementos = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      elementos.add(i);
    }
    
    // El algoritmo de Floyd debe construir el heap en O(n)
    long start = System.nanoTime();
    Heap<Integer> heap = new Heap<>(elementos);
    long end = System.nanoTime();
    
    System.out.println("Construcción de heap con " + elementos.size() + " elementos -> " + (end - start) + "ns");
    
    assertEquals(100, heap.getLongitud(), "El heap debe tener 100 elementos");
    assertEquals(99, heap.getMaximo(), "El máximo debe ser 99");
    
    // Verificar que el heap está correctamente construido sacando elementos
    int anterior = Integer.MAX_VALUE;
    while (heap.getLongitud() > 0) {
      int actual = heap.sacarMaximo();
      assertTrue(actual <= anterior, "Los elementos deben salir en orden decreciente");
      anterior = actual;
    }
  }

  @Test
  void testHeapMantienePropiedadDeOrden() {
    Heap<Integer> heap = new Heap<>(20);
    
    // Agregar elementos en orden arbitrario
    heap.agregarAlHeap(5);
    heap.agregarAlHeap(10);
    heap.agregarAlHeap(3);
    heap.agregarAlHeap(8);
    heap.agregarAlHeap(15);
    
    // Verificar que salen en orden correcto
    assertEquals(15, heap.sacarMaximo(), "El máximo debe ser 15");
    assertEquals(10, heap.sacarMaximo(), "El siguiente máximo debe ser 10");
    assertEquals(8, heap.sacarMaximo(), "El siguiente máximo debe ser 8");
    
    // Agregar más elementos
    heap.agregarAlHeap(20);
    heap.agregarAlHeap(1);
    
    assertEquals(20, heap.sacarMaximo(), "El nuevo máximo debe ser 20");
    assertEquals(5, heap.sacarMaximo(), "El siguiente máximo debe ser 5");
    assertEquals(3, heap.sacarMaximo(), "El siguiente máximo debe ser 3");
    assertEquals(1, heap.sacarMaximo(), "El último elemento debe ser 1");
    
    assertEquals(0, heap.getLongitud(), "El heap debe estar vacío");
  }

  @Test
  void testSacarMaximoHeapVacio() {
    Heap<Integer> heap = new Heap<>(0);
    assertThrows(IllegalStateException.class, () -> {
      heap.sacarMaximo();
    }, "Debe lanzar IllegalStateException al sacar máximo de heap vacío");
  }

  @Test
  void testHeapUnElemento() {
    Heap<Integer> heap = new Heap<>(1);
    heap.agregarAlHeap(42);
    
    assertEquals(1, heap.getLongitud(), "El heap debe tener 1 elemento");
    assertEquals(42, heap.getMaximo(), "El máximo debe ser 42");
    
    Integer elemento = heap.sacarMaximo();
    assertEquals(42, elemento, "El elemento extraído debe ser 42");
    assertEquals(0, heap.getLongitud(), "El heap debe estar vacío después de extraer");
    assertNull(heap.getMaximo(), "El máximo ahora debe ser null");
  }

  @Test
  void testElementosIgualPrioridad() {
    Heap<Usuario> heap = new Heap<>(5);
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(3);
    Usuario u4 = new Usuario(4);
    Usuario u5 = new Usuario(5);
    
    // Todos con el mismo balance
    u1.agregarBalance(10);
    u2.agregarBalance(10);
    u3.agregarBalance(10);
    u4.agregarBalance(10);
    u5.agregarBalance(10);
    
    heap.agregarAlHeap(u5);
    heap.agregarAlHeap(u4);
    heap.agregarAlHeap(u3);
    heap.agregarAlHeap(u2);
    heap.agregarAlHeap(u1);
    
    // En caso de empate, el de menor ID es mayor
    assertEquals(u1, heap.getMaximo(), "Con balances iguales, el usuario con ID=1 debe ser el máximo");
    assertEquals(u1, heap.sacarMaximo());
    assertEquals(u2, heap.sacarMaximo());
    assertEquals(u3, heap.sacarMaximo());
    assertEquals(u4, heap.sacarMaximo());
    assertEquals(u5, heap.sacarMaximo());
  }

  @Test
  void testIndicesUsuarioActualizados() {
    Heap<Usuario> heap = new Heap<>(5);
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(3);
    
    // Inicialmente todos con balance 0
    heap.agregarAlHeap(u1);
    heap.agregarAlHeap(u2);
    heap.agregarAlHeap(u3);
    
    // Verificar que todos tienen índices válidos
    assertTrue(u1.getHeapIndex() >= 0, "u1 debe tener índice válido");
    assertTrue(u2.getHeapIndex() >= 0, "u2 debe tener índice válido");
    assertTrue(u3.getHeapIndex() >= 0, "u3 debe tener índice válido");
    
    // Extraer el máximo y verificar que el índice se marca como inválido
    Usuario extraido = heap.sacarMaximo();
    assertEquals(-1, extraido.getHeapIndex(), "El usuario extraído debe tener índice -1");
    
    // Los demás deben mantener índices válidos
    int indiceU2 = u2.getHeapIndex();
    int indiceU3 = u3.getHeapIndex();
    assertTrue(indiceU2 >= 0 && indiceU2 < heap.getLongitud(), "u2 debe mantener índice válido");
    assertTrue(indiceU3 >= 0 && indiceU3 < heap.getLongitud(), "u3 debe mantener índice válido");
  }

  @Test
  void testActualizarPosicionUsuario() {
    Heap<Usuario> heap = new Heap<>(3);
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(3);
    
    u1.agregarBalance(10);
    u2.agregarBalance(20);
    u3.agregarBalance(5);
    
    heap.agregarAlHeap(u1);
    heap.agregarAlHeap(u2);
    heap.agregarAlHeap(u3);
    
    // Inicialmente u2 tiene el mayor balance
    assertEquals(u2, heap.getMaximo(), "u2 debería ser el máximo con balance 20");
    
    // Incrementar el balance de u3 para que sea mayor
    u3.agregarBalance(25); // Ahora tiene 30
    heap.actualizarPosicionUsuario(u3);
    
    // Ahora u3 debería ser el máximo
    assertEquals(u3, heap.getMaximo(), "u3 debería ser el máximo después de actualizar");
    
    // Decrementar el balance de u2
    u2.agregarBalance(-15); // Ahora tiene 5
    heap.actualizarPosicionUsuario(u2);
    
    // Verificar el orden de extracción
    assertEquals(u3, heap.sacarMaximo(), "u3 debe extraerse primero con balance 30");
    assertEquals(u1, heap.sacarMaximo(), "u1 debe extraerse segundo con balance 10");
    assertEquals(u2, heap.sacarMaximo(), "u2 debe extraerse último con balance 5");
  }

  @Test
  void testInsertarExtraerAleatorio() {
    Heap<Integer> heap = new Heap<>(100);
    Random random = new Random(42); // Semilla fija para reproducibilidad
    
    // Insertar 100 elementos aleatorios
    for (int i = 0; i < 100; i++) {
      heap.agregarAlHeap(random.nextInt(1000));
    }
    
    // Extraer todos y verificar que están en orden
    int anterior = Integer.MAX_VALUE;
    while (heap.getLongitud() > 0) {
      int actual = heap.sacarMaximo();
      assertTrue(actual <= anterior, "Los elementos deben salir en orden decreciente");
      anterior = actual;
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
      for (int i = 0; i < 1000000; i++) {
        assertEquals(longitud, heap.getLongitud(), "La longitud del heap no debe cambiar");
      }
      long end = System.nanoTime();
      System.out.println("getLongitud() del heap con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(n, longitud, "La longitud del heap debe ser " + n);
    }
  }

  @Test
  void testGetMaximoEsO1() {
    int[] casos = {1000, 10000, 100000, 1000000};
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      for (int i = 0; i < n; i++) heap.agregarAlHeap(i);
      Integer maxEsperado = heap.getMaximo();
      long start = System.nanoTime();
      for (int i = 0; i < 1000000; i++) {
        assertEquals(maxEsperado, heap.getMaximo(), "El máximo no debe cambiar");
      }
      long end = System.nanoTime();
      System.out.println("getMaximo() del heap con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(n - 1, maxEsperado, "El máximo debe ser " + (n - 1));
    }
  }

  @Test
  void testConstructorConArrayListEsON() {
    int[] casos = {1000, 10000, 100000, 1000000};
    long[] tiempos = new long[casos.length];
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      ArrayList<Integer> elementos = new ArrayList<>(n);
      for (int i = 0; i < n; i++) elementos.add(i);
      
      long start = System.nanoTime();
      Heap<Integer> heap = new Heap<>(elementos);
      long end = System.nanoTime();
      tiempos[idx] = end - start;
      
      System.out.println("Constructor con " + n + " elementos -> " + tiempos[idx]/1000000.0 + "ms");
      assertEquals(n, heap.getLongitud(), "El heap debe tener " + n + " elementos");
    }
    
    // Verificar que el crecimiento es lineal, no cuadrático
    if (casos.length > 2) {
      double ratio1 = (double)tiempos[1] / tiempos[0];
      double ratio2 = (double)tiempos[2] / tiempos[1];
      double ratioTamaño = (double)casos[1] / casos[0];
      
      System.out.println("Ratio de tiempo 10k/1k: " + ratio1);
      System.out.println("Ratio de tiempo 100k/10k: " + ratio2);
      System.out.println("Ratio de tamaño: " + ratioTamaño);
      
      // La proporción de tiempos debe ser cercana a la proporción de tamaños
      assertTrue(ratio2 < ratioTamaño * 2, "La construcción debe ser aproximadamente lineal");
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
    assertEquals(3, heap.getLongitud(), "La longitud del heap debería ser 3");
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
    assertEquals(t6, max, "El máximo debería ser t6");
    assertEquals(11, heap.getLongitud(), "La longitud del heap debería ser 11");
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
    assertEquals(t1, max, "El máximo debería ser t1");
    assertEquals(1, heap.getLongitud(), "La longitud del heap debería ser 1 después de sacar dos elementos");
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
