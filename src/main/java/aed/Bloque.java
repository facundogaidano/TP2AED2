package aed;

import java.util.ArrayList;

/**
 * Representa un bloque en la cadena de Berretacoin.
 * Mantiene una estructura para operar sobre transacciones.
 */
public class Bloque {
    private Heap<Transaccion> heapTransacciones;                        // Max heap para transacción de mayor valor.
    private ListaEnlazada<Transaccion> listaEnlazadaTransacciones;      // Lista para el orden original.
    private int montoTotal;                                             // Suma de montos para cálculo O(1)
    private int cantTrx;                                                // Contador de transacciones no-creación.
    private ListaEnlazada<Transaccion>.Handle[] handlesTransacciones;   // Array de handles

    /**
     * Constructor de bloque con un conjunto de transacciones.
     * Complejidad: O(n)
     */
    public Bloque(Transaccion[] listaTrx) {
        this.listaEnlazadaTransacciones = new ListaEnlazada<Transaccion>();
        this.cantTrx = 0;
        this.montoTotal = 0;

        // Encontrar el ID máximo de transacción - O(n)
        int maxId = 0;
        for (int i = 0; i < listaTrx.length; i++) {
            Transaccion t = listaTrx[i];
            maxId = Math.max(maxId, t.id());
        }
        
        // Inicializar el array de handles - O(n)
        this.handlesTransacciones = new ListaEnlazada.Handle[maxId + 1];
        ArrayList<Transaccion> heapLista = new ArrayList<>(listaTrx.length);

        // Inicializamos la lista y calculamos estadísticas - O(n)
        for (int i = 0; i < listaTrx.length; i++) {
            Transaccion t = listaTrx[i];
            listaEnlazadaTransacciones.agregarAtras(t);
            // Guardar el handle para acceso O(1)
            handlesTransacciones[t.id()] = obtenerHandleDelUltimo();
            heapLista.add(t);

            if (t.id_comprador() != 0) {
                montoTotal += t.monto();
                cantTrx++;
            }
        }

        // Crear el heap de transacciones - O(n)
        this.heapTransacciones = new Heap<>(heapLista);
    }

    /**
     * Devuelve el handle del último elemento de la lista enlazada.
     * Este método es O(1) ya que simplemente crea un handle para el último nodo.
     */
    private ListaEnlazada<Transaccion>.Handle obtenerHandleDelUltimo() {
        int ultimoIndice = listaEnlazadaTransacciones.longitud() - 1;
        return listaEnlazadaTransacciones.new Handle(listaEnlazadaTransacciones.getNodo(ultimoIndice));
    }

    /**
     * Devuelve las transacciones del bloque como un array.
     * Complejidad: O(n)
     */
    public Transaccion[] getTransacciones() {
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
    public Transaccion transaccionMayorValor() {
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
        
        // Obtener la transacción de mayor valor - O(log n)
        Transaccion max = heapTransacciones.sacarMaximo();
        
        if (max != null) {
            // Obtener el handle de la transacción - O(1)
            ListaEnlazada<Transaccion>.Handle handle = handlesTransacciones[max.id()];
            
            if (handle != null) {
                // Eliminar directamente usando el handle - O(1)
                handle.eliminar();
                
                // Actualizar estadísticas
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
