package aed;

import java.util.ArrayList;

/**
 * Representa un bloque en la cadena de Berretacoin.
 * Mantiene una estructura para operar sobre transacciones.
 */
public class Bloque {
    private Heap<Transaccion> heapTransacciones;        // Max heap para transacción de mayor valor.
    private ListaEnlazada<Transaccion> listaEnlazadaTransacciones;  // Lista para el orden original.
    private int montoTotal;                             // Suma de montos para cálculo O(1)
    private int cantTrx;                                // Contador de transacciones no-creación.

    /**
     * Constructor de bloque con un conjunto de transacciones.
     * Complejidad: O(n)
     */
    public Bloque(int idBloque, Transaccion[] listaTrx) {
        this.listaEnlazadaTransacciones = new ListaEnlazada<Transaccion>();
        this.cantTrx = 0;
        this.montoTotal = 0;

        // Inicializamos la lista y calculamos estadísticas - O(n)
        for (int i = 0; i < listaTrx.length; i++) {
            Transaccion t = listaTrx[i];
            this.listaEnlazadaTransacciones.agregarAtras(t);

            if (t.id_comprador() != 0) {
                montoTotal += t.monto();
                cantTrx++;
            }
        }

        // Construimos el heap - O(n)
        ArrayList<Transaccion> heapLista = new ArrayList<>(listaEnlazadaTransacciones.longitud());
        Iterador<Transaccion> it = listaEnlazadaTransacciones.iterador();

        while (it.haySiguiente()) {
            Transaccion trx = it.siguiente();
            heapLista.add(trx);
        }
        this.heapTransacciones = new Heap<>(heapLista);
    }

    /**
     * Devuelve las transacciones del bloque como un array.
     * Complejidad: O(n)
     */
    public Transaccion[] getTransacciones() { // O(n)
        ArrayList<Transaccion> nuevaLista = new ArrayList<>(listaEnlazadaTransacciones.longitud());
        Iterador<Transaccion> it = listaEnlazadaTransacciones.iterador();
        
        int c = 0;
        while (it.haySiguiente()) {
            nuevaLista.add(c, it.siguiente());
            c++;
        }
        // 
        return nuevaLista.toArray(new Transaccion[0]);
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
     * Complejidad: O(log n)
     */
    public Transaccion eliminarMayorValor() {
        if (heapTransacciones.getLongitud() == 0) return null;
        
        // Obtener la transacción de mayor valor - O(1)
        Transaccion max = heapTransacciones.sacarMaximo(); // O(log n)

        if (max != null) {
            if (listaEnlazadaTransacciones.obtener(max.id()).equals(max)) {
                // Eliminar transacción manteniendo orden original
                listaEnlazadaTransacciones.eliminar(max.id()); // O(1)
                
                // Actualizar los valores si no era de creación
                if (max.id_comprador() != 0) {
                    montoTotal -= max.monto();
                    cantTrx--;
                }
            }
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
}
