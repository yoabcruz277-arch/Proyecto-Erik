public class Cola<T> implements PiCoLa<T> {

    // Aquí va tu codigo 
    private class Nodo{
        public T elemento;
        public Nodo siguiente;

        public Nodo(T elemento) {
            this.elemento = elemento;
            this.siguiente = null;
        }
    
    }
     protected Nodo primero;
    private int tamanio;
    protected Nodo fin;
    public Cola() {
        // Aquí va tu codigo 
        this.primero=null;
        this.tamanio=0;
        this.fin=null;
    }
    @Override
    public void meter(T elemento) {
        // Aquí va tu codigo 
        if (elemento==null){
            throw new IllegalArgumentException("NO se permiten elementos nulos.");
        }
        Nodo nodo = new Nodo(elemento);
        if(estaVacia()){
            primero=nodo;
            fin=nodo;
            
        }else{
            fin.siguiente=nodo;
        fin = nodo;
        }
        tamanio++;
        
    }

    @Override
    public T sacar() {
        // Aquí va tu codigo 
        if(estaVacia()){
            throw new IllegalArgumentException("NO hay elementos en la cola :(");
        }
        
        T elemento=primero.elemento;
        primero=primero.siguiente;
        if(primero==null){
            fin=null;
        }
        tamanio--;
        return elemento;
    }

    @Override
    public T mira() {
        // Aquí va tu codigo 
        if(estaVacia()){
            throw new IllegalArgumentException("NO hay elementos en la cola :(");
        }
        return primero.elemento;
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
        @SuppressWarnings("unchecked") Cola<T> m = (Cola<T>)o;
        Nodo n1 = this.primero;
        Nodo n2 = m.primero;
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
        Nodo actual = primero;

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
  