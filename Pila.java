public class Pila<T> implements PiCoLa<T> {

    private class Nodo {
        public T elemento;
        public Nodo siguiente;

        public Nodo(T elemento) {
            this.elemento = elemento;
            this.siguiente = null;
        }
    }

    protected Nodo tope;
    private int tamanio;

    public Pila() {
        // Aquí va tu codigo 
        this.tope=null;
        this.tamanio=0;
    }

    @Override
    public void meter(T elemento) {
        // Aquí va tu codigo 
        if (elemento==null){
            throw new IllegalArgumentException("NO se permiten elementos nulos.");
        }
        Nodo nodo = new Nodo(elemento);
        nodo.siguiente= tope;
        tope = nodo;
        tamanio++;

    }

    @Override
    public T sacar() {
        // Aquí va tu codigo 
        if(estaVacia()){
            throw new IllegalArgumentException("NO existen elementos por sacar en la pila ;(");
        }
        T elemento  = tope.elemento;
        tope = tope.siguiente;
        tamanio--;
        return elemento;
    }

    @Override
    public T mira() {
        // Aquí va tu codigo 
        if(estaVacia()){
            throw new IllegalArgumentException("No existe un tope en la pila jeje");
        }
        return tope.elemento;
    }

    @Override
    public boolean estaVacia() {
        // Aquí va tu codigo 
        
        if(tamanio==0){
            return true;
        }
        return false;
    }

    @Override
    public int devolverTamanio() {
        // Aquí va tu codigo 
        return tamanio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        @SuppressWarnings("unchecked") Pila<T> m = (Pila<T>)o;
        Nodo n1 = this.tope;
        Nodo n2 = m.tope;
        while (n1 !=null && n2 != null){
            if (!n1.elemento.equals(n2.elemento))
                return false;
            n1 = n1.siguiente;
            n2 = n2.siguiente;
        }
        return (n1 == null && n2 == null);
    }

    @Override
    public String toString() {
        String resultado = "[";
        Nodo actual = tope;

        while (actual != null) {
            resultado += actual.elemento;
            if (actual.siguiente != null) {
                resultado += ",\n ";
            }
            actual = actual.siguiente;
        }

        resultado += "]";
        return resultado;
    }

}
