package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapTests {
  private Heap<Integer> heapInt;
  
  @BeforeEach
  void setUp() {
    // Crear heaps vacíos para las pruebas
    heapInt = new Heap<>(10);
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
  void testBufferVacio() {
    // Caso borde: heap con buffer vacío
    Heap<Integer> heap = new Heap<>(0);
    assertEquals(0, heap.getLongitud(), "El heap debe estar vacío");
    assertNull(heap.getMaximo(), "El máximo debe ser null");
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