package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;

    // Constructor.
    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    /* Comprueba las Transacciones y agrega los montos a los usuarios especificos por su Id. 
     * Buscar otro nombre para el metodo, no es muy descriptivo.
     * usuariosArray se usa para evitar recorrer n^2 veces el Heap, con el Array lo hacemos n veces O(n) */
    public static void aplicarTransacciones(Heap<Usuario> heap, Transaccion[] transacciones, Usuario[] usuariosArray) {
        for (int i = 0; i < transacciones.length; i++) {
            Transaccion trx = transacciones[i];
            if (trx.id_comprador() != 0) {
                usuariosArray[trx.id_comprador()].agregarBalance(-trx.monto());
            }
            usuariosArray[trx.id_vendedor()].agregarBalance(trx.monto());
        }
    }

    /* Se usa para el hackearTx, es para devolver los fondos los usuarios de la transacción. 
     * Ahora con el usuariosArray es O(1) */
    public static void revertirTransaccion(Heap<Usuario> heap, Transaccion trx, Usuario[] usuariosArray) {
        if (trx.id_comprador() != 0) {
            usuariosArray[trx.id_comprador()].agregarBalance(trx.monto());
        }
        usuariosArray[trx.id_vendedor()].agregarBalance(-trx.monto());
    }

    public int monto() {
        return monto;
    }

    public int id_comprador() {
        return id_comprador;
    }
    
    public int id_vendedor() {
        return id_vendedor;
    }

    public int id() {
        return id;
    }

    @Override
    public int compareTo(Transaccion otro) {
        if (monto != otro.monto()) {
            return Integer.compare(monto, otro.monto());
        }
        // En caso de empate, ver id si son distinto.
        return Integer.compare(id, otro.id());
    }

    @Override
    public boolean equals(Object otro){
        if (this == otro) return true;
        if (!(otro instanceof Transaccion)) return false;
        Transaccion otraTrx = (Transaccion) otro; // Le cambio el tipo para que sea Transaccion y pueda compararlos.
        return id == otraTrx.id && id_comprador == otraTrx.id_comprador && id_vendedor == otraTrx.id_vendedor && monto == otraTrx.monto;
    }
}