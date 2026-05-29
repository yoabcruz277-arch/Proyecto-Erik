import Proyecto.ListaDoblementeLigada;
/**
 * Interfaz que define el comportamiento para el TDA Gráfica.
 * Estructura los métodos necesarios para la manipulación de vértices, aristas 
 * y recorridos básicos dentro de la red.
 *
 * @param <T> El tipo de elemento que almacenará la gráfica.
 */
public interface Grafica<T> extends Iterable<T> {

    /**
     * Agrega un nuevo vértice a la gráfica.
     * @param elemento Declara el dato a almacenar en el vértice.
     */
    public void agregarVertice(T elemento);

    /**
     * Agrega una nueva arista no ponderada entre dos vértices existentes.
     * @param e1 Declara el elemento del vértice de origen.
     * @param e2 Declara el elemento del vértice de destino.
     */
    public void agregarArista(T e1, T e2);
    
    /**
     * Agrega una nueva arista ponderada entre dos vértices existentes.
     * @param e1 Declara el elemento del vértice de origen.
     * @param e2 Declara el elemento del vértice de destino.
     * @param peso Declara el peso de la conexión.
     */
    public void agregarAristaPonderada(T e1, T e2, int peso);

    /**
     * Obtiene el recorrido de la gráfica mediante Búsqueda en Anchura (BFS).
     * @param inicio Declara el elemento desde donde comienza el recorrido.
     * @return Una lista doblemente ligada con los elementos en el orden visitado.
     */
    public ListaDoblementeLigada<T> devolverBfs(T inicio);

    /**
     * Obtiene el recorrido de la gráfica mediante Búsqueda en Profundidad (DFS).
     * @param inicio Declara el elemento desde donde comienza el recorrido.
     * @return Una lista doblemente ligada con los elementos en el orden visitado.
     */
    public ListaDoblementeLigada<T> devolverDfs(T inicio);

    /**
     * Elimina un vértice de la gráfica y todas sus conexiones incidentes.
     * @param elemento Declara el elemento del vértice a eliminar.
     */
    public void eliminarVertice(T elemento);

    /**
     * Elimina la arista entre dos vértices.
     * @param e1 Declara el elemento del vértice de origen.
     * @param e2 Declara el elemento del vértice de destino.
     */
    public void eliminarArista(T e1, T e2);

    /**
     * Calcula la ruta más corta sin considerar los pesos de las aristas.
     * @param inicio Declara el elemento inicial de la ruta.
     * @param fin Declara el elemento final de la ruta.
     * @return Una lista doblemente ligada con los elementos correspondientes al trayecto.
     */
    public ListaDoblementeLigada<T> devolverRutaMasCortaNoPonderada(T inicio, T fin);
    
    /**
     * Calcula la ruta más corta tomando en cuenta el peso de cada conexión.
     * @param inicio Declara el elemento inicial del recorrido.
     * @param fin Declara el elemento final del recorrido.
     * @return Una lista doblemente ligada indicando el camino de menor costo.
     */
    public ListaDoblementeLigada<T> rutaMasCortaPonderada(T inicio, T fin);
    
    /**
     * Obtiene la cantidad total de vértices de la gráfica.
     * @return El número de vértices actuales.
     */
    public int devolverNumeroDeVertices();
    
    /**
     * Obtiene la cantidad total de aristas de la gráfica.
     * @return El número de aristas actuales.
     */
    public int devolverNumeroDeAristas(); 

    /**
     * Calcula y devuelve las componentes conexas resultantes tras la eliminación de un vértice.
     * @param v Declara el elemento a aislar temporalmente para el análisis.
     * @return Una lista que contiene listas de elementos correspondientes a cada componente.
     */
    public ListaDoblementeLigada<ListaDoblementeLigada<T>> componentesConexasDespuesDeEliminarVertice(T v);
    
}
