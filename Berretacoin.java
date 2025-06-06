package aed;

public class Berretacoin {
    // private int trxCreacion;
    // private Heap usuarios; // https://www.digitalocean.com/community/tutorials/max-heap-java https://last9.io/blog/heaps-in-java/
    
    // private Bloque ultimoBloque; 
    // hacer clase Bloque que tenga dentro de si misma el atributo del Heap para trx
    // a su vez, la clase bloque deberia tener su metodo obtenerMax() donde saca la raiz del Heap transacciones
    

    public Berretacoin(int n_usuarios){
        // this.usuarios = new Heap[n_usuarios] // VER
        
    }

    public void agregarBloque(Transaccion[] transacciones){
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion txMayorValorUltimoBloque(){
        // return ultimoBloque.obtenerMax(); // VER --> ultimoBloque.transacciones.maximo() ponele
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion[] txUltimoBloque(){
        // 
        throw new UnsupportedOperationException("Implementar!");
    }

    public int maximoTenedor(){
       // return usuarios.maximo();
       throw new UnsupportedOperationException("Implementar!");
    }

    public int montoMedioUltimoBloque(){
        // return montoTotal - (trxCreacion) / cantTrx;
        throw new UnsupportedOperationException("Implementar!");
    }

    public void hackearTx(){
        // private hackeada = new Transaccion
        // hackeada = txMayorValorUltimoBloque()
        // ultimoBloque.borrar(hackeada) 
        // usuarios.actualizarSaldosHeap(hackeada.id_vendedor, -monto) // VER COMO HACER LOS NOMBRES
        // usuarios.actualizarSaldosHeap(hackeada.id_comprador, monto)
        throw new UnsupportedOperationException("Implementar!");
    }
}
