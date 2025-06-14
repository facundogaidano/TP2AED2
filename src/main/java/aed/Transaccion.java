package aed;

import java.util.ArrayList;

/*
 * Representa una transacción en el sistema Berretacoin.
 * Implementa Comparable para permitir organización en heap.
 */
public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;

    /**
     * Constructor para crear una nueva transacción.
     * Complejidad: O(1)
     */
    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    /**
     * Aplica un conjunto de transacciones a los usuarios afectados y actualiza el heap.
     * Complejidad: O(n + K log P) donde K es el número de usuarios afectados.
     * Peor caso: O(n log P) si K = n.
     */
    public static void aplicarTransacciones(Heap<Usuario> heap, Transaccion[] transacciones, Usuario[] usuariosArray) {
        // Registro de usuarios modificados para actualizar su prioridad.
        boolean[] usuariosActualizados = new boolean[usuariosArray.length];
        ArrayList<Usuario> usuariosAfectados = new ArrayList<>(); // Es para almacenar los usuarios a actualizar en el heap.
        
        // Aplicar todas las transacciones y actualizar balances - O(n)
        for (int i = 0; i < transacciones.length; i++) {
            Transaccion trx = transacciones[i];
            if (trx.id_comprador() != 0) {
                Usuario comprador = usuariosArray[trx.id_comprador()];
                comprador.agregarBalance(-trx.monto());
                
                // Si el usuario no fue actualizado, el valor de su indice en usuariosActualizados sera False, entonces
                // lo actualizamos a True.
                if (!usuariosActualizados[trx.id_comprador()]) {
                    usuariosActualizados[trx.id_comprador()] = true;
                    usuariosAfectados.add(comprador); // Los agrega a lista de usuarios a actualizar.
                }
            }
            
            Usuario vendedor = usuariosArray[trx.id_vendedor()];
            vendedor.agregarBalance(trx.monto());

            // Repetimos la misma logica que para los usuarios compradores.
            if (!usuariosActualizados[trx.id_vendedor()]) { // O(1)
                usuariosActualizados[trx.id_vendedor()] = true;
                usuariosAfectados.add(vendedor); // Los agrega a lista de usuarios a actualizar.
            }
        }
        
        // Actualizar el heap solo para los usuarios en la lista - O(K log P)
        for (int i = 0; i < usuariosAfectados.size(); i++) {
            Usuario usuario = usuariosAfectados.get(i);
            heap.actualizarPosicionUsuario(usuario);
        }
    }

    /**
     * Revierte los balances de los usuarios involucrados en la última transacción de mayor valor.
     * Complejidad: O(log P)
     */
    public static void revertirTransaccion(Heap<Usuario> heap, Transaccion trx, Usuario[] usuariosArray) {
        if (trx.id_comprador() != 0) {
            Usuario comprador = usuariosArray[trx.id_comprador()];
            comprador.agregarBalance(trx.monto());
            heap.actualizarPosicionUsuario(comprador);
        }
        
        Usuario vendedor = usuariosArray[trx.id_vendedor()];
        vendedor.agregarBalance(-trx.monto());
        heap.actualizarPosicionUsuario(vendedor);
        
    }

    /**
     * Obtiene el monto de la transacción.
     * Complejidad: O(1)
     */
    public int monto() { return monto; }

    /**
     * Obtiene el ID del comprador.
     * Complejidad: O(1)
     */
    public int id_comprador() { return id_comprador; }
    
    /**
     * Obtiene el ID del vendedor.
     * Complejidad: O(1)
     */
    public int id_vendedor() { return id_vendedor; }

    /**
     * Obtiene el ID de la transacción.
     * Complejidad: O(1)
     */
    public int id() { return id; }

    /**
     * Compara esta transacción con otra por monto y luego por ID.
     * Complejidad: O(1)
     */
    @Override
    public int compareTo(Transaccion otro) { // Asumo que no va a haber Overflow de Integer.
        if (this.monto != otro.monto) {
            // Para un max heap, debe restar otro.monto - this.monto
            // para priorizar montos mayores
            return this.monto - otro.monto;
        }
        // En caso de empate por monto, el de menor ID es prioritario
        return this.id - otro.id;
    }

    /**
     * Verifica si esta transacción es igual a otro objeto.
     * Complejidad: O(1)
     */
    @Override
    public boolean equals(Object otro){
        if (this == otro) return true;
        if (otro == null || getClass() != otro.getClass()) return false;
        Transaccion otra = (Transaccion) otro;
        return id == otra.id && 
               id_comprador == otra.id_comprador && 
               id_vendedor == otra.id_vendedor && 
               monto == otra.monto;
    }
}