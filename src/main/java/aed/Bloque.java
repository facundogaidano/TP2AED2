package aed;

import java.util.ArrayList;

/**
 * Representa un bloque en la cadena de Berretacoin.
 * Mantiene una estructura optimizada para operaciones sobre transacciones.
 */
public class Bloque {
    private Heap<Transaccion> heapTransacciones;        // Max heap para transacción de mayor valor.
    private ArrayList<Transaccion> listaTransacciones;  // Lista para el orden original.
    //private int idBloque;                                Lo necesitamos?
    private int montoTotal;                             // Suma de montos para cálculo O(1)
    private int cantTrx;                                // Contador de transacciones no-creación.

    /**
     * Constructor de bloque con un conjunto de transacciones.
     * Complejidad: O(n)
     */
    public Bloque(int idBloque, Transaccion[] listaTrx) {
        // this.idBloque = idBloque;
        this.listaTransacciones = new ArrayList<>(listaTrx.length);
        this.cantTrx = 0;
        this.montoTotal = 0;

        // Inicializamos la lista y calculamos estadísticas - O(n)
        for (int i = 0; i < listaTrx.length; i++) {
            Transaccion t = listaTrx[i];
            this.listaTransacciones.add(t);
            t.setIndexEnBloque(i);

            if (t.id_comprador() != 0) {
                montoTotal += t.monto();
                cantTrx++;
            }
        }

        // Construimos el heap - O(n)
        ArrayList<Transaccion> heapList = new ArrayList<>(listaTransacciones);
        this.heapTransacciones = new Heap<>(heapList);
    }

    /**
     * Devuelve las transacciones del bloque como un array.
     * Complejidad: O(n)
     */
    public Transaccion[] getTransacciones() { // O(N)
        return listaTransacciones.toArray(new Transaccion[0]);
    }

    /**
     * Devuelve la transacción con mayor valor del bloque.
     * Complejidad: O(1)
     */
    public Transaccion transaccionMayorValor() { // O(1)
        if (heapTransacciones.getLongitud() == 0) return null;
        return heapTransacciones.getMaximo();
    }

    /**
     * Elimina y devuelve la transacción de mayor valor del bloque.
     * Mantiene el orden relativo de las transacciones restantes.
     * Complejidad: O(log n) para el heap, O(n) para actualizar lista
     */
    public Transaccion eliminarMayorValor() {
        if (heapTransacciones.getLongitud() == 0) return null;
        
        // Obtener la transacción de mayor valor - O(1)
        Transaccion max = heapTransacciones.sacarMaximo();
        
        if (max != null) {
            // Buscar y eliminar de la lista manteniendo orden relativo - O(n)
            for (int i = 0; i < listaTransacciones.size(); i++) {
                if (listaTransacciones.get(i).equals(max)) {
                    // Eliminar transacción manteniendo orden original
                    listaTransacciones.remove(i);
                    
                    // Actualizar índices de transacciones siguientes
                    for (int j = i; j < listaTransacciones.size(); j++) {
                        listaTransacciones.get(j).setIndexEnBloque(j);
                    }
                    
                    // Actualizar los valores si no era de creación
                    if (max.id_comprador() != 0) {
                        montoTotal -= max.monto();
                        cantTrx--;
                    }
                    break;
                }
            }
            
            // Marcar como eliminada de la lista
            max.setIndexEnBloque(-1);
        }
        
        return max;
    }

    /**
     * Calcula el monto medio de las transacciones no-creación.
     * Complejidad: O(1)
     */
    public int montoMedio() { // O(1)
        return cantTrx == 0 ? 0 : montoTotal / cantTrx;
    }

//Lo necesitamos?
/*     // Devuelve la cantidad de transacciones en el bloque con complejidad O(1).
    public int cantidad() {
        return heapTransacciones.getLongitud();
    }

    // Devuelve el ID del bloque con complejidad O(1).
    public int getIdBloque() {
        return idBloque;
    } */
}
