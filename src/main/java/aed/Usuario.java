package aed;

/*
 * Representa un usuario dentro del sistema Berretacoin.
 * Implementa Comparable para poder ordenar en un heap por su balance.
 */
public class Usuario implements Comparable<Usuario> {
    private int id;             // ID del usuario, indexado desde 1.
    private int balance;        // Saldo actual del usuario

    /**
     * Constructor que inicializa un usuario con balance cero.
     * Complejidad: O(1)
     */
    public Usuario(int id) {
        this.id = id;
        this.balance = 0;
        // this.heapIndex = -1;
    }

    /**
     * Modifica el balance del usuario agregando o restando un monto.
     * Complejidad: O(1)
     */
    public void agregarBalance(int monto) {
        this.balance += monto;
    }

    /**
     * Devuelve el ID del usuario.
     * Complejidad: O(1)
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el balance actual del usuario.
     * Complejidad: O(1)
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Actualiza el índice del usuario en el heap.
     * Evitamos el uso de un Handle con este sistema.
     * Complejidad: O(1)
     */
    // public void setHeapIndex(int index) {
    //     this.heapIndex = index;
    // }

    // /**
    //  * Devuelve el índice del usuario en el heap.
    //  * Complejidad: O(1)
    //  */
    // public int getHeapIndex() {
    //     return this.heapIndex;
    // }

    /**
     * Compara este usuario con otro para ordenamiento en el heap.
     * Primero por balance, y en caso de empate, el de menor ID es prioritario.
     * Complejidad: O(1)
     */
    @Override
    public int compareTo(Usuario otro) { // Asumo que no va a haber Overflow de Integer.
        if (this.balance != otro.balance) {
            return this.balance - otro.balance;
        }
        return otro.id - this.id;
    }

    /**
     * Determina si este usuario es igual a otro objeto.
     * Dos usuarios son considerados iguales si tienen el mismo ID.
     * Complejidad: O(1)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario otro = (Usuario) obj;
        return id == otro.id;
    }
}
