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
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    u1.agregarBalance(100);
    u2.agregarBalance(200);
    
    assertTrue(u1.compareTo(u2) < 0, "Usuario con menor balance debería ser menor");
    assertTrue(u2.compareTo(u1) > 0, "Usuario con mayor balance debería ser mayor");
    
    Usuario u3 = new Usuario(3);
    Usuario u4 = new Usuario(4);
    u3.agregarBalance(300);
    u4.agregarBalance(300);
    
    assertTrue(u3.compareTo(u4) > 0, "Con mismo balance, menor ID debería ser mayor");
    assertTrue(u4.compareTo(u3) < 0, "Con mismo balance, mayor ID debería ser menor");
    
    Usuario u5 = new Usuario(5);
    Usuario u5Copy = new Usuario(5);
    u5.agregarBalance(500);
    u5Copy.agregarBalance(500);
    
    assertEquals(0, u5.compareTo(u5Copy), "Con mismo balance e ID, deberían ser iguales");
    
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
    u1Copy.agregarBalance(200);
    u2.agregarBalance(100);
    
    assertTrue(u1.equals(u1Copy), "Usuarios con mismo ID deben ser iguales aunque balance difiera");
    assertFalse(u1.equals(u2), "Usuarios con distinto ID no deben ser iguales aunque balance sea igual");
    
    assertFalse(u1.equals(null), "Usuario no debe ser igual a null");
    assertTrue(u1.equals(u1), "Usuario debe ser igual a sí mismo");
    
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
    
    usuario = new Usuario(1);
    
    usuario.agregarBalance(-100);
    assertEquals(-100, usuario.getBalance(), "El balance debe permitir valores negativos");
  }

  @Test
  void testGetId() {
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(2);
    Usuario u3 = new Usuario(Integer.MAX_VALUE);
    Usuario u4 = new Usuario(-10);
    
    assertEquals(1, u1.getId(), "El ID del usuario 1 debe ser 1");
    assertEquals(2, u2.getId(), "El ID del usuario 2 debe ser 2");
    assertEquals(Integer.MAX_VALUE, u3.getId(), "El ID debe soportar valores grandes");
    assertEquals(-10, u4.getId(), "El ID debe soportar valores negativos si se especifican");
    
    assertNotEquals(u1.getId(), u2.getId(), "Los IDs de distintos usuarios deben ser diferentes");
  }

  @Test
  void testConstructor() {
    Usuario u1 = new Usuario(42);
    
    assertEquals(42, u1.getId(), "El ID debe ser inicializado con el valor dado");
    assertEquals(0, u1.getBalance(), "El balance inicial debe ser 0");
  }
  
  @Test
  void testAgregarBalanceBidireccional() {
    Usuario usuario = new Usuario(1);
    
    for (int i = 1; i <= 10; i++) {
      usuario.agregarBalance(i * 10);
      assertEquals(i * (i + 1) * 5, usuario.getBalance(), 
                  "El balance debe acumularse correctamente");
    }
    
    int balance = usuario.getBalance();
    for (int i = 1; i <= 10; i++) {
      usuario.agregarBalance(-i * 5);
      balance -= i * 5;
      assertEquals(balance, usuario.getBalance(), 
                  "El balance debe disminuir correctamente");
    }
  }
  
  @Test
  void testCompareToEsO1() {
    int[] tamanios = {1000, 10000, 100000, 1000000};
    
    Usuario u1 = new Usuario(1);
    u1.agregarBalance(100);
    Usuario u2 = new Usuario(2);
    u2.agregarBalance(200);
    
    for (int n : tamanios) {
      long start = System.nanoTime();
      for (int i = 0; i < n; i++) {
        u1.compareTo(u2);
      }
      long end = System.nanoTime();
      System.out.println("compareTo() x" + n + " -> " + ((end - start) / n) + "ns por operación");
    }
  }
  
  @Test
  void testGetBalanceEsO1() {
    int[] tamanios = {1000, 10000, 100000, 1000000};
    Usuario usuario = new Usuario(1);
    
    for (int n : tamanios) {
      long start = System.nanoTime();
      for (int i = 0; i < n; i++) {
        usuario.getBalance();
      }
      long end = System.nanoTime();
      System.out.println("getBalance() x" + n + " -> " + ((end - start) / n) + "ns por operación");
    }
  }
  
  @Test
  void testGetIdEsO1() {
    int[] tamanios = {1000, 10000, 100000, 1000000};
    Usuario usuario = new Usuario(1);
    
    for (int n : tamanios) {
      long start = System.nanoTime();
      for (int i = 0; i < n; i++) {
        usuario.getId();
      }
      long end = System.nanoTime();
      System.out.println("getId() x" + n + " -> " + ((end - start) / n) + "ns por operación");
    }
  }
  
  @Test
  void testAgregarBalanceEsO1() {
    int[] tamanios = {1000, 10000, 100000, 1000000};
    Usuario usuario = new Usuario(1);
    
    for (int n : tamanios) {
      long start = System.nanoTime();
      for (int i = 0; i < n; i++) {
        usuario.agregarBalance(0);
      }
      long end = System.nanoTime();
      System.out.println("agregarBalance() x" + n + " -> " + ((end - start) / n) + "ns por operación");
    }
  }
  
  @Test
  void testEqualsEsO1() {
    int[] tamanios = {1000, 10000, 100000, 1000000};
    Usuario u1 = new Usuario(1);
    Usuario u2 = new Usuario(1);
    
    for (int n : tamanios) {
      long start = System.nanoTime();
      for (int i = 0; i < n; i++) {
        u1.equals(u2);
      }
      long end = System.nanoTime();
      System.out.println("equals() x" + n + " -> " + ((end - start) / n) + "ns por operación");
    }
  }
}