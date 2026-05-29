import Proyecto.ListaDoblementeLigada; 
import miCola.Cola;                    
import java.util.Iterator;

/**
 * Clase que implementa la estructura de una gráfica mediante listas de adyacencia.
 * Gestiona la conexión de los vértices y la manipulación de aristas para 
 * resolver trayectos y búsquedas.
 *
 * @param <T> El tipo de datos que almacenan los vértices.
 */
public class GraficaListasAdyacencia<T> implements Grafica<T>{

    /** Estructura que almacena secuencialmente los vértices de la gráfica. */
    private ListaDoblementeLigada <Vertice> vertices;
    /** Cantidad total de vértices existentes. */
    private int numeroDeVertices;
    /** Cantidad total de aristas existentes. */
    private int numeroDeAristas;
   
    /**
    * Clase interna que representa un vértice dentro de la gráfica.
    * Almacena su elemento y la lista de sus conexiones directas.
    */
   public class Vertice{
        /** Dato principal almacenado en el vértice. */
        T elemento;
        /** Lista de conexiones hacia otros vértices. */
        ListaDoblementeLigada<Arista> aristas;
        
        /** Estado que indica si el vértice ya fue visitado en algún recorrido. */
        public boolean visitado;
        
        /** Valor que indica la distancia mínima desde un punto de origen. */
        public int distanciaOrigen;
        
        /**
         * Inicializa un nuevo vértice con sus valores predeterminados.
         * @param elemento Declara el valor que contendrá el vértice.
         */
        public Vertice(T elemento ){
            this.elemento=elemento;
            this.aristas= new ListaDoblementeLigada<Arista>();
            this.visitado=false;
            this.distanciaOrigen=Integer.MAX_VALUE;
            this.anterior=null;
        }

        /** Referencia al vértice previo en la ruta calculada. */
        public Vertice anterior;

        
    }

    /**
     * Clase interna que representa una arista o conexión entre dos vértices.
     * Maneja la referencia hacia el vértice destino y el peso asociado.
     */
    public class Arista{
        /** Vértice al cual apunta al vecino. */
        Vertice vecino;
        /** Peso numérico de la arista. */
        public int peso;

        /**
         * Inicializa una arista asignándole un vecino y un peso.
         * @param vecino Declara el vértice de destino.
         * @param peso Declara el valor del trayecto.
         */
        public Arista(Vertice vecino, int peso){
            this.vecino=vecino;
            this.peso=peso;
        }

    }
    /**
     * Inicializa una gráfica vacía con los contadores en ceros.
     */
    public GraficaListasAdyacencia(){
    vertices= new ListaDoblementeLigada<>();
    numeroDeAristas=0;
    numeroDeVertices=0;
    }
    
    /**
     * Clase interna encargada de proveer iteración secuencial sobre los elementos de la gráfica.
     */
    private class IteradorGrafica implements Iterator<T>{
        /** Iterador que opera directamente sobre la lista de vértices. */
        private Iterator<Vertice> iterador;
        /**
         * Inicializa el iterador tomando como base los vértices registrados.
         */
        public IteradorGrafica(){
            iterador= vertices.iterator();
        }

        /**
         * Comprueba si aún quedan elementos por iterar.
         * @return Verdadero en caso de haber un siguiente elemento, falso de lo contrario.
         */
        @Override
        public boolean hasNext(){
            return iterador.hasNext();
        }

        /**
         * Obtiene el elemento subsecuente en la iteración.
         * @return El dato extraído del próximo vértice.
         */
        @Override
        public T next(){
            Vertice v= iterador.next();
            return v.elemento;
        }
    }

    /**
     * Devuelve un objeto iterador configurado para recorrer la gráfica.
     * @return Instancia activa de iterador.
     */
    @Override
    public Iterator<T> iterator() {
        return new IteradorGrafica(); 
    }

    /**
     * Valida la existencia de un elemento específico dentro de la colección de vértices.
     * @param elemento Declara el valor que se desea buscar.
     * @return Verdadero si el vértice se localiza, falso en su defecto.
     */
    public boolean buscarVertice(T elemento){
        for(Vertice v: vertices){
            if (v.elemento.equals(elemento)){
                return true;
            }
        }
        return false;
    }

    /**
     * Agrega un vértice a la gráfica tras asegurar que no se duplique.
     * @param elemento Declara el elemento del vértice.
     */
    @Override
    public void agregarVertice(T elemento){
    if(elemento == null){
    throw new IllegalArgumentException("El elemento es nulo");
}
    if(buscarVertice(elemento)!=false){
        throw new IllegalArgumentException("Ya se encuentra el Vertice en la grafica");
    }
    Vertice verticito=new Vertice(elemento);
    vertices.agregarFinal(verticito);
    numeroDeVertices++;
    }

    /**
     * Obtiene el objeto Vertice que contiene al elemento dado.
     * @param elemento Declara el elemento a buscar.
     * @return El vértice encontrado o nulo si no existe.
     */
    public Vertice  obtenervertice(T elemento){
    for (Vertice v:vertices){
        if(v.elemento.equals(elemento)){
            return v;
        }
    }
    return null;
    }

    /**
     * Verifica la existencia de una arista directa desde el vértice de origen al destino.
     * @param u Declara el vértice base.
     * @param v Declara el vértice objetivo a buscar entre las adyacencias.
     * @return Verdadero en caso de haber conexión, falso en caso contrario.
     */
    public boolean buscarArista(Vertice u, Vertice v){
    for(Arista a:v.aristas){
        if(a.vecino.equals(u)){
            return true;
        }
    }
    return false;
 }

    /**
     * Conecta de forma bidireccional dos vértices (agrega una arista) usando el 
     * valor predeterminado de peso 1.
     * @param e1 Declara el primer extremo de la arista.
     * @param e2 Declara el segundo extremo de la arista.
     */
    @Override
    public void agregarArista(T e1, T e2){
        if(e1==null||e2==null){
            throw new IllegalArgumentException("El vertice u o v son nulos");
        }
        if(buscarVertice(e1)==false||buscarVertice(e2)==false){
            throw new IllegalArgumentException("El vertice u o v no existen");
        }
        if(e1.equals(e2)){
            throw new IllegalArgumentException("Los vertices u y v son iguales , no se puede añadir la conexion");
        }
        Vertice v1= obtenervertice(e1);
        Vertice v2=obtenervertice(e2);
        if(buscarArista(v1, v2)==false&&buscarArista(v2, v1)==false){
            Arista arista1= new Arista(v1,1);
            Arista arista2=new Arista(v2, 1);  
            v1.aristas.agregarFinal(arista2); 
            v2.aristas.agregarFinal(arista1);    
            numeroDeAristas++;     
        }
    }

    /**
     * Enlaza dos vértices guardando el valor (arista ponderada)
     * del trayecto dentro de la arista.
     * @param e1 Declara la información del nodo inicial.
     * @param e2 Declara la información del nodo destino.
     * @param peso Declara el número de peso de esta conexión cruzada.
     */
    @Override
    public void agregarAristaPonderada(T e1, T e2, int peso){
         if(e1==null||e2==null){
            throw new IllegalArgumentException("El vertice u o v son nulos");
        }
        if(buscarVertice(e1)==false||buscarVertice(e2)==false){
            throw new IllegalArgumentException("El vertice u o v no existen");
        }
        if(e1.equals(e2)){
            throw new IllegalArgumentException("El vertice u o v no existen");
        }
        if (peso <= 0) {
        throw new IllegalArgumentException("El peso debe ser positivo");
        }
        Vertice v1= obtenervertice(e1);
        Vertice v2=obtenervertice(e2);
        if(buscarArista(v1, v2)==false&&buscarArista(v2, v1)==false){
            Arista arista1= new Arista(v1,peso);
            Arista arista2=new Arista(v2, peso);  
            v1.aristas.agregarFinal(arista2); 
            v2.aristas.agregarFinal(arista1);    
            numeroDeAristas++;     
        }

    }

    /**
     * Construye una ruta utilizando el recorrido por anchura de sus componentes (BFS).
     * @param inicio Declara el punto de arranque de la búsqueda.
     * @return El orden de visita almacenado secuencialmente.
     */
    @Override
    public ListaDoblementeLigada<T> devolverBfs(T inicio){
        ListaDoblementeLigada<T> recorrido = new ListaDoblementeLigada<T>();
        if (numeroDeVertices==0){
            return recorrido;
        }
        Vertice inicial = obtenervertice(inicio);
        if (inicial == null) {
            throw new IllegalArgumentException("El vertice inicial no existe");
        }
        for (Vertice v : vertices) {
            v.visitado = false;
        }
        Cola<Vertice> colita = new Cola<Vertice>();
        colita.meter(inicial);
        inicial.visitado=true;
        while(!colita.estaVacia()){
            Vertice actual=colita.sacar();
            recorrido.agregarFinal(actual.elemento);
            for(Arista a:actual.aristas){
                if(a.vecino.visitado==false){
                    colita.meter(a.vecino);
                    a.vecino.visitado=true;
                }
                }
                
            }
            return recorrido;
    }

    /**
     * Genera una lista representando la exploración a profundidad del sistema (DFS).
     * @param inicio Declara el elemento base.
     * @return La secuencia cronológica de los vértices alcanzados.
     */
    @Override
    public ListaDoblementeLigada<T> devolverDfs(T inicio){
        ListaDoblementeLigada<T> recorrido = new ListaDoblementeLigada<T>();
        if(numeroDeVertices==0){
            return recorrido;
        }
        Vertice inicial = obtenervertice(inicio);
        if (inicial == null) {
            throw new IllegalArgumentException("El vertice inicial no existe");
        }
                for (Vertice v : vertices) {
                     v.visitado = false;
                 }
                Pila <Vertice> pilita = new Pila<Vertice>();
                pilita.meter(inicial);
                inicial.visitado=true;
                while(!pilita.estaVacia()){
                Vertice actual=pilita.sacar();
                recorrido.agregarFinal(actual.elemento);
                for(Arista a:actual.aristas){
                if(a.vecino.visitado==false){
                    pilita.meter(a.vecino);
                    a.vecino.visitado=true;
                }
                }
            }
                return recorrido;
    }

    /**
     * Elimina de la lista de aristas de v la conexión hacia u.
     * @param v Declara el vértice dueño de la lista de aristas.
     * @param u Declara el vértice vecino a borrar de las conexiones.
     */
    public void eliminarAristaAux(Vertice v, Vertice u){
    Arista aristaEliminar = null;

    for(Arista a : v.aristas){
        if(a.vecino.equals(u)){
            aristaEliminar = a;
            break;
        }
    }

    if(aristaEliminar != null){
        v.aristas.eliminar(aristaEliminar);
        }
    }
    
    /**
     * Elimina un vértice y sus aristas incidentes de la gráfica.
     * @param elemento Declara el elemento del vértice a borrar.
     */
    @Override
    public void eliminarVertice(T elemento){

        if(elemento == null){
            throw new IllegalArgumentException("El vertice a eliminar es nulo");
        }

        Vertice verticeEliminar = obtenervertice(elemento);

        if(verticeEliminar == null){
            throw new IllegalArgumentException("El vertice a eliminar no existe");
        }

        int aristasEliminadas = 0;

        for(Arista a : verticeEliminar.aristas){

            eliminarAristaAux(a.vecino, verticeEliminar);

            aristasEliminadas++;
        }

        vertices.eliminar(verticeEliminar);

        numeroDeVertices--;

        numeroDeAristas -= aristasEliminadas;
    }
    @Override

    /**
     * Agarra ambos puntos eliminando la unión (arista).
     * @param e1 Declara el dato inicial.
     * @param e2 Declara el dato en el extremo inverso.
     */
    public void eliminarArista(T e1, T e2){
    if(e1==null||e2==null){
        throw new IllegalArgumentException("EL vertice u o v son nulos");
    }
    if(buscarVertice(e1)==false||buscarVertice(e2)==false){
        throw new IllegalArgumentException("EL vertice u o v no existen en la Grafica");
    }
    if(e1.equals(e2)){
        throw new IllegalArgumentException("EL vertice u o v son el mismo");
    }
    Vertice v1= obtenervertice(e1);
            Vertice v2=obtenervertice(e2);
    if(buscarArista(v1, v2)==false||buscarArista(v2, v1)==false){
    throw new IllegalArgumentException("La arista a eliminar no existe");
    }
    eliminarAristaAux(v1, v2);
        eliminarAristaAux(v2, v1);
        numeroDeAristas--;
    }   

   /**
     * Obtiene la ruta con la menor cantidad de vértices posibles entre dos puntos.
     * @param inicio Declara el elemento de origen.
     * @param fin Declara el elemento destino.
     * @return Lista con los elementos que forman el camino más corto.
     */
    @Override
    public ListaDoblementeLigada<T> devolverRutaMasCortaNoPonderada(T inicio, T fin){
        if (!buscarVertice(inicio) || !buscarVertice(fin)){
            throw new IllegalArgumentException("Checa tus vertices mono"); 
        }
        Vertice vertiD = new Vertice(null);
        Vertice vertiO = new Vertice(null);
        for (Vertice v : vertices){
            v.anterior = null; 
            v.visitado = false;
            
            if (v.elemento.equals(inicio)){
                vertiO = v;
            }
            if (v.elemento.equals(fin)){
                vertiD = v; 
            }
        }
        Cola<Vertice> colita = new Cola<Vertice>();
        vertiO.visitado = true;
        colita.meter(vertiO);
        while (!colita.estaVacia()){
            Vertice actual = colita.sacar();
            if (!actual.elemento.equals(fin)){
                for (Arista a : actual.aristas){
                    if(a.vecino.visitado == false){
                        a.vecino.anterior = actual;
                        a.vecino.visitado = true;
                        colita.meter(a.vecino);
                    }
                }
            }
        }
        if (vertiD.visitado == false){
            throw new IllegalArgumentException("Trono xd");
        }
        ListaDoblementeLigada<T> camino = new ListaDoblementeLigada<>();
        Vertice actual = vertiD;
        while (actual != null){
            camino.agregar(actual.elemento);
            actual = actual.anterior;
        }
        ListaDoblementeLigada<T> caminoCorrecto = new ListaDoblementeLigada<>();
        
        for (int i = camino.devolverLongitud() - 1; i >= 0; i--) {
            T elementoEeee = camino.acceder(i);
            caminoCorrecto.agregar(elementoEeee);
        }
        return caminoCorrecto; 
    }

    /**
     * Reinicia las variables de distancia y estado de todos los vértices.
     */
    private void iniciarVertices() {
        for (Vertice v : vertices) {
            v.anterior = null;
            v.visitado = false;
            v.distanciaOrigen = Integer.MAX_VALUE;
        }
    }

    /**
     * Busca el vértice no visitado que tenga la menor distancia acumulada.
     * @return El vértice con la menor distancia.
     */
    private Vertice menorDistancia() {
        Vertice minimo = null;
        int minDist = Integer.MAX_VALUE;
        for (Vertice v : vertices) {
            if (v.visitado == false && v.distanciaOrigen < minDist) {
                minDist = v.distanciaOrigen;
                minimo = v;
            }
        }
        return minimo;
    }

    /**
     * Recolecta el rastro mediante los enlaces anteriores hasta su sentido original.
     * @param destino Declara el punto donde terminó.
     * @return El grupo estructurado como camino lógico final.
     */
    private ListaDoblementeLigada<T> construirRuta(Vertice destino) {
        ListaDoblementeLigada<T> caminoInvertido = new ListaDoblementeLigada<>();
        Vertice rastreador = destino;
        while (rastreador != null) {
            caminoInvertido.agregar(rastreador.elemento);
            rastreador = rastreador.anterior;
        }

        ListaDoblementeLigada<T> caminoCorrecto = new ListaDoblementeLigada<>();
        for (int i = caminoInvertido.devolverLongitud() - 1; i >= 0; i--) {
            caminoCorrecto.agregar(caminoInvertido.acceder(i));
        }
        return caminoCorrecto;
    }

    /**
     * Calcula la ruta de menor peso acumulado entre dos vértices.
     * @param inicio Declara el elemento de partida.
     * @param fin Declara el elemento de llegada.
     * @return Lista con la ruta ponderada más corta.
     */
    @Override
    public ListaDoblementeLigada<T> rutaMasCortaPonderada(T inicio, T fin){
        if (!buscarVertice(inicio) || !buscarVertice(fin)) {
            throw new IllegalArgumentException("Checa tus vertices mono, no existen");
        }

        iniciarVertices();

        Vertice vertiO = null;
        Vertice vertiD = null;
        
        for (Vertice v : vertices) {
            if (v.elemento.equals(inicio)) vertiO = v;
            if (v.elemento.equals(fin)) vertiD = v;
        }
        
        vertiO.distanciaOrigen = 0;

        for (int i = 0; i < devolverNumeroDeVertices(); i++) {
            
            Vertice actual = menorDistancia();

            if (actual == null || actual.distanciaOrigen == Integer.MAX_VALUE || actual.elemento.equals(fin)) {
                break;
            }

            actual.visitado = true;

            for (Arista a : actual.aristas) {
                if (a.vecino.visitado == false) {
                    int nuevaDistancia = actual.distanciaOrigen + a.peso;
                    
                    if (nuevaDistancia < a.vecino.distanciaOrigen) {
                        a.vecino.distanciaOrigen = nuevaDistancia;
                        a.vecino.anterior = actual;
                    }
                }
            }
        }

        if (vertiD == null || vertiD.distanciaOrigen == Integer.MAX_VALUE) {
            return new ListaDoblementeLigada<>(); 
        }
        
        return construirRuta(vertiD);
    }

    /**
     * Devuelve las componentes conexas que se generan al eliminar un vértice de la gráfica.
     * @param v Declara el elemento del vértice a eliminar.
     * @return Una lista de listas con los elementos de cada componente conexa.
     */
    public ListaDoblementeLigada<ListaDoblementeLigada<T>> componentesConexasDespuesDeEliminarVertice(T v) {
        
        ListaDoblementeLigada<ListaDoblementeLigada<T>> componentes = new ListaDoblementeLigada<>();

        if (v == null) {
            throw new IllegalArgumentException("Revisa el vertice lince");
        }
        if (!buscarVertice(v)) {
            throw new IllegalArgumentException("No existe el vertice xddd");
        }
        eliminarVertice(v);
        for (Vertice vertice : vertices) {
            vertice.visitado = false;
        }
        for (Vertice vertice : vertices) {
            if (vertice.visitado == false) {
                ListaDoblementeLigada<T> componente = new ListaDoblementeLigada<>();
                
                Cola<Vertice> cola = new Cola<>();
                vertice.visitado = true;
                cola.meter(vertice);

                while (!cola.estaVacia()) {
                    Vertice actual = cola.sacar();
                    componente.agregar(actual.elemento);
                    for (Arista a : actual.aristas) {
                        if (a.vecino.visitado == false) {
                            a.vecino.visitado = true;
                            cola.meter(a.vecino);
                        }
                    }
                }
                componentes.agregar(componente);
            }
        }
        return componentes;
    }

    /**
     * Muestra el total actual de vértices de la gráfica.
     * @return Representación numérica de vértices.
     */
    @Override
    public int devolverNumeroDeVertices(){
        return numeroDeVertices;
    }

    /**
     * Muestra el total actual de aristas de la gráfica.
     * @return Representación numérica de aristas.
     */
    @Override
    public int devolverNumeroDeAristas(){
        return numeroDeAristas;
    }
}
