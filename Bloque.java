package aed;

public class Bloque {
    private Heap transacciones;
    private int idBloque;
    private int montoTotal;
    private int cantTrx;

    public Bloque(Transaccion[] listaTrx) {
        // this.transacciones = new Heap(listaTrx);
        this.cantTrx = listaTrx.length;
        this.montoTotal = sumatoriaMontoTotal(listaTrx);
        if (idBloque < 3000) {
            this.montoTotal--;
            this.cantTrx--;
        }
    }

    private int sumatoriaMontoTotal(Transaccion[] listaTrx) {
        int contador = 0;
        for (int i = 0; i < cantTrx; i++) {
            contador += listaTrx[i].monto();
        }
        return contador;
    }
    // for trx +Montos
     // a la par, +1 (#trx)
}
