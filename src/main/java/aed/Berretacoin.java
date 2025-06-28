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
    private Bloque ultimoBloque;        // Último bloque agregado.

    /**
     * Constructor que inicializa el sistema con un número dado de usuarios.
     * Complejidad: O(P)
     */
    public Berretacoin(int n_usuarios){
        // Array de usuarios indexado desde 1.
        this.usuariosArray = new Usuario[n_usuarios + 1]; 
        this.usuarios = new Heap<>(n_usuarios); // O(P)

        // Inicializar todos los usuarios - O(P)
        for (int i = 0; i < n_usuarios; i++) { // O(P)
            Usuario user = new Usuario(i + 1);
            usuariosArray[i + 1] = user;
            usuarios.agregarElemento(user, user.getId()); // O(1)
        }

        // Construir el heap de usuarios - O(P)
        usuarios.construirHeap(); // O(P)
    }

    /**
     * Actualiza las posiciones de los usuarios en el heap después de cambios en sus balances.
     * Complejidad: O(log P)
     */
    private void actualizarPosicionesUsuarios(ArrayList<Usuario> usuariosAfectados) {
        for (int i = 0; i < usuariosAfectados.size(); i++) { // O(1) máximo de usuarios afectados son 2.
            Usuario usuario = usuariosAfectados.get(i);
            usuarios.actualizarPosicion(usuario.getId()); // O(log p)
        }
    }

    /**
     * Revierte los balances de los usuarios involucrados en la última transacción de mayor valor.
     * Complejidad: O(1)
     */
    private static void revertirTransaccion(Transaccion trx, Usuario[] usuariosArray) {

        if (trx.id_comprador() != 0) {
            Usuario comprador = usuariosArray[trx.id_comprador()];
            comprador.agregarBalance(trx.monto());
        }
        
        Usuario vendedor = usuariosArray[trx.id_vendedor()];
        vendedor.agregarBalance(-trx.monto());
    }

    /**
     * Agrega un bloque de transacciones a la cadena y actualiza los balances.
     * Complejidad: O(n * log P)
     */
    public void agregarBloque(Transaccion[] transacciones){
        // Crear nuevo bloque y actualizar referencias - O(n)
        Bloque bloque = new Bloque(transacciones);
        this.ultimoBloque = bloque;
        
        // Aplicar transacciones y actualizar balances de usuarios - O(n)
        for (int i = 0; i < transacciones.length; i++) {
            Transaccion trx = transacciones[i];
            if (trx.id_comprador() != 0) {
                Usuario comprador = usuariosArray[trx.id_comprador()];
                comprador.agregarBalance(-trx.monto());
            }
            
            Usuario vendedor = usuariosArray[trx.id_vendedor()];
            vendedor.agregarBalance(trx.monto());
        }

        for (int i = 0; i < transacciones.length; i++) { // O(n * log P)
            Transaccion trx = transacciones[i];
            // Obtener usuarios afectados por la transacción
            ArrayList<Usuario> usuariosAfectados = trx.usuariosAfectados(usuariosArray);
            
            // Actualizar posiciones de los usuarios afectados en el heap - O(log P)
            actualizarPosicionesUsuarios(usuariosAfectados);
        }
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
        if (usuarios.getLongitud() == 0) return 0;
        return usuarios.getMaximo().getId();
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
        revertirTransaccion(hackeada, usuariosArray);

        // Obtener los usuarios afectados por la transacción hackeada - O(1)
        ArrayList<Usuario> usuariosAfectados = hackeada.usuariosAfectados(usuariosArray);
    
        // Actualizar posiciones en el heap - O(log P)
        actualizarPosicionesUsuarios(usuariosAfectados);
    }
}
