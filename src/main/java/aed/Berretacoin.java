package aed;

import java.util.ArrayList;

/**
 * Implementación del sistema de criptomoneda Berretacoin.
 * Gestiona usuarios, transacciones y bloques con estructuras optimizadas.
 * n es el número de transacciones y P es el número de usuarios.
 */
public class Berretacoin {
    private Heap<Usuario> usuarios;     // MaxHeap de usuarios ordenados por balance.
    private Usuario[] usuariosArray;    // Acceso a usuarios O(1) por ID.
    private Usuario maxTenedor;         // Usuario con mayor balance.
    private int contadorBloques = 0;    // Contador de bloques en la cadena.
    private Bloque ultimoBloque;        // Último bloque agregado.

    /**
     * Constructor que inicializa el sistema con un número dado de usuarios.
     * Complejidad: O(P)
     */
    public Berretacoin(int n_usuarios){
        // Array de usuarios indexado desde 1.
        this.usuariosArray = new Usuario[n_usuarios + 1]; 

        // Inicializar todos los usuarios con balance cero - O(P)
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        for (int i = 0; i < n_usuarios; i++) {
            Usuario user = new Usuario(i + 1);
            usuariosArray[i + 1] = user;
            listaUsuarios.add(user);
        }

        // Crear el heap de usuarios con los usuarios inicializados - O(P)
        this.usuarios = new Heap<>(listaUsuarios);
        this.maxTenedor = usuarios.getMaximo();
        this.contadorBloques = 0;
    }

    /**
     * Agrega un bloque de transacciones a la cadena y actualiza los balances.
     * Complejidad: O(n * log P)
     */
    public void agregarBloque(Transaccion[] transacciones){
        // Crear nuevo bloque y actualizar referencias - O(n)
        Bloque bloque = new Bloque(contadorBloques, transacciones);
        this.ultimoBloque = bloque;
        contadorBloques++;
        
        // Aplicar transacciones y actualizar balances de usuarios - O(n * log P)
        Transaccion.aplicarTransacciones(usuarios, transacciones, usuariosArray);
        
        // Actualizar referencia al usuario con mayor balance
        this.maxTenedor = usuarios.getMaximo();
    }

    /**
     * Devuelve la transacción con mayor monto del último bloque.
     * Complejidad: O(1)
     */
    public Transaccion txMayorValorUltimoBloque(){
        if (ultimoBloque == null) return null;
        return ultimoBloque.transaccionMayorValor();
    }

    /**
     * Devuelve todas las transacciones del último bloque.
     * Complejidad: O(n)
     */
    public Transaccion[] txUltimoBloque(){
        if (ultimoBloque == null) return new Transaccion[0];
        return ultimoBloque.getTransacciones();
    }

    /**
     * Devuelve el ID del usuario con mayor balance.
     * Complejidad: O(1)
     */
    public int maximoTenedor(){
        if (maxTenedor == null) return 0;
        return maxTenedor.getId();
    }

    /**
     * Devuelve el monto medio de las transacciones del último bloque.
     * Complejidad: O(1)
     */
    public int montoMedioUltimoBloque(){
        if (ultimoBloque == null) return 0;
        return ultimoBloque.montoMedio();
    }

    /**
     * Elimina la transacción de mayor valor del último bloque y revierte los montos.
     * Complejidad: O(log n + log P)
     */
    public void hackearTx() {
        if (ultimoBloque == null) return;
        
        // Obtener y eliminar la transacción de mayor valor - O(log n)
        Transaccion hackeada = ultimoBloque.eliminarMayorValor();
        if (hackeada == null) return;
        
        // Revertir los efectos de la transacción en los usuarios - O(log P)
        Transaccion.revertirTransaccion(usuarios, hackeada, usuariosArray);
        
        // Actualizar la referencia al usuario con mayor balance - O(1)
        this.maxTenedor = usuarios.getMaximo();
    }
}
