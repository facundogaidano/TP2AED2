package aed;

import java.util.ArrayList;

public class ListaEnlazada<T> implements Secuencia<T> {
    // Completar atributos privados
    private Nodo primNodo;
    private Nodo ultNodo;
    private int longLista;
    private ArrayList<Nodo> listaNodoHandle;

    private class Nodo {
        T valor;
        Nodo ant;
        Nodo sig;

        Nodo(T v){ valor = v; }
    }

    public Nodo getNodo(int indice) {
        return listaNodoHandle.get(indice);
    }

    public int getIndiceNodo(Nodo nodo) {
        return listaNodoHandle.indexOf(nodo);
    }

    public ListaEnlazada() {
        primNodo = null;
        ultNodo = null;
        longLista = 0;
        listaNodoHandle = new ArrayList<Nodo>();
    }

    public int longitud() {
        return longLista;
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (longLista == 0) {
          primNodo = nuevo;
          ultNodo = nuevo;
        } else {
          ultNodo.sig = nuevo;
          nuevo.ant = ultNodo;
          ultNodo = nuevo;
        }
        longLista = longLista + 1;
        listaNodoHandle.add(nuevo);
    }

    public T obtener(int i) {
        return listaNodoHandle.get(i).valor;
    }

    public T proxNodo(Nodo i) {
        return i.sig.valor;
    }

    public boolean hayProxNodo(Nodo i) {
        return i.sig != null;
    }


    public void eliminar(int i) {
        Nodo actual = listaNodoHandle.get(i);
        if (actual.ant != null) {
            actual.ant.sig = actual.sig;
        } else {
            primNodo = actual.sig;
        }
        if (actual.sig != null) {
            actual.sig.ant = actual.ant;
        } else {
            ultNodo = actual.ant;
        }
        listaNodoHandle.remove(i);
        longLista = longLista - 1;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primNodo;
        for (int i = 0; i < indice; i++) {
            actual = actual.sig;
        }
        actual.valor = elem;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        primNodo = null;
        ultNodo = null;
        longLista = 0;
        listaNodoHandle = new ArrayList<Nodo>();

        Nodo actual = lista.primNodo;
        while (actual != null) {
            agregarAtras(actual.valor);
            actual = actual.sig;
        }
    }

    public int obtenerCantidadHandles() {
        return listaNodoHandle.size();
    }
    
    @Override
    public String toString() {
        String res = "[";
        Nodo actual = primNodo;
        while (actual != null) {
            res += actual.valor;
            if (actual.sig != null) {
                res += ", ";
            }
            actual = actual.sig;
        }
        res = res + "]";
        return res;
    }

    private class ListaIterador implements Iterador<T> {
        private Nodo punteroNodo;

        public ListaIterador() {
            this.punteroNodo = null;
        }

        public boolean haySiguiente() {
	        if (punteroNodo == null) {
                return primNodo != null;
            } else {
                return punteroNodo.sig != null;
            }
        }
        
        public boolean hayAnterior() {
	        return punteroNodo != null; 
            
        }

        public T siguiente() {

            if (punteroNodo == null) {
                punteroNodo = primNodo;
            } else {
                punteroNodo = punteroNodo.sig;
            }
            
	        return punteroNodo.valor;
        }
        

        public T anterior() {
            
	        T valor = punteroNodo.valor;
            punteroNodo = punteroNodo.ant;
            return valor;
        }
    }

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }

    public class Handle {
        private Nodo indice;

        public Handle(Nodo indice) {
            this.indice = indice;
        }

        public Nodo getIndice() {
            return indice;
        }

        public void setIndice(Nodo indice) {
            this.indice = indice;
        }
        
        public void eliminar() {
            if (indice == null) return;
            
            if (indice.ant != null) {
                indice.ant.sig = indice.sig;
            } else {
                primNodo = indice.sig;
            }
            
            if (indice.sig != null) {
                indice.sig.ant = indice.ant;
            } else {
                ultNodo = indice.ant;
            }
            
            int posicion = listaNodoHandle.indexOf(indice);
            if (posicion >= 0) {
                listaNodoHandle.remove(posicion);
            }
            
            longLista--;
        }
        
        public T obtenerValor() {
            return indice != null ? indice.valor : null;
        }
    }
}