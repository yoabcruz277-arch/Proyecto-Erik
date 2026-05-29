import Proyecto.ListaDoblementeLigada;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase que gestiona la red del metro mediante el uso de una gráfica.
 * Tiene información sobre las estaciones, sus líneas, posiciones y las conexiones 
 * que sirven para calcular trayectos.
 */
public class Modelo {
    
    /** Lista que contiene a los vértices eliminados. */
    ListaDoblementeLigada<Estacion> estacionesEliminadas=new ListaDoblementeLigada<>();

    public static class Estacion implements Comparable<Estacion>{
        /** Nombre de la estación. */
        private String nombre;
        /** Nombre de la línea a la que pertenece la estación. */
        private String linea;
        /** Posición de la estación dentro de su línea correspondiente. */
        private int posicion;


        /**
         * Inicializa una nueva estación con los atributos correspondientes.
         * @param nombre Declara el nombre de la estación.
         * @param linea Declara la línea de la estación.
         * @param posicion Declara la posición de la estación en la línea.
         */
        public Estacion(String nombre, String linea, int posicion){
            this.nombre=nombre;
            this.linea=linea;
            this.posicion=posicion;
        }

        /**
         * Obtiene el nombre de la estación.
         * @return El nombre de la estación.
         */
        public String getNombre(){
            return this.nombre;
        }

        /**
         * Obtiene la línea a la que pertenece la estación.
         * @return La línea de la estación.
         */
        public String getLinea(){
            return this.linea;
        }
        /**
         * Obtiene la posición de la estación dentro de su línea.
         * @return La posición de la estación.
         */
        public int getPosicion(){
            return this.posicion;
        }


        /**
         * Compara esta estación con otra basándose en el nombre, 
         * ignorando el uso de mayúsculas y minúsculas.
         * @param e La estación con la cual se comparará.
         * @return Un número entero negativo, cero o positivo según el resultado de la comparación.
         */
        @Override
        public int compareTo(Estacion e){
            return this.nombre.compareToIgnoreCase(e.getNombre());
        }

        /**
         * Verifica si el objeto dado es igual a la estación evaluando su nombre y línea.
         * @param xd Objeto a comparar con la estación actual.
         * @return Verdadero si coinciden en nombre y línea, falso en caso contrario.
         */
        @Override
        public boolean equals(Object xd){
            if(xd instanceof Estacion){
                Estacion e=(Estacion) xd;
                return this.nombre.equalsIgnoreCase(e.getNombre()) && this.linea.equalsIgnoreCase(e.getLinea());
            }
            return false;
        }

        /**
         * Devuelve una cadena con los datos de la estación.
         * @return Cadena que contiene nombre, línea y posición.
         */
        @Override
        public String toString(){
            return "Nombre: "+this.nombre+", Línea: "+this.linea+ ", Posición(de izquierda a derecha): "+this.posicion;
        }
    }

    /** Lista doblemente ligada que almacena los vértices correspondientes a cada estación. */
    private ListaDoblementeLigada<Estacion> estaciones;
    /** Gráfica encargada de estructurar las conexiones y rutas del sistema. */
    private Grafica<Estacion> grafica;

    /**
     * Inicializa las estructuras base para manejar las estaciones e invoca 
     * el archivo de datos del metro.
     */
    public Modelo(){
        this.grafica=new GraficaListasAdyacencia<>();
        this.estaciones=new ListaDoblementeLigada<>();
        leerTodo("metro.txt");
    }

    /**
     * Lee el archivo de datos indicado para instanciar las estaciones y 
     * registrar sus conexiones dentro de la gráfica.
     * @param nombreArchivo Declara el nombre del archivo de texto a procesar.
     */
    public void leerTodo(String nombreArchivo){
        try(BufferedReader buffer=new BufferedReader(new FileReader(nombreArchivo))){
            String lineaActual;
            buffer.readLine();
            Estacion anterior= null;

            while ((lineaActual=buffer.readLine())!=null){
                String[] datos=lineaActual.split(",");
                String nombre=datos[0].trim();
                String lineaMetro=datos[1].trim();
                int posi=Integer.parseInt(datos[2].trim());
                Estacion nuevaEstacion=new Estacion((nombre), lineaMetro, posi);
                // Registra la estación como vértice en la gráfica y en la lista global
                grafica.agregarVertice(nuevaEstacion);
                estaciones.agregarFinal(nuevaEstacion);
                // Si hay una estación previa y es de la misma línea, crea el túnel(arista) entre ellas
                if(anterior!=null && anterior.getLinea().equals(lineaMetro)){
                    grafica.agregarAristaPonderada(anterior, nuevaEstacion, 1);
                }
                anterior=nuevaEstacion; // Desplaza la referencia para conectar la siguiente estación consecutiva
            }
            // Ejecuta el enlace cruzado de correspondencias entre líneas distintas
            conectarTransbordos();
        }catch(IOException e){
            System.out.println("Error al leer el archivo :v");
            return;
        }
    }

    /**
     * Conecta de manera cruzada las estaciones que comparten el mismo nombre en diferentes líneas.
     */
    public void conectarTransbordos(){
        int total=estaciones.devolverLongitud();
        // Compara cada estación contra todas las demás de la lista
        for(int i=0; i<total; i++){
            Estacion uno=estaciones.acceder(i);
            for(int j=i+1; j<total; j++){
                Estacion dos=estaciones.acceder(j);
                // Si los nombres coinciden, se detecta un transbordo
                if(uno.getNombre().equals(dos.getNombre())){
                    try{
                        grafica.agregarAristaPonderada(uno, dos, 1); // Añade la conexión bidireccional con peso de 1
                    }catch(IllegalArgumentException e){//si la arista ya fue vinculada de regreso

                    }
                }
            }
        }
    }
    
    /**
     * Busca un objeto estación validando su nombre y línea.
     * @param nombre Declara el nombre de la estación solicitada.
     * @param linea Declara la línea a la que debe pertenecer.
     * @return El objeto Estacion que coincide, o nulo si no existe.
     */
    public Estacion buscarEstacion(String nombre, String linea){
        // Itera sobre la lista doblemente ligada de estaciones
        for(int i=0; i<estaciones.devolverLongitud(); i++){
            Estacion e=estaciones.acceder(i);
            // Compara ignorando diferencias entre mayúsculas y minúsculas
            if(e.getNombre().equalsIgnoreCase(nombre.trim())&& e.getLinea().equalsIgnoreCase(linea.trim())){
                return e; // Devuelve la estación en cuanto encuentra la coincidencia
            }
        }
        return null; // Devuelve null si finaliza el ciclo sin éxito
    }

    /**
     * Obtiene y formatea todas las estaciones que pertenecen a una línea concreta.
     * * @param linea Declara la línea sobre la cual se hace la consulta.
     * @return Cadena que lista las estaciones pertenecientes a esa línea o un error en caso contrario.
     */
    public String estacionLinea(String linea){
        String rs="\n"+"Estaciones de la línea "+linea+"\n";
        boolean xd=false; // Bandera para verificar si la línea realmente existe o tiene datos
        // Recorre las estaciones
        for(int i=0;i<estaciones.devolverLongitud(); i++){
            Estacion e= estaciones.acceder(i);
            // Filtra únicamente las estaciones que pertenezcan a la línea buscada
            if(e.getLinea().equalsIgnoreCase(linea.trim())){
                rs=rs+" "+e.getNombre()+" Posición: "+e.getPosicion()+"\n";
                xd=true; // Da true al encontrar componentes
            }
        }
        // Valida si no se halló ninguna estación asociada a la cadena
        if(!xd){
            return "\n"+"No se encontro nadota para la línea"+linea;
        }
        return rs;
    }

    /**
     * Agrega una nueva estación al final de la línea elegida y la conecta a la gráfica.
     * @param nombre Declara el nombre que tendrá la nueva estación.
     * @param linea Declara la línea donde se añadirá esta estación.
     * @return Mensaje validando que se agrego de la estación o informando sobre algún problema.
     */
    public String agregarEstacion(String nombre, String linea){
        // Valida que la estación no exista previamente en esa misma línea
        if(buscarEstacion(nombre, linea)!=null){
            return "\n"+"Esa estacion ya esta en esa línea -.-";
        }
        int maxPos=0;
        Estacion theLast=null;
        // Ciclo para recorrer todas las estaciones y encontrar el extremo actual de la línea
        for(int i=0;i<estaciones.devolverLongitud();i++){ 
            Estacion e=estaciones.acceder(i);
            // Almacena la estación con el índice de posición más alto
            if(e.getLinea().equalsIgnoreCase(linea.trim())){
                if(e.getPosicion()>maxPos){
                    maxPos=e.getPosicion();
                    theLast=e;
                }
            }
        }
        // Instancia la nueva estación asignándole la posición siguiente
        Estacion nueva=new Estacion(nombre, linea, maxPos+1);
        try{
            // Agrega la estación
            grafica.agregarVertice(nueva);
            estaciones.agregarFinal(nueva);
            if(theLast!=null){ //para si no es la ultima, the last se conecte con la ultima
                grafica.agregarAristaPonderada(theLast, nueva, 1);
            }
            for(int i=0;i<estaciones.devolverLongitud();i++){ //para ver si es transbordo
                Estacion e=estaciones.acceder(i);
                // Si comparte nombre pero es un objeto distinto, crea la arista de transbordo
                if(e.getNombre().equalsIgnoreCase(nueva.getNombre())&&e!=nueva){
                    grafica.agregarAristaPonderada(e, nueva, 1);
                }
            }
            return "\n"+"Ya se agrego al final "+linea+ " :D";
        }catch(Exception e){
            return "\n"+"Ya lo vi jefe, pongalo bien(hubo un error): "+e.getMessage();
        }
    }

    /**
     * Elimina una estación registrada de la red del metro mediante su nombre y línea.
     * @param nombre Declara el nombre de la estación a borrar.
     * @param linea Declara la línea correspondiente de la estación.
     * @return Mensaje sobre el estado de eliminación.
     */
    public String eliminarEstacion(String nombre, String linea){
        boolean esperofuncione = false;
        for (int i = estaciones.devolverLongitud() - 1; i >= 0; i--) {
            Estacion est = estaciones.acceder(i);
            
            if (est.getNombre().equalsIgnoreCase(nombre)) {
                try {
                    grafica.eliminarVertice(est);
                    estaciones.eliminar(est);
                    estacionesEliminadas.agregarFinal(est);
                    esperofuncione = true;
                } catch (IllegalArgumentException e) {
                    return "\nTrono xddd xd: " + e.getMessage();
                }
            }
        }
        
        if (!esperofuncione) {
            return "\nRevisa bien la estacion papu :v";
        }
        
        return "\nJARVIS: la estación [" + nombre + "] fue exterminada con exito señor.";
    }

    /**
     * Restaura una estación desde el historial y reconstruye sus conexiones en la gráfica.
     * @param nombre Declara el nombre de la estación a restaurar.
     * @param linea Declara la línea de la estación a restaurar.
     * @return Texto con el resultado de la operación y el total de conexiones reconstruidas.
     */
    public String restaurarEstacionDesdeHistorial(String nombre, String linea) {
        Estacion nose = new Estacion(nombre, linea, 0);

        int indi = estacionesEliminadas.devolverIndiceElemento(nose);

        if (indi == -1) {
            return "\nEsta estacion si esta abierta mono";
        }

        Estacion chiga = estacionesEliminadas.acceder(indi);
        estacionesEliminadas.eliminar(indi); 

        grafica.agregarVertice(chiga);
        estaciones.agregarFinal(chiga); 
        int conexionesReconstruidas = 0;
       
        for (Estacion otra : estaciones) {
            if (otra.getLinea().equalsIgnoreCase(chiga.getLinea())) {
                if (Math.abs(otra.getPosicion() - chiga.getPosicion()) == 1) {
                    grafica.agregarAristaPonderada(chiga, otra, 1);
                    grafica.agregarAristaPonderada(otra, chiga, 1);
                    conexionesReconstruidas++;
                }
            }
            if (otra.getNombre().equalsIgnoreCase(chiga.getNombre()) && !otra.getLinea().equalsIgnoreCase(chiga.getLinea())) {
                grafica.agregarAristaPonderada(chiga, otra, 1);
                grafica.agregarAristaPonderada(otra, chiga, 1);
                conexionesReconstruidas++;
            }
        }
        return "\nEstación [" + nombre + " - Línea " + linea + "] se volvio a abrir Bv. Se recontruyeron sus transbordos: " + conexionesReconstruidas;    
    }

    /**
     * Genera y devuelve una cadena de texto con el historial de las estaciones que 
     * han sido eliminadas.
     * Si la lista de eliminadas está vacía, notifica que no hay registros.
     * @return Una cadena formateada con los nombres y líneas de las estaciones en el 
     * historial, o un mensaje indicando que está vacío.
     */
    public String mostrarEstacionesEliminadas() {
        if (estacionesEliminadas.devolverLongitud()==0) {
            return "\n"+"Está vacío xDDD";
        }
        String texto="\n"+"Estaciones eliminadas:"+"\n";
        for (int i=0; i<estacionesEliminadas.devolverLongitud(); i++) {
            Estacion e=estacionesEliminadas.acceder(i);
            texto += "[" + e.getNombre() + " Línea: " + e.getLinea() + "]"+"\n";
        }
        return texto;
    }


    /**
     * Une dos estaciones dentro de la red del metro por medio de una nueva arista.
     * @param nombreOri Declara el nombre de la estación de inicio del túnel.
     * @param lineaOri Declara la línea de la estación de inicio.
     * @param nombreDes Declara el nombre de la estación de fin del túnel.
     * @param lineaDes Declara la línea de la estación de fin.
     * @param peso Declara el coste de tiempo que representa este tramo.
     * @return Cadena que indica el resultado de conectar ambas estaciones.
     */
    public String agregarArista(String nombreOri, String lineaOri, String nombreDes, String lineaDes, int peso){
        Estacion sixe = buscarEstacion(nombreOri, lineaOri);
        Estacion ven = buscarEstacion(nombreDes, lineaDes);
        // Verifica que ninguna de las dos estaciones sea nula
        if (sixe == null || ven == null){
            return "\n"+"Monkey revisa bien las estaciones.";
        }try{
        // Añade el túnel ponderado a la gráfica conectando ambos extremos
        grafica.agregarAristaPonderada(sixe, ven, peso);
        return "\n"+"Se unieron las estacioes Papu :v";
        }catch(IllegalArgumentException e){
            return "\n"+"Error interno al crear túnel :,v " + e.getMessage();
        }
    }

    /**
     * Elimina un transbordo o arista que une a dos estaciones en la gráfica.
     * @param nombreOri Declara el nombre de la estación origen a desconectar.
     * @param lineaOri Declara la línea de la estación origen.
     * @param nombreDes Declara el nombre de la estación destino a desconectar.
     * @param lineaDes Declara la línea de la estación destino.
     * @return Cadena que indica el resultado de la eliminación del túnel.
     */
    public String eliminarArista(String nombreOri, String lineaOri, String nombreDes, String lineaDes){
        Estacion eso = buscarEstacion(nombreOri, lineaOri);
        Estacion tilin = buscarEstacion(nombreDes, lineaDes);
        // Valida que los dos extremos existan
        if (eso == null || tilin == null){
            return "\n"+"Revisa que existan las estaciones monkey";
        }
        try{
        // Elimina la arista de adyacencia de la gráfica
        grafica.eliminarArista(eso, tilin);
        return "\n"+"JARVIS: Se destruyo con exito señor ";
        }catch(IllegalArgumentException e){
            return "\n"+"Error al eliminar el tunel"+e.getMessage();
        }
    }

    /**
     * Cambia el contenido de la ruta de estaciones obtenida dentro de una cadena.
     * @param camino Lista de vértices correspondientes a las estaciones del recorrido.
     * @return Cadena que muestra el detalle paso a paso del trayecto y la cantidad de paradas.
     */
    public String cadenaCamino(ListaDoblementeLigada<Estacion> camino){
        if(camino.devolverLongitud()==0 ||camino==null){
            return "\n"+"No existe el camino ._.";
        }
        String textooo="El camino: ";
        // Construye de forma la cadena con el nombre y línea de cada estación
        for(int i=0; i<camino.devolverLongitud(); i++){
            textooo=textooo+"["+camino.acceder(i).getNombre()+" Línea: "+camino.acceder(i).getLinea()+"]";
            if(i<camino.devolverLongitud()-1){
                textooo=textooo+", ";
            }
        }
        // Añade el total de nodos recorridos al final
        textooo=textooo+"\n"+"Estaciones a recorrer: "+camino.devolverLongitud();
        return textooo;
    }

    /**
     * Calcula la ruta con el menor número de estaciones entre dos puntos de la red.
     * @param nombreOri Declara el nombre de la estación de inicio.
     * @param lineaOri Declara la línea de la estación inicial.
     * @param nombreDes Declara el nombre de la estación final.
     * @param lineaDes Declara la línea de la estación final.
     * @return Cadena de las estaciones involucradas en el recorrido óptimo.
     */
    public String caminoCortoNoPonderado(String nombreOri, String lineaOri, String nombreDes, String lineaDes){
        Estacion o=buscarEstacion(nombreOri, lineaOri);
        Estacion d=buscarEstacion(nombreDes, lineaDes);
        if(o==null||d==null){
            return "\n"+"Escribelo bien el nombre y la linea xdddd";
        }
        try{
        // Ejecuta el BFS
        ListaDoblementeLigada<Estacion> caminote=grafica.devolverRutaMasCortaNoPonderada(o, d);
        // Devuelve el camino formateado a texto
        return cadenaCamino(caminote);
        }catch(Exception e){
            return "\n"+"Hubo un error xdxdxd " +e.getMessage();
        }
    }

    /**
     * Calcula la ruta más rápida analizando los pesos (tiempo) entre un origen y un destino.
     * @param nombreOri Declara el nombre de la estación base de partida.
     * @param lineaOri Declara la línea a la que pertenece la estación base.
     * @param nombreDes Declara el nombre de la estación de llegada.
     * @param lineaDes Declara la línea de la estación de llegada.
     * @return Cadena con el formato correcto que indica las paradas por las que atraviesa.
     */
    public String caminoCortoPonderado(String nombreOri, String lineaOri, String nombreDes, String lineaDes){
        Estacion o=buscarEstacion(nombreOri, lineaOri);
        Estacion d=buscarEstacion(nombreDes, lineaDes);
        if(o==null||d==null){
            return "\n"+"Escribelo bien el nombre y la linea xdddd";
        }
        try{
        // Ejecuta la optimización basada en Dijkstra para obtener el camino de menor peso
        ListaDoblementeLigada<Estacion> caminote=grafica.rutaMasCortaPonderada(o, d);
        // Transforma la lista de estaciones en una cadena
        return cadenaCamino(caminote);
        }catch(Exception e){
            return "\n"+"Hubo un error xdxdxd " +e.getMessage();
        }
    }

    /**
     * Devuelve la cantidad total de vértices.
     * @return Cadena con el número de estaciones.
     */
    public String numeroEstaciones(){
        return "\n"+"El total de estaciones(vértices) es: "+grafica.devolverNumeroDeVertices();
    }

    /**
     * Devuelve la cantidad de aristas.
     * @return Cadena mostrando la cantidad de túneles.
     */
    public String numeroTuneles(){
        return "\n"+"El total de tuneles(aristas) es: "+grafica.devolverNumeroDeAristas();
    }

    /**
     * Obtiene los conjuntos conexos de estaciones que quedarían en el caso de eliminar una en específico.
     * @param nombre Declara el nombre de la estación a agarrar para la simulación.
     * @param linea Declara la línea donde se sitúa.
     * @return Cadena con las secciones de estaciones que quedan aisladas tras la eliminación.
     */
    public String obtenerComponentesConexas(String nombre, String linea){
        Estacion v=buscarEstacion(nombre, linea);
        if(v==null){
            return "\n"+"NO se encontro nadota wa wa :,v";
        }
        try{
            // Recupera la lista anidada con las subgráficas conexas resultantes
            ListaDoblementeLigada<ListaDoblementeLigada<Estacion>> componentes= grafica.componentesConexasDespuesDeEliminarVertice(v);
            if(componentes==null||componentes.devolverLongitud()==0){
                return "\n"+"No hay nadotaa xd";
            }
            String textote="\n"+"===Componentes Conexas==="+"\n";
            textote=textote+"Componentes encontradas: "+componentes.devolverLongitud()+"\n";
            // Itera sobre el conjunto completo de componentes identificadas
            for(int i=0;i<componentes.devolverLongitud(); i++){
                textote=textote+"\n"+"Componente "+(i+1)+":"+"\n";
                ListaDoblementeLigada<Estacion> comp=componentes.acceder(i);
                // Recorre las estaciones individuales de la componente actual
                for(int j=0; j<comp.devolverLongitud(); j++){
                    textote=textote+"["+comp.acceder(j).getNombre()+" Línea: "+comp.acceder(j).getLinea()+"]";
                    if(j<comp.devolverLongitud()-1){
                        textote=textote+" - ";
                    }
                }
                textote=textote+"\n";
            }
            return textote;
        }catch(Exception e){
            return "\n"+"Hubo un error wa wa "+ e.getMessage();
        }
    }

    /**
     * Convierte y agrupa la información individual de todas las estaciones registradas a texto.
     * @return Cadena listando todo el directorio de estaciones.
     */
    @Override
    public String toString(){
        if(estaciones.devolverLongitud()==0){
            return "\n"+"No hay estaciones xddd";
        }
        String todote="\n"+"===Todas las estaciones==="+"\n";
        for(int i=0; i<estaciones.devolverLongitud(); i++){
            todote= todote+estaciones.acceder(i).toString()+"\n";
        }
        return todote;
    }
}
