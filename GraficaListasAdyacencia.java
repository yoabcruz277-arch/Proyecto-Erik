public class GraficaListasAdyacencia<T> implements Grafica<T>{
   private ListaDoblementeLigada <Vertice> vertices;
   private int numeroDeVertices;
   private int numeroDeAristas;
    public class Vertice{
        T elemento;
        ListaDoblementeLigada<Arista> aristas;
         public boolean visitado;
        public int distanciaOrigen;
        public Vertice(T elemento ){
            this.elemento=elemento;
            this.aristas= new ListaDoblementeLigada<Arista>();
            this.visitado=false;
            this.distanciaOrigen=Integer.MAX_VALUE;
            this.anterior=null;
        }
        public Vertice anterior;

        
    }
    public class Arista{
        Vertice vecino;
        public int peso;
        public Arista(Vertice vecino, int peso){
            this.vecino=vecino;
            this.peso=peso;
        }

    }
    public GraficaListasAdyacencia(){
    vertices= new ListaDoblementeLigada<>();
    numeroDeAristas=0;
    numeroDeVertices=0;
    }
    @Override
    public java.util.Iterator<T> iterator() {
        return null; 
    }

    @Override
 public void agregarVertice(T elemento){

 }
 @Override
    public void agregarArista(T e1, T e2){

    }
    @Override
    public void agregarAristaPonderada(T e1, T e2, int peso){

    }
    @Override
    public ListaDoblementeLigada<T> devolverBfs(T inicio){
        return null;
    }
    @Override
    public ListaDoblementeLigada<T> devolverDfs(T inicio){
        return null;
    }
    @Override
    public void eliminarVertice(T elemento){

    }
    @Override
    public void eliminarArista(T e1, T e2){

    }
@Override
    public ListaDoblementeLigada<T> devolverRutaMasCortaNoPonderada(T inicio, T fin){
        return null;
    }
    @Override
    public ListaDoblementeLigada<T> rutaMasCortaPonderada(T inicio, T fin){
        return  null;
    }
    @Override
    public int devolverNumeroDeVertices(){
        return numeroDeVertices;
    }
    @Override
    public int devolverNumeroDeAristas(){
        return numeroDeAristas;
    }
}