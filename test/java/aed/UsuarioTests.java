package aed;

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
    assert u1.getBalance() == 100 : "El balance de Usuario 1 debería ser 100";
    assert u2.getBalance() == 200 : "El balance de Usuario 2 debería ser 200";
    assert u3.getBalance() == 300 : "El balance de Usuario 3 debería ser 300";
    assert u4.getBalance() == 400 : "El balance de Usuario 4 debería ser 400";
  }

  @Test
  void testCompareTo() {
    Usuario usuario1 = new Usuario(1);
    Usuario usuario2 = new Usuario(1); // mismo id
    usuario1.agregarBalance(100);
    usuario2.agregarBalance(100);
    assert usuario1.compareTo(usuario2) == 0 : "Usuario 1 y Usuario 2 deberían ser iguales por balance y id";
  }

  @Test
  void testEquals() {
    Usuario usuario1 = new Usuario(1);
    Usuario usuario2 = new Usuario(1);
    Usuario usuario3 = new Usuario(2);
    
    usuario1.agregarBalance(100);
    usuario2.agregarBalance(100);
    usuario3.agregarBalance(200);
    
    assert usuario1.equals(usuario2) : "Usuario 1 debería ser igual a Usuario 2 por id y balance";
    assert !usuario1.equals(usuario3) : "Usuario 1 no debería ser igual a Usuario 3 por id diferente";
    assert !usuario1.equals(null) : "Usuario 1 no debería ser igual a null";
    assert !usuario1.equals("string") : "Usuario 1 no debería ser igual a un objeto de tipo String";
  }

  @Test
  void testGetBalance() {
    Usuario usuario = new Usuario(1);
    assert usuario.getBalance() == 0 : "El balance inicial debería ser 0";
    
    usuario.agregarBalance(100);
    assert usuario.getBalance() == 100 : "El balance debería ser 100 después de agregar 100";
    
    usuario.agregarBalance(-50);
    assert usuario.getBalance() == 50 : "El balance debería ser 50 después de restar 50";
    
    usuario.agregarBalance(0);
    assert usuario.getBalance() == 50 : "El balance debería seguir siendo 50 después de agregar 0";
  }

  @Test
  void testGetId() {
    Usuario usuario = new Usuario(1);
    assert usuario.getId() == 1 : "El ID del usuario debería ser 1";
    
    Usuario otroUsuario = new Usuario(2);
    assert otroUsuario.getId() == 2 : "El ID del otro usuario debería ser 2";
    
    assert usuario.getId() != otroUsuario.getId() : "Los IDs de los usuarios deberían ser diferentes";
  }
}
