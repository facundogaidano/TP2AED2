package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UsuarioTests {
  
  @Test
  void testAgregarBalance() {
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(3);
    Usuario u4 = new Usuario(4);
    
    u1.agregarBalance(100);
    u2.agregarBalance(200);
    u3.agregarBalance(300);
    u4.agregarBalance(400);
    
    assertEquals(100, u1.getBalance(), "El balance de Usuario 1 debería ser 100");
    assertEquals(200, u2.getBalance(), "El balance de Usuario 2 debería ser 200");
    assertEquals(300, u3.getBalance(), "El balance de Usuario 3 debería ser 300");
    assertEquals(400, u4.getBalance(), "El balance de Usuario 4 debería ser 400");
  }

  @Test
  void testCompareTo() {
    // Usuarios con distinto balance
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    u1.agregarBalance(100);
    u2.agregarBalance(200);
    
    assertTrue(u1.compareTo(u2) < 0, "Usuario con menor balance debería ser menor");
    assertTrue(u2.compareTo(u1) > 0, "Usuario con mayor balance debería ser mayor");
    
    // Usuarios con mismo balance, distinto ID
    Usuario u3 = new Usuario(3);
    Usuario u4 = new Usuario(4);
    u3.agregarBalance(300);
    u4.agregarBalance(300);
    
    assertTrue(u3.compareTo(u4) > 0, "Con mismo balance, menor ID debería ser mayor");
    assertTrue(u4.compareTo(u3) < 0, "Con mismo balance, mayor ID debería ser menor");
    
    // Usuario con mismo balance y mismo ID
    Usuario u5 = new Usuario(5);
    Usuario u5Copy = new Usuario(5);
    u5.agregarBalance(500);
    u5Copy.agregarBalance(500);
    
    assertEquals(0, u5.compareTo(u5Copy), "Con mismo balance e ID, deberían ser iguales");
    
    // Propiedad transitiva
    Usuario uA = new Usuario(10);
    Usuario uB = new Usuario(11);
    Usuario uC = new Usuario(12);
    uA.agregarBalance(1000);
    uB.agregarBalance(500);
    uC.agregarBalance(200);
    
    assertTrue(uA.compareTo(uB) > 0 && uB.compareTo(uC) > 0 && uA.compareTo(uC) > 0,
              "La comparación debe ser transitiva");
  }

  @Test
  void testEquals() {
    Usuario u1 = new Usuario(1);
    Usuario u1Copy = new Usuario(1);
    Usuario u2 = new Usuario(2);
    
    u1.agregarBalance(100);
    u1Copy.agregarBalance(200); // Balance diferente, mismo ID
    u2.agregarBalance(100);     // Mismo balance, ID diferente
    
    // La igualdad se basa solo en ID
    assertTrue(u1.equals(u1Copy), "Usuarios con mismo ID deben ser iguales aunque balance difiera");
    assertFalse(u1.equals(u2), "Usuarios con distinto ID no deben ser iguales aunque balance sea igual");
    
    // Casos borde
    assertFalse(u1.equals(null), "Usuario no debe ser igual a null");
    assertFalse(u1.equals("string"), "Usuario no debe ser igual a un objeto de otro tipo");
    assertTrue(u1.equals(u1), "Usuario debe ser igual a sí mismo");
    
    // Propiedades de equals
    assertTrue(u1.equals(u1Copy) && u1Copy.equals(u1), "equals debe ser simétrico");
    
    Usuario u1Copy2 = new Usuario(1);
    assertTrue(u1.equals(u1Copy) && u1Copy.equals(u1Copy2) && u1.equals(u1Copy2),
              "equals debe ser transitivo");
  }

  @Test
  void testGetBalance() {
    Usuario usuario = new Usuario(1);
    assertEquals(0, usuario.getBalance(), "El balance inicial debería ser 0");
    
    usuario.agregarBalance(100);
    assertEquals(100, usuario.getBalance(), "El balance debería ser 100 después de agregar 100");
    
    usuario.agregarBalance(-50);
    assertEquals(50, usuario.getBalance(), "El balance debería ser 50 después de restar 50");
    
    usuario.agregarBalance(0);
    assertEquals(50, usuario.getBalance(), "El balance debería seguir siendo 50 después de agregar 0");
    
    usuario.agregarBalance(Integer.MAX_VALUE - 50);
    assertEquals(Integer.MAX_VALUE, usuario.getBalance(), "El balance debe soportar valores grandes");
    
    // Reset para siguiente prueba
    usuario = new Usuario(1);
    
    usuario.agregarBalance(-100);
    assertEquals(-100, usuario.getBalance(), "El balance debe permitir valores negativos");
  }

  @Test
  void testGetId() {
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(Integer.MAX_VALUE);
    Usuario u4 = new Usuario(-10); // Aunque no debería usarse, el código lo permite
    
    assertEquals(1, u1.getId(), "El ID del usuario 1 debe ser 1");
    assertEquals(2, u2.getId(), "El ID del usuario 2 debe ser 2");
    assertEquals(Integer.MAX_VALUE, u3.getId(), "El ID debe soportar valores grandes");
    assertEquals(-10, u4.getId(), "El ID debe soportar valores negativos si se especifican");
    
    assertNotEquals(u1.getId(), u2.getId(), "Los IDs de distintos usuarios deben ser diferentes");
  }
  
  @Test
  void testHeapIndex() {
    Usuario usuario = new Usuario(1);
    
    assertEquals(-1, usuario.getHeapIndex(), "El índice inicial debe ser -1");
    
    usuario.setHeapIndex(10);
    assertEquals(10, usuario.getHeapIndex(), "El índice debe actualizarse a 10");
    
    usuario.setHeapIndex(0);
    assertEquals(0, usuario.getHeapIndex(), "El índice debe poder ser 0");
    
    usuario.setHeapIndex(-1);
    assertEquals(-1, usuario.getHeapIndex(), "El índice debe poder volver a -1");
    
    // Valores extremos
    usuario.setHeapIndex(Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, usuario.getHeapIndex(), "El índice debe soportar valores grandes");
    
    usuario.setHeapIndex(Integer.MIN_VALUE);
    assertEquals(Integer.MIN_VALUE, usuario.getHeapIndex(), "El índice debe soportar valores negativos grandes");
  }

  @Test
  void testConstructor() {
    Usuario u1 = new Usuario(42);
    
    assertEquals(42, u1.getId(), "El ID debe ser inicializado con el valor dado");
    assertEquals(0, u1.getBalance(), "El balance inicial debe ser 0");
    assertEquals(-1, u1.getHeapIndex(), "El índice inicial debe ser -1");
  }
  
  @Test
  void testAgregarBalanceBidireccional() {
    Usuario usuario = new Usuario(1);
    
    // Incrementar balance
    for (int i = 1; i <= 10; i++) {
      usuario.agregarBalance(i * 10);
      assertEquals(i * (i + 1) * 5, usuario.getBalance(), 
                  "El balance debe acumularse correctamente");
    }
    
    // Decrementar balance
    int balance = usuario.getBalance();
    for (int i = 1; i <= 10; i++) {
      usuario.agregarBalance(-i * 5);
      balance -= i * 5;
      assertEquals(balance, usuario.getBalance(), 
                  "El balance debe disminuir correctamente");
    }
  }
  
  @Test
  void testOperacionesO1() {
    Usuario usuario = new Usuario(1);
    
    // Medir tiempo para un número grande de operaciones
    long start, end;
    int iterations = 10_000_000;
    
    // Medir getBalance
    start = System.nanoTime();
    for (int i = 0; i < iterations; i++) {
      usuario.getBalance();
    }
    end = System.nanoTime();
    long balanceTime = end - start;
    System.out.printf("getBalance() x%d -> %dns (%.2fns por operación)%n", 
                     iterations, balanceTime, (double)balanceTime/iterations);
    
    // Medir getId
    start = System.nanoTime();
    for (int i = 0; i < iterations; i++) {
      usuario.getId();
    }
    end = System.nanoTime();
    long idTime = end - start;
    System.out.printf("getId() x%d -> %dns (%.2fns por operación)%n", 
                     iterations, idTime, (double)idTime/iterations);
    
    // Medir getHeapIndex
    start = System.nanoTime();
    for (int i = 0; i < iterations; i++) {
      usuario.getHeapIndex();
    }
    end = System.nanoTime();
    long indexTime = end - start;
    System.out.printf("getHeapIndex() x%d -> %dns (%.2fns por operación)%n", 
                     iterations, indexTime, (double)indexTime/iterations);
    
    // Estas operaciones deberían tener tiempos constantes independientemente del número de iteraciones
    assertTrue(balanceTime / iterations < 10, "getBalance debe ser O(1)");
    assertTrue(idTime / iterations < 10, "getId debe ser O(1)");
    assertTrue(indexTime / iterations < 10, "getHeapIndex debe ser O(1)");
  }
}