package aed;
import java.util.ArrayList;

/**
 * Implementación de un heap máximo genérico.
 */
public class Heap<T extends Comparable<T>> {
    private ArrayList<Par> heap;                    // Lista que almacena los elementos del heap
    private ArrayList<Handle> listaHandle;          // Lista de handles para acceso O(1) a ID Usuario

    /**
     * Clase interna para manejar los elementos del heap.
     * Almacena el valor y el índice del elemento en el heap.
     */
    public class Par {
        T valor;
        int indice;
        
        Par(T v, int i){
            valor = v;
            indice = i;
        }

        public int getIndice() {
            return this.indice;
        }

        public T getValor() {
            return this.valor;
        }
    }

    /**
     * Constructor que inicializa un heap vacío con la capacidad especificada.
     * Complejidad: O(n)
     */
    public Heap(int capacidadInicial) {
        heap = new ArrayList<>(capacidadInicial);
        listaHandle = new ArrayList<>();
        for (int i = 0; i < capacidadInicial + 1; i++) {
            listaHandle.add(new Handle(-1));
        }
    }

    /**
     * Constructor que crea un heap a partir de una lista de elementos.
     * Utiliza el algoritmo de Floyd para construcción en tiempo lineal.
     * https://www.cubawiki.com.ar/images/e/e3/AED2_apunte_final_2021.pdf (página 37).
     * Complejidad: O(n)
     */
    public Heap(ArrayList<T> elementos) {
        int n = elementos.size();
        this.heap = new ArrayList<>(n);
        
        // Inicializar el heap y la lista de handles
        this.listaHandle = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            listaHandle.add(new Handle(i));
            heap.add(new Par(elementos.get(i), i));
        }

        // Construir el heap usando el algoritmo de Floyd - O(n)
        construirHeap();
    }

    /**
     * Agrega un elemento al heap y mantiene la propiedad de heap.
     * Complejidad: O(1)
     */
    public void agregarElemento(T elemento, int handleId) {
        int indiceEnHeap = heap.size();
        heap.add(new Par(elemento, handleId));
        listaHandle.get(handleId).setIndice(indiceEnHeap);
    }

    /**
     * Construye el heap a partir de los elementos actuales.
     * Complejidad: O(n)
    */
    public void construirHeap() {
        // Construir el heap usando el algoritmo de Floyd - O(n)
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }
    
    /**
     * Extrae y devuelve el elemento máximo del heap.
     * Complejidad: O(log n)
     */    
    public T sacarMaximo() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap vacío");
        }

        // Guardar el elemento máximo (raíz del heap)
        // y reemplazarlo con el último elemento del heap.
        Par maxElemento = heap.get(0);
        Par ultimoElemento = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, ultimoElemento);
            heapifyDown(0);
        }

        return maxElemento.getValor();
    }


    /**
     * Reorganiza el heap hacia arriba desde la posición indicada.
     * Complejidad: O(log n)
     */
    private void heapifyUp(int indice) {
        Par elemento = heap.get(indice);
        
        // Bucle para subir el elemento hasta su posición correcta - O(log n)
        while (indice > 0) {
            int indicePadre = (indice - 1) / 2;
            Par padre = heap.get(indicePadre);
            
            if (elemento.getValor().compareTo(padre.getValor()) <= 0) {
                break;
            }
            
            // Intercambiar con padre
            heap.set(indice, padre);
            heap.set(indicePadre, elemento);
            
            // Actualizar índices si son Usuarios
            listaHandle.get(padre.getIndice()).setIndice(indice);
            listaHandle.get(elemento.getIndice()).setIndice(indicePadre);
            
            indice = indicePadre;
        }
    }

    /**
     * Reorganiza el heap hacia abajo desde la posición indicada.
     * Complejidad: O(log n)
     */
    private void heapifyDown(int indice) {
        int tamaño = heap.size();
        if (indice >= tamaño) return;
        
        Par elemento = heap.get(indice);
        
        // Bucle para bajar el elemento hasta su posición correcta - O(log n)
        while (true) {
            int hijoIzquierdoId = 2 * indice + 1;
            int hijoDerechoId = 2 * indice + 2;
            int raizId = indice;
            
            // Verificar hijo izquierdo
            if (hijoIzquierdoId < tamaño && heap.get(hijoIzquierdoId).getValor().compareTo(heap.get(raizId).getValor()) > 0) {
                raizId = hijoIzquierdoId;
            }
            
            // Verificar hijo derecho
            if (hijoDerechoId < tamaño && heap.get(hijoDerechoId).getValor().compareTo(heap.get(raizId).getValor()) > 0) {
                raizId = hijoDerechoId;
            }
            
            if (raizId == indice) {
                break; // El elemento está en su posición correcta
            }
            
            // Intercambiar con el hijo mayor
            Par raizIdCopia = heap.get(raizId);
            heap.set(raizId, elemento);
            heap.set(indice, raizIdCopia);
            
            // Actualizar índices
            listaHandle.get(elemento.getIndice()).setIndice(raizId);
            listaHandle.get(raizIdCopia.getIndice()).setIndice(indice);
            
            indice = raizId;
        }
    }

    /**
     * Actualiza la posición de un Elemento en el heap tras un cambio de prioridad.
     * Complejidad: O(log P)
     */
    public void actualizarPosicion(int indiceHandle) {
        if (indiceHandle < 0 || indiceHandle >= listaHandle.size()) {
            return;
        }

        // Obtener el índice del elemento en el heap usando el handle
        int indice = listaHandle.get(indiceHandle).getIndice();
        if (indice >= 0 && indice < heap.size()) {
            // Si está en el heap, reorganizar hacia arriba y abajo - O(log P)
            heapifyUp(indice);
            // Obtener nuevo índice después de heapifyUp
            indice = listaHandle.get(indiceHandle).getIndice();
            heapifyDown(indice);
        }
    }

    /**
     * Devuelve el elemento máximo del heap sin extraerlo.
     * Complejidad: O(1)
     */
    public T getMaximo() {
        if (heap.isEmpty()) { return null; }
        return heap.get(0).getValor();
    }

    /**
     * Devuelve el número de elementos en el heap.
     * Complejidad: O(1)
     */
    public int getLongitud() { return heap.size(); }
    
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
