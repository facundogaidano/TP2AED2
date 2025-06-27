package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapTests {
  private Heap<Integer> heapInt;
  private Heap<Usuario> heapUsuario;
  private Heap<Transaccion> heapTransaccion;
  
  @BeforeEach
  void setUp() {
    // Crear heaps vacíos para las pruebas
    heapInt = new Heap<>(10);
    heapUsuario = new Heap<>(10);
    heapTransaccion = new Heap<>(10);
  }

  @Test
  void testConstructorVacio() {
    assertEquals(0, heapInt.getLongitud(), "El heap debe estar vacío al inicializarse");
    assertNull(heapInt.getMaximo(), "El máximo de un heap vacío debe ser null");
  }

  @Test
  void testSacarMaximoHeapVacio() {
    // Caso borde: sacar máximo de un heap vacío
    assertThrows(IllegalStateException.class, () -> {
      heapInt.sacarMaximo();
    }, "Debe lanzar IllegalStateException al sacar máximo de heap vacío");
  }

  @Test
  void testHeapUnElemento() {
    // Caso borde: heap con un único elemento
    heapInt.agregar(42, 0);
    
    assertEquals(1, heapInt.getLongitud(), "El heap debe tener 1 elemento");
    assertEquals(42, heapInt.getMaximo(), "El máximo debe ser 42");
    
    Integer elemento = heapInt.sacarMaximo();
    assertEquals(42, elemento, "El elemento extraído debe ser 42");
    assertEquals(0, heapInt.getLongitud(), "El heap debe estar vacío después de extraer");
    assertNull(heapInt.getMaximo(), "El máximo ahora debe ser null");
  }

  @Test
  void testElementosIgualPrioridad() {
    // Caso borde: elementos con misma prioridad
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(3);
    Usuario u4 = new Usuario(4);
    Usuario u5 = new Usuario(5);
    
    u1.agregarBalance(10);
    u2.agregarBalance(10);
    u3.agregarBalance(10);
    u4.agregarBalance(10);
    u5.agregarBalance(10);
    
    heapUsuario.agregar(u5, u5.getId());
    heapUsuario.agregar(u4, u4.getId());
    heapUsuario.agregar(u3, u3.getId());
    heapUsuario.agregar(u2, u2.getId());
    heapUsuario.agregar(u1, u1.getId());
    
    assertEquals(u1, heapUsuario.getMaximo(), "Con balances iguales, el usuario con ID=1 debe ser el máximo");
    assertEquals(u1, heapUsuario.sacarMaximo());
    assertEquals(u2, heapUsuario.sacarMaximo());
    assertEquals(u3, heapUsuario.sacarMaximo());
    assertEquals(u4, heapUsuario.sacarMaximo());
    assertEquals(u5, heapUsuario.sacarMaximo());
  }

  @Test
  void testTransaccionesEnHeap() {
    // Probar manejo de transacciones en el heap
    Transaccion t1 = new Transaccion(0, 0, 1, 1);
    Transaccion t2 = new Transaccion(1, 1, 2, 2);
    Transaccion t3 = new Transaccion(2, 2, 3, 3);
    Transaccion t4 = new Transaccion(3, 3, 4, 4);
    Transaccion t5 = new Transaccion(4, 4, 5, 3); // Mismo monto que t3
    
    heapTransaccion.agregar(t1, t1.id());
    heapTransaccion.agregar(t2, t2.id());
    heapTransaccion.agregar(t3, t3.id());
    heapTransaccion.agregar(t4, t4.id());
    heapTransaccion.agregar(t5, t5.id());
    
    assertEquals(t4, heapTransaccion.sacarMaximo(), "t4 tiene el mayor monto (4)");
    assertEquals(t5, heapTransaccion.sacarMaximo(), "t3 y t5 tienen mismo monto, pero t5 tiene mayor ID");
    assertEquals(t3, heapTransaccion.sacarMaximo(), "t3 tiene monto 3");
    assertEquals(t2, heapTransaccion.sacarMaximo(), "t2 tiene monto 2");
    assertEquals(t1, heapTransaccion.sacarMaximo(), "t1 tiene monto 1");
  }

  @Test
  void testInsertarExtraerAleatorio() {
    // Prueba con elementos aleatorios para verificar estabilidad
    Heap<Integer> heap = new Heap<>(100);
    Random random = new Random(42); // Random
    
    // Insertar 100 elementos aleatorios
    for (int i = 0; i < 100; i++) {
      int num = random.nextInt(1000);
      heap.agregar(num, i);
    }
    
    int anterior = Integer.MAX_VALUE;
    while (heap.getLongitud() > 0) {
      int actual = heap.sacarMaximo();
      assertTrue(actual <= anterior, "Los elementos deben salir en orden decreciente");
      anterior = actual;
    }
  }

  // Pruebas de complejidad

  @Test
  void testConstructorArrayListEsON() {
    // Verificar que la construcción del heap es O(n)
    int[] casos = {1000, 10000, 100000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      ArrayList<Integer> elementos = new ArrayList<>(n);
      for (int i = 0; i < n; i++) elementos.add(i);
      
      long start = System.nanoTime();
      Heap<Integer> heap = new Heap<>(elementos);
      long end = System.nanoTime();
      
      System.out.println("Constructor con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(n, heap.getLongitud(), "El heap debe tener " + n + " elementos");
    }
  }

  @Test
  void testAgregarElementoEsOLogN() {
    // Verificar que agregar elementos es O(log n)
    int[] casos = {1000, 10000, 100000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      
      for (int i = 0; i < n-1; i++) {
        heap.agregar(i, i);
      }
      
      long start = System.nanoTime();
      heap.agregar(n, n);
      long end = System.nanoTime();
      
      System.out.println("agregar() con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(n, heap.getLongitud(), "El heap debe contener " + n + " elementos");
    }
  }

  @Test
  void testSacarMaximoEsOLogN() {
    // Verificar que sacar el máximo es O(log n)
    int[] casos = {1000, 10000, 100000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      
      for (int i = 0; i < n; i++) {
        heap.agregar(i, i);
      }
      
      long start = System.nanoTime();
      Integer max = heap.sacarMaximo();
      long end = System.nanoTime();
      
      System.out.println("sacarMaximo() con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(n-1, max, "El máximo debe ser " + (n-1));
      assertEquals(n-1, heap.getLongitud(), "El heap debe contener " + (n-1) + " elementos");
    }
  }

  @Test
  void testGetMaximoEsO1() {
    // Verificar que obtener el máximo es O(1)
    int[] casos = {1000, 10000, 100000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      
      for (int i = 0; i < n; i++) {
        heap.agregar(i, i);
      }
      
      long start = System.nanoTime();
      Integer max = heap.getMaximo();
      long end = System.nanoTime();
      
      System.out.println("getMaximo() con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(n-1, max, "El máximo debe ser " + (n-1));
    }
  }

  @Test
  void testActualizarPosicionEsOLogP() {
    // Verificar que actualizar posición de usuario es O(log P)
    int[] casos = {1000, 10000, 100000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Usuario> heap = new Heap<>(n);
      
      Usuario[] usuarios = new Usuario[n];
      for (int i = 0; i < n; i++) {
        usuarios[i] = new Usuario(i + 1);
        usuarios[i].agregarBalance(i);
        heap.agregar(usuarios[i], usuarios[i].getId());
      }
      
      Usuario usuarioActualizar = usuarios[n/2];
      usuarioActualizar.agregarBalance(n);
      
      long start = System.nanoTime();
      heap.actualizarPosicion(usuarioActualizar.getId());
      long end = System.nanoTime();
      
      System.out.println("actualizarPosicion() con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(usuarioActualizar, heap.getMaximo(), "El usuario actualizado debe ser el máximo");
    }
  }

  @Test
  void testGetLongitudEsO1() {
    // Verificar que getLongitud es O(1)
    int[] casos = {1000, 10000, 100000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      
      for (int i = 0; i < n; i++) {
        heap.agregar(i, i);
      }
      
      long start = System.nanoTime();
      int longitud = heap.getLongitud();
      long end = System.nanoTime();
      
      System.out.println("getLongitud() con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(n, longitud, "La longitud debe ser " + n);
    }
  }

  @Test
  void testBufferVacio() {
    // Caso borde: heap con buffer vacío
    Heap<Integer> heap = new Heap<>(0);
    assertEquals(0, heap.getLongitud(), "El heap debe estar vacío");
    assertNull(heap.getMaximo(), "El máximo debe ser null");
  }

  @Test
  void testHeapGrande() {
    // Caso borde: heap con muchos elementos
    int n = 10000; // Reducido para que los tests corran más rápido
    Heap<Integer> heap = new Heap<>(n);
    for (int i = 0; i < n; i++) {
      heap.agregar(i, i);
    }
    
    assertEquals(n, heap.getLongitud(), "El heap debe tener " + n + " elementos");
    assertEquals(n-1, heap.getMaximo(), "El máximo debe ser " + (n-1));
  }

  @Test
  void testElementosNegativos() {
    // Caso borde: valores negativos
    Heap<Integer> heap = new Heap<>(10);
    heap.agregar(-1, 0);
    heap.agregar(-10, 1);
    heap.agregar(-5, 2);
    heap.agregar(-3, 3);
    
    assertEquals(-1, heap.sacarMaximo(), "El mayor valor negativo es -1");
    assertEquals(-3, heap.sacarMaximo(), "El siguiente es -3");
    assertEquals(-5, heap.sacarMaximo(), "El siguiente es -5");
    assertEquals(-10, heap.sacarMaximo(), "El último es -10");
  }
}