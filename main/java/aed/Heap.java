package aed;
import java.util.*;

public class Heap<T extends Comparable<T>> {
    private List<T> heap; // Es un elemento generico para que se pueda usar para los bloques : Heap<Transaccion>; y para los usuarios : Heap<Usuario>.

    /* Constructor del Heap como un ArrayList. */
    public Heap(int capacidadInicial) {
        heap = new ArrayList<>(capacidadInicial);
    }

    private int padre(int i) {
        return i / 2;
    }

    private int hijoIzquierdo(int i) {
        return 2 * i;
    }

    private int hijoDerecho(int i) {
        return 2 * i + 1;
    }

    // Agrega un elemento al heap.
    public void agregarAlHeap(T elemento) {
        heap.add(elemento);
        int indice = heap.size() - 1;
        heapifyUp(indice);
    }
    
    // Saca el primer elemento del heap (el máximo) y lo elimina.
    public void sacarMaximo() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap vacío"); // Preguntar si puedo usar expeciones.
        }
        int ultimoId = heap.size() - 1;
        T ultimoElemento = heap.remove(ultimoId);
        if (ultimoId == 0) {
            return; // Evito que se rompa el heap si solo quedaba un elemento.
        }
        heap.set(0, ultimoElemento); 
        heapifyDown(0);
    }

    /* Queria una forma para que se puedan eliminar elementos dentro del Heap usando el Handle. */
    public void eliminarPorHandle(Handle handle) {
        int indice = handle.getIndice();
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap vacío"); // Preguntar si puedo usar expeciones.
        }
        if (indice < 0 || indice >= heap.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango"); // Preguntar si puedo usar expeciones.
        }
        int ultimoId = heap.size() - 1;
        if (indice == ultimoId) {
            heap.remove(ultimoId);
            return;
        }
        T lastElement = heap.remove(ultimoId);
        heap.set(indice, lastElement);

        // Ordeno el Heap después de eliminar el elemento.
        int padre = padre(indice);
        if (indice > 0 && heap.get(indice).compareTo(heap.get(padre)) > 0) {
            heapifyUp(indice);
        } else {
            heapifyDown(indice);
        }
    }

    private void heapifyUp(int indice) {
        while (indice > 0 && heap.get(padre(indice)).compareTo(heap.get(indice)) < 0) {
            T temp = heap.get(indice);
            heap.set(indice, heap.get(padre(indice)));
            heap.set(padre(indice), temp);
            indice = padre(indice);
        }
    }

    private void heapifyDown(int indice) {
        while (true) {
            int izquierda = hijoIzquierdo(indice);
            int derecha = hijoDerecho(indice);
            int masgrande = indice;

            if (izquierda < heap.size() && heap.get(izquierda).compareTo(heap.get(masgrande)) > 0) {
                masgrande = izquierda;
            }
            if (derecha < heap.size() && heap.get(derecha).compareTo(heap.get(masgrande)) > 0) {
                masgrande = derecha;
            }
            if (masgrande == indice) {
                break;
            }
            T temp = heap.get(indice);
            heap.set(indice, heap.get(masgrande));
            heap.set(masgrande, temp);
            indice = masgrande;
        }
    }

    // Devuelve el primer elemento del Heap.
    public T getMaximo() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap vacío"); // Preguntar si puedo usar expeciones.
        }
        return heap.get(0);
    }

    // Devuelve el tamaño del Heap.
    public int getLongitud() {
        return heap.size();
    }

    // Devuelve el elemento en la posición indicada por el Handle.
    public T getPorHandle(Handle handle) {
        return heap.get(handle.getIndice());
    }

    public class Handle {
        private int indice;

        public Handle(int indice) {
            this.indice = indice;
        }

        public int getIndice() {
            return indice;
        }

        public void setIndice(int indice) {
            this.indice = indice;
        }
    }
}
