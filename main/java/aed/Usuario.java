package aed;

public class Usuario implements Comparable<Usuario> {
    private int id; 
    private int balance; 

    // Constructor de Usuario.
    public Usuario(int id) {
        this.id = id;
        this.balance = 0;
    }

    // Agrega un monto al balance del usuario.
    public void agregarBalance(int monto) {
        this.balance += monto;
    }

    // Devuelve el ID del usuario.
    public int getId() {
        return id;
    }

    // Devuelve el balance del usuario.
    public int getBalance() {
        return balance;
    }

    @Override
    public int compareTo(Usuario otro) {
        if (this.balance != otro.balance) {
            return Integer.compare(this.balance, otro.balance);
        }
        // En empate de balance, menor id es mayor
        return Integer.compare(otro.id, this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario otro = (Usuario) obj;
        return id == otro.id && balance == otro.balance;
    }
}
