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
     * Obtiene los usuarios afectados por esta transacción.
     * Complejidad: O(1)
     */
    public ArrayList<Usuario> usuariosAfectados(Usuario[] usuariosArray) {
        ArrayList<Usuario> usuariosAfectados = new ArrayList<>(2); // Máximo son 2 los usuarios involucrados. O(1)

        if (id_comprador != 0) {
            Usuario comprador = usuariosArray[id_comprador];
            if (!usuariosAfectados.contains(comprador)) { // Verificar si ya está en la lista
                usuariosAfectados.add(comprador);
            }
        }
        
        Usuario vendedor = usuariosArray[id_vendedor];
        if (!usuariosAfectados.contains(vendedor)) { // Verificar si ya está en la lista
            usuariosAfectados.add(vendedor);
        }

        return usuariosAfectados;
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