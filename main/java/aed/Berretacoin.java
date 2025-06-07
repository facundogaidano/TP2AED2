package aed;

public class Berretacoin {
    private Heap<Usuario> usuarios; // Un Heap de usuarios, para que el maximoTenedor sea O(1) y agregar un usuario sea O(P).
    private Usuario[] usuariosArray; // Para evitar el O(P^2) en recalcular el maxTenedor. Con esto es O(P) el agregar y O(1) el pedir el maximoTenedor.
    private Usuario maxTenedor; // El usuario con mayor balance, si hay empate, el de menor id.
    private int contadorBloques = 0;
    private Bloque ultimoBloque; // El último bloque agregado a la cadena de bloques. 

    // Constructor de Berretacoin.
    public Berretacoin(int n_usuarios){
        this.usuarios = new Heap<>(n_usuarios);
        this.usuariosArray = new Usuario[n_usuarios + 1]; // +1 porque los usuarios empiezan desde el id 1.
        this.maxTenedor = null;
        for (int i = 0; i < n_usuarios; i++) {
            Usuario user = new Usuario(i + 1);
            usuarios.agregarAlHeap(user);
            usuariosArray[i + 1] = user;
            if (maxTenedor == null || user.getId() < maxTenedor.getId()) { // El menor id es el maximo tenedor en el caso inicial.
                maxTenedor = user;
            }
        }
    }

    // Agrega un Array de Transaccion al bloque y este también lo define como el último bloque.
    public void agregarBloque(Transaccion[] transacciones){
        Bloque bloque = new Bloque(contadorBloques, transacciones); 
        this.ultimoBloque = bloque;
        contadorBloques++;
        Transaccion.aplicarTransacciones(usuarios, transacciones, usuariosArray); // Aplica las transacciones a los usuarios.
        recalcularMaxTenedor(); // Recalcula el maxTenedor después de aplicar las transacciones.
    }

    // Devuelve el ID del último bloque de mayor valor.
    public Transaccion txMayorValorUltimoBloque(){
        if (ultimoBloque == null) return null;
        return ultimoBloque.transaccionMayorValor();
    }

    // Devuelve un array de las transacciones del último bloque.
    public Transaccion[] txUltimoBloque(){
        if (ultimoBloque == null) return new Transaccion[0];
        return ultimoBloque.getTransacciones();
    }

    // Devuelve el ID del usuario con mayor balance.
    public int maximoTenedor(){
        return maxTenedor.getId();
    }

    // Devuelve el monto medio de las transacciones del último bloque.
    public int montoMedioUltimoBloque(){
        if (ultimoBloque == null) return 0;
        return ultimoBloque.montoMedio();
    }

    public void hackearTx(){
        if (ultimoBloque == null) return;
        Transaccion hackeada = ultimoBloque.eliminarMayorValor(); // Eliminar la transacción de mayor valor del último bloque.
        if (hackeada == null) return;
        Transaccion.revertirTransaccion(usuarios, hackeada, usuariosArray); // Revertir la transacción hackeada.
        recalcularMaxTenedor(); // Recalcular el maxTenedor después de revertir la transacción.
    }

    /* Lo uso para actualizar el maxTenedor después de cada transacción.
     * Su complejidad es O(P) ya que recorre todos los usuarios, pero a la hora de
     * pedir el maximoTenedor() es O(1) ya que lo guarda en una variable.
     * El uso de usuariosArray es para evitar hacer un O(P^2) */
    private void recalcularMaxTenedor() {
        maxTenedor = null;
        for (int i = 1; i < usuariosArray.length; i++) {
            Usuario u = usuariosArray[i];
            if (maxTenedor == null ||
                u.getBalance() > maxTenedor.getBalance() ||
                (u.getBalance() == maxTenedor.getBalance() && u.getId() < maxTenedor.getId())) {
                maxTenedor = u;
            }
        }
    }
}
