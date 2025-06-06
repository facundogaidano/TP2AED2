package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;

    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    @Override
    public int compareTo(Transaccion otro) {
        // Compara los montos, en caso de ser iguales compara por id.
        if (monto > otro.monto()) {
            return 1;
        } else if (monto < otro.monto()) {
            return -1;
        }

        if (id > otro.id()) {
            return 1;
        } else if (id < otro.id()) {
            return -1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object otro){
        // Compara las clases y en caso de ambas ser Transaccion compara los valores de las mismas en la funcion valoresIguales.
        boolean esNull = (otro == null);
        boolean claseDistinta = otro.getClass() != this.getClass();

        if (esNull || claseDistinta) {
            return false;
        }

        Transaccion otraTransaccion = (Transaccion) otro;
        
        return valoresIguales(otraTransaccion);
    }

    private boolean valoresIguales(Transaccion otraTransaccion) {

        return id == otraTransaccion.id() &&
                id_comprador == otraTransaccion.id_comprador() &&
                id_vendedor == otraTransaccion.id_vendedor() &&
                monto == otraTransaccion.monto();
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
}