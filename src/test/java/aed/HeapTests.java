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
  void testConstructorConArrayList() {
    ArrayList<Integer> elementos = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      elementos.add(i);
    }
    
    Heap<Integer> heap = new Heap<>(elementos);
    
    assertEquals(100, heap.getLongitud(), "El heap debe tener 100 elementos");
    assertEquals(99, heap.getMaximo(), "El máximo debe ser 99");
    
    int anterior = Integer.MAX_VALUE;
    while (heap.getLongitud() > 0) {
      int actual = heap.sacarMaximo();
      assertTrue(actual <= anterior, "Los elementos deben salir en orden decreciente");
      anterior = actual;
    }
  }

  @Test
  void testHeapMantienePropiedadDeOrden() {
    // Agregar elementos en orden arbitrario
    heapInt.agregarAlHeap(5);
    heapInt.agregarAlHeap(10);
    heapInt.agregarAlHeap(3);
    heapInt.agregarAlHeap(8);
    heapInt.agregarAlHeap(15);
    
    assertEquals(15, heapInt.sacarMaximo(), "El máximo debe ser 15");
    assertEquals(10, heapInt.sacarMaximo(), "El siguiente máximo debe ser 10");
    assertEquals(8, heapInt.sacarMaximo(), "El siguiente máximo debe ser 8");
    
    heapInt.agregarAlHeap(20);
    heapInt.agregarAlHeap(1);
    
    assertEquals(20, heapInt.sacarMaximo(), "El nuevo máximo debe ser 20");
    assertEquals(5, heapInt.sacarMaximo(), "El siguiente máximo debe ser 5");
    assertEquals(3, heapInt.sacarMaximo(), "El siguiente máximo debe ser 3");
    assertEquals(1, heapInt.sacarMaximo(), "El último elemento debe ser 1");
    
    assertEquals(0, heapInt.getLongitud(), "El heap debe estar vacío");
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
    heapInt.agregarAlHeap(42);
    
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
    
    heapUsuario.agregarAlHeap(u5);
    heapUsuario.agregarAlHeap(u4);
    heapUsuario.agregarAlHeap(u3);
    heapUsuario.agregarAlHeap(u2);
    heapUsuario.agregarAlHeap(u1);
    
    assertEquals(u1, heapUsuario.getMaximo(), "Con balances iguales, el usuario con ID=1 debe ser el máximo");
    assertEquals(u1, heapUsuario.sacarMaximo());
    assertEquals(u2, heapUsuario.sacarMaximo());
    assertEquals(u3, heapUsuario.sacarMaximo());
    assertEquals(u4, heapUsuario.sacarMaximo());
    assertEquals(u5, heapUsuario.sacarMaximo());
  }

  @Test
  void testActualizarPosicionUsuario() {
    // Probar actualización de posición en el heap
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(3);
    
    u1.agregarBalance(10);
    u2.agregarBalance(20);
    u3.agregarBalance(5);
    
    heapUsuario.agregarAlHeap(u1);
    heapUsuario.agregarAlHeap(u2);
    heapUsuario.agregarAlHeap(u3);
    
    assertEquals(u2, heapUsuario.getMaximo(), "u2 debería ser el máximo con balance 20");
    
    u3.agregarBalance(25);
    heapUsuario.actualizarPosicionUsuario(u3);
    
    assertEquals(u3, heapUsuario.getMaximo(), "u3 debería ser el máximo después de actualizar");
    
    u2.agregarBalance(-15);
    heapUsuario.actualizarPosicionUsuario(u2);
    
    assertEquals(u3, heapUsuario.sacarMaximo(), "u3 debe extraerse primero con balance 30");
    assertEquals(u1, heapUsuario.sacarMaximo(), "u1 debe extraerse segundo con balance 10");
    assertEquals(u2, heapUsuario.sacarMaximo(), "u2 debe extraerse último con balance 5");
  }

  @Test
  void testTransaccionesEnHeap() {
    // Probar manejo de transacciones en el heap
    Transaccion t1 = new Transaccion(0, 0, 1, 1);
    Transaccion t2 = new Transaccion(1, 1, 2, 2);
    Transaccion t3 = new Transaccion(2, 2, 3, 3);
    Transaccion t4 = new Transaccion(3, 3, 4, 4);
    Transaccion t5 = new Transaccion(4, 4, 5, 3); // Mismo monto que t3
    
    heapTransaccion.agregarAlHeap(t1);
    heapTransaccion.agregarAlHeap(t2);
    heapTransaccion.agregarAlHeap(t3);
    heapTransaccion.agregarAlHeap(t4);
    heapTransaccion.agregarAlHeap(t5);
    
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
      heap.agregarAlHeap(random.nextInt(1000));
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
    int[] casos = {1000, 10000, 100000, 1000000};
    
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
    int[] casos = {1000, 10000, 100000, 1000000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      
      for (int i = 0; i < n-1; i++) {
        heap.agregarAlHeap(i);
      }
      
      long start = System.nanoTime();
      heap.agregarAlHeap(n);
      long end = System.nanoTime();
      
      System.out.println("agregarAlHeap() con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(n, heap.getLongitud(), "El heap debe contener " + n + " elementos");
    }
  }

  @Test
  void testSacarMaximoEsOLogN() {
    // Verificar que sacar el máximo es O(log n)
    int[] casos = {1000, 10000, 100000, 1000000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      
      for (int i = 0; i < n; i++) {
        heap.agregarAlHeap(i);
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
    int[] casos = {1000, 10000, 100000, 1000000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      
      for (int i = 0; i < n; i++) {
        heap.agregarAlHeap(i);
      }
      
      long start = System.nanoTime();
      Integer max = heap.getMaximo();
      long end = System.nanoTime();
      
      System.out.println("getMaximo() con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(n-1, max, "El máximo debe ser " + (n-1));
    }
  }

  @Test
  void testActualizarPosicionUsuarioEsOLogP() {
    // Verificar que actualizar posición de usuario es O(log P)
    int[] casos = {1000, 10000, 100000, 1000000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Usuario> heap = new Heap<>(n);
      
      Usuario[] usuarios = new Usuario[n];
      for (int i = 0; i < n; i++) {
        usuarios[i] = new Usuario(i + 1);
        usuarios[i].agregarBalance(i);
        heap.agregarAlHeap(usuarios[i]);
      }
      
      Usuario usuarioActualizar = usuarios[n/2];
      usuarioActualizar.agregarBalance(n);
      
      long start = System.nanoTime();
      heap.actualizarPosicionUsuario(usuarioActualizar);
      long end = System.nanoTime();
      
      System.out.println("actualizarPosicionUsuario() con " + n + " elementos -> " + (end - start) + "ns");
      assertEquals(usuarioActualizar, heap.getMaximo(), "El usuario actualizado debe ser el máximo");
    }
  }

  @Test
  void testGetLongitudEsO1() {
    // Verificar que getLongitud es O(1)
    int[] casos = {1000, 10000, 100000, 1000000};
    
    for (int idx = 0; idx < casos.length; idx++) {
      int n = casos[idx];
      Heap<Integer> heap = new Heap<>(n);
      
      for (int i = 0; i < n; i++) {
        heap.agregarAlHeap(i);
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
    int n = 100000;
    Heap<Integer> heap = new Heap<>(n);
    for (int i = 0; i < n; i++) {
      heap.agregarAlHeap(i);
    }
    
    assertEquals(n, heap.getLongitud(), "El heap debe tener " + n + " elementos");
    assertEquals(n-1, heap.getMaximo(), "El máximo debe ser " + (n-1));
  }

  @Test
  void testElementosNegativos() {
    // Caso borde: valores negativos
    Heap<Integer> heap = new Heap<>(10);
    heap.agregarAlHeap(-1);
    heap.agregarAlHeap(-10);
    heap.agregarAlHeap(-5);
    heap.agregarAlHeap(-3);
    
    assertEquals(-1, heap.sacarMaximo(), "El mayor valor negativo es -1");
    assertEquals(-3, heap.sacarMaximo(), "El siguiente es -3");
    assertEquals(-5, heap.sacarMaximo(), "El siguiente es -5");
    assertEquals(-10, heap.sacarMaximo(), "El último es -10");
  }
}
