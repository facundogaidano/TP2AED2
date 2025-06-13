package aed;
import java.util.ArrayList;

/**
 * Implementación de un heap máximo genérico.
 * Optimizado para acceso O(1) a elementos Usuario mediante índices internos.
 * No utiliza un Handle externo, cada Usuario tiene su propio índice en el heap.
 */
public class Heap<T extends Comparable<T>> {
    private ArrayList<T> heap; // Lista que almacena los elementos del heap
    private ArrayList<Integer> listaHandle;


    /**
     * Constructor que inicializa un heap vacío con la capacidad especificada.
     * Complejidad: O(1)
     */
    public Heap(int capacidadInicial) {
        heap = new ArrayList<>(capacidadInicial);
        listaHandle = new ArrayList<>();
        // Initialize with enough capacity
        for (int i = 0; i < capacidadInicial + 1; i++) {
            listaHandle.add(-1);
        }
    }

    /**
     * Constructor que crea un heap a partir de una lista de elementos.
     * Utiliza el algoritmo de Floyd para construcción en tiempo lineal.
     * https://www.cubawiki.com.ar/images/e/e3/AED2_apunte_final_2021.pdf (página 37).
     * Complejidad: O(n)
     */
    public Heap(ArrayList<T> elementos) {
        this.heap = new ArrayList<>(elementos);
        this.listaHandle = new ArrayList<>();

        int maxId = 0;
        for (T elemento : elementos) {
            if (elemento instanceof Usuario) {
                maxId = Math.max(maxId, ((Usuario)elemento).getId());
            }
        }

        for (int i = 0; i <= maxId; i++) {
            listaHandle.add(-1);
        }

        for (int indice = 0; indice < heap.size(); indice++) {
            if (heap.get(indice) instanceof Usuario) {
                Usuario u = (Usuario)heap.get(indice);
                listaHandle.set(u.getId(), indice);
            }
        }

        // Construir el heap usando el algoritmo de Floyd - O(n)
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    /**
     * Agrega un elemento al heap y mantiene la propiedad de heap.
     * Complejidad: O(log n)
     */
    public void agregarAlHeap(T elemento) {
        heap.add(elemento);
        int indice = heap.size() - 1;

        // Si es un Usuario, almacenar su posición para acceso - O(1)
        if (elemento instanceof Usuario) {
            Usuario u = (Usuario) elemento;
            
            listaHandle.set(u.getId(), indice);
        }

        heapifyUp(indice);
    }
    
    /**
     * Extrae y devuelve el elemento máximo del heap.
     * Complejidad: O(log n)
     */
    public T sacarMaximo() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap vacío");
        }
        T maxElem = heap.get(0);

        if (maxElem instanceof Usuario) {
            listaHandle.set(((Usuario)maxElem).getId(), -1); // Ya no está en el heap
        }

        T ultimoElemento = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, ultimoElemento);
            
            // Actualizar índice si es Usuario.
            if (ultimoElemento instanceof Usuario) {
                listaHandle.set(((Usuario) ultimoElemento).getId(), 0);
            }
            
            // Reorganizar el heap hacia abajo desde la raíz. - O(log n)
            heapifyDown(0);
        }
        
        return maxElem;
    }

    /**
     * Reorganiza el heap hacia arriba desde la posición indicada.
     * Complejidad: O(log n)
     */
    private void heapifyUp(int indice) {
        T elemento = heap.get(indice);
        
        // Bucle para subir el elemento hasta su posición correcta - O(log n)
        while (indice > 0) {
            int indicePadre = (indice - 1) / 2;
            T padre = heap.get(indicePadre);
            
            if (elemento.compareTo(padre) <= 0) {
                break;
            }
            
            // Intercambiar con padre
            heap.set(indice, padre);
            heap.set(indicePadre, elemento);
            
            // Actualizar índices si son Usuarios
            if (padre instanceof Usuario) {
                listaHandle.set(((Usuario) padre).getId(), indice);
            }
            if (elemento instanceof Usuario) {
                listaHandle.set(((Usuario) elemento).getId(), indicePadre);
            }
            
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
        
        T elemento = heap.get(indice);
        
        // Bucle para bajar el elemento hasta su posición correcta - O(log n)
        while (true) {
            int hijoIzquierdoId = 2 * indice + 1;
            int hijoDerechoId = 2 * indice + 2;
            int idDelMasLargo = indice;
            
            // Verificar hijo izquierdo
            if (hijoIzquierdoId < tamaño && heap.get(hijoIzquierdoId).compareTo(heap.get(idDelMasLargo)) > 0) {
                idDelMasLargo = hijoIzquierdoId;
            }
            
            // Verificar hijo derecho
            if (hijoDerechoId < tamaño && heap.get(hijoDerechoId).compareTo(heap.get(idDelMasLargo)) > 0) {
                idDelMasLargo = hijoDerechoId;
            }
            
            if (idDelMasLargo == indice) {
                break; // El elemento está en su posición correcta
            }
            
            // Intercambiar con el hijo mayor
            T masLargo = heap.get(idDelMasLargo);
            heap.set(idDelMasLargo, elemento);
            heap.set(indice, masLargo);
            
            // Actualizar índices si son Usuarios
            if (elemento instanceof Usuario) {
                listaHandle.set(((Usuario) elemento).getId(), idDelMasLargo);
            }
            if (masLargo instanceof Usuario) {
                listaHandle.set(((Usuario) masLargo).getId(), indice);
            }
            
            indice = idDelMasLargo;
        }
    }

    /**
     * Actualiza la posición de un Usuario en el heap tras un cambio de prioridad.
     * Utiliza acceso directo O(1) al índice almacenado en el Usuario.
     * Complejidad: O(log P)
     */
    public void actualizarPosicionUsuario(Usuario usuario) {
        int id = usuario.getId();

        while (listaHandle.size() <= id) {
            listaHandle.add(-1);
        }

        int indice = listaHandle.get(id);
        
        if (indice >= 0 && indice < heap.size() && heap.get(indice) instanceof Usuario) {
            // Si el usuario está en el heap, reorganizar hacia arriba y abajo - O(log P)
            if (heap.get(indice) == usuario) {
                heapifyUp(indice);
                // Obtener nuevo índice después de heapifyUp
                indice = listaHandle.get(id);
                heapifyDown(indice);
            }
        }
    }

    /**
     * Devuelve el elemento máximo del heap sin extraerlo.
     * Complejidad: O(1)
     */
    public T getMaximo() {
        if (heap.isEmpty()) { return null; }
        return heap.get(0);
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
