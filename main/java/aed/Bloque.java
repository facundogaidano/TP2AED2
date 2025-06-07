package aed;

import java.util.ArrayList;

public class Bloque {
    private Heap<Transaccion> heapTransacciones; // Usamos un Heap para mantener las transacciones ordenadas por monto.
    private ArrayList<Transaccion> listaTransacciones; // Con esto evitamos tener O(n^2) al eliminar transacciones.
    private int idBloque;
    private int montoTotal; // Lo usamos para que el monto medio sea O(1).
    private int cantTrx; // Cantidad de transacciones que no son de creación (id_comprador != 0).

    // Constructor de Bloque.
    public Bloque(int idBloque, Transaccion[] listaTrx) {
        this.idBloque = idBloque;
        this.heapTransacciones = new Heap<>(listaTrx.length); // Inicializo el heap con la capacidad de las transacciones.
        this.listaTransacciones = new ArrayList<>(); // Inicializo la lista de transacciones.
        this.cantTrx = 0;
        this.montoTotal = 0;
        for (int i = 0; i < listaTrx.length; i++) { // Recorro las transacciones y las agrego al heap y a la lista.
            Transaccion t = listaTrx[i];
            heapTransacciones.agregarAlHeap(t);
            this.listaTransacciones.add(t);
            if (t.id_comprador() != 0) { // Evitamos agregar TransaccionesCreación y contarlas en el monto total.
                montoTotal += t.monto();
                cantTrx++;
            }
        }
    }

    // Devuelvo las transacciones del bloque como un array.
    public Transaccion[] getTransacciones() {
        return listaTransacciones.toArray(new Transaccion[0]);
    }

    // Devuelve la transacción con mayor valor del bloque.
    public Transaccion transaccionMayorValor() {
        if (heapTransacciones.getLongitud() == 0) return null;
        return heapTransacciones.getMaximo();
    }

    // Elimina la transacción de mayor valor del bloque.
    public Transaccion eliminarMayorValor() {
        if (heapTransacciones.getLongitud() == 0) return null;
        Transaccion max = heapTransacciones.getMaximo();
        heapTransacciones.sacarMaximo();
        for (int i = 0; i < listaTransacciones.size(); i++) {
            if (listaTransacciones.get(i).equals(max)) {
                listaTransacciones.remove(i);
                break;
            }
        }
        if (max.id_comprador() != 0) {
            montoTotal -= max.monto();
            cantTrx--;
        }
        return max;
    }

    // Calcula el monto medio de las transacciones.
    public int montoMedio() {
        return cantTrx == 0 ? 0 : montoTotal / cantTrx;
    }

    // Devuelve la cantidad de transacciones en el bloque.
    public int cantidad() {
        return heapTransacciones.getLongitud();
    }

    // Devuelve el ID del bloque.
    public int getIdBloque() {
        return idBloque;
    }
}
