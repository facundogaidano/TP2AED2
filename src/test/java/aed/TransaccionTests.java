package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TransaccionTests {

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
    Transaccion t4 = new Transaccion(0, 0, 2, 10); 
    Transaccion t5 = new Transaccion(0, 1, 1, 10);
    Transaccion t6 = new Transaccion(0, 0, 1, 15);

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
  void testGetters() {
    Transaccion t = new Transaccion(42, 1, 2, 100);
    
    assertEquals(42, t.id(), "ID debe ser 42");
    assertEquals(1, t.id_comprador(), "ID de comprador debe ser 1");
    assertEquals(2, t.id_vendedor(), "ID de vendedor debe ser 2");
    assertEquals(100, t.monto(), "Monto debe ser 100");
  }
  
  @Test
  void testComplejidadCompareTo() {
    int n = 1000000;
    Transaccion t1 = new Transaccion(1, 1, 2, 10);
    Transaccion t2 = new Transaccion(2, 3, 4, 20);
    
    long start = System.nanoTime();
    for (int i = 0; i < n; i++) {
      t1.compareTo(t2);
    }
    long end = System.nanoTime();
    
    System.out.println("compareTo() x1.000.000 -> " + ((end - start) / n) + "ns por operación");
  }
}