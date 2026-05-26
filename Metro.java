import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Metro {

    private ListaDoblementeLigada<Estacion> estaciones;
    private Grafica<Estacion> grafica;

    public Metro(){
        this.grafica=new GraficaListas<>();
        this.estaciones=new ListaDoblementeLigada<>();
        leerTodo("metro.txt");
    }

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
                grafica.agregarVertice(nuevaEstacion);
                estaciones.agregarFinal(nuevaEstacion);
                if(anterior!=null && anterior.getLinea().equals(lineaMetro)){
                    grafica.agregarAristaPonderada(anterior, nuevaEstacion, 1);
                }
                anterior=nuevaEstacion;
            }
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
        for(int i=0; i<total; i++){
            Estacion uno=estaciones.acceder(i);
            for(int j=i+1; j<total; j++){
                Estacion dos=estaciones.acceder(j);
                if(uno.getNombre().equals(dos.getNombre())){
                    try{
                        grafica.agregarAristaPonderada(uno, dos, 1);
                    }catch(IllegalArgumentException e){//si la arista ya fue vinculada de regreso

                    }
                }
            }
        }
    }
    
    /**
     * Busca un objeto estación validando su nombre y línea.
     */
    public Estacion buscarEstacion(String nombre, String linea){
        for(int i=0; i<estaciones.devolverLongitud(); i++){
            Estacion e=estaciones.acceder(i);
            if(e.getNombre().equalsIgnoreCase(nombre.trim())&& e.getLinea().equalsIgnoreCase(linea.trim())){
                return e;
            }
        }
        return null;
    }

    public String estacionLinea(String linea){
        String rs="\n"+"Estaciones de la línea "+linea+"\n";
        boolean xd=false;
        for(int i=0;i<estaciones.devolverLongitud(); i++){
            Estacion e= estaciones.acceder(i);
            if(e.getLinea().equalsIgnoreCase(linea.trim())){
                rs=rs+" "+e.getNombre()+" Posición: "+e.getPosicion()+"\n";
                xd=true;
            }
        }
        if(!xd){
            return "\n"+"No se encontro nadota para la línea"+linea;
        }
        return rs;
    }

    public String agregarEstacion(String nombre, String linea){
        if(buscarEstacion(nombre, linea)!=null){
            return "\n"+"Esa estacion ya esta en esa línea -.-";
        }
        int maxPos=0;
        Estacion theLast=null;
        for(int i=0;i<estaciones.devolverLongitud();i++){ //para recorrer todo
            Estacion e=estaciones.acceder(i);
            if(e.getLinea().equalsIgnoreCase(linea.trim())){
                if(e.getPosicion()>maxPos){
                    maxPos=e.getPosicion();
                    theLast=e;
                }
            }
        }
        Estacion nueva=new Estacion(nombre, linea, maxPos+1);
        try{
            grafica.agregarVertice(nueva);
            estaciones.agregarFinal(nueva);
            if(theLast!=null){ //para si no es la ultima, the last se conecte con la ultima
                grafica.agregarAristaPonderada(theLast, nueva, 1);
            }
            for(int i=0;i<estaciones.devolverLongitud();i++){ //para ver si es transbordo
                Estacion e=estaciones.acceder(i);
                if(e.getNombre().equalsIgnoreCase(nueva.getNombre())&&e!=nueva){
                    grafica.agregarAristaPonderada(e, nueva, 1);
                }
            }
            return "\n"+"Ya se agrego al final "+linea+ " :D";
        }catch(Exception e){
            return "\n"+"Ya lo vi jefe, pongalo bien(hubo un error): "+e.getMessage();
        }
    }

    public String eliminarEstacion(String nombre, String linea){
        Estacion eliminada=buscarEstacion(nombre, linea);
        if(eliminada!=null){
            try{
                grafica.eliminarVertice(eliminada);
                estaciones.eliminar(eliminada);
                return "\n"+"Si se pudo eliminar: "+eliminada;
            }catch(Exception e){
                return "\n"+"Hubo un error xdd al eliminar: "+ eliminada;
            }
        }else{
            return "\n"+ "No se encontro la estación, verifica sus datos :,v";
        }
    }


    public String agregarArista(String nombreOri, String lineaOri, String nombreDes, String lineaDes, int peso){
        Estacion sixe = buscarEstacion(nombreOri, lineaOri);
        Estacion ven = buscarEstacion(nombreDes, lineaDes);
        if (sixe == null || ven == null){
            return "\n"+"Monkey revisa bien las estaciones.";
        }try{
        grafica.agregarAristaPonderada(sixe, ven, peso);
        return "\n"+"Se unieron las estacioes Papu :v";
        }catch(IllegalArgumentException e){
            return "\n"+"Error interno al crear túnel :,v " + e.getMessage();
        }
    }

    public String eliminarArista(String nombreOri, String lineaOri, String nombreDes, String lineaDes){
        Estacion eso = buscarEstacion(nombreOri, lineaOri);
        Estacion tilin = buscarEstacion(nombreDes, lineaDes);

        if (eso == null || tilin == null){
            return "\n"+"Revisa que existan las estaciones monkey";
        }
        try{
        grafica.eliminarArista(eso, tilin);
        return "\n"+"JARVIS: Se destruyo con exito señor ";
        }catch(IllegalArgumentException e){
            return "\n"+"Error al eliminar el tunel"+e.getMessage();
        }
    }


    public String cadenaCamino(ListaDoblementeLigada<Estacion> camino){
        if(camino.devolverLongitud()==0 ||camino==null){
            return "\n"+"No existe el camino ._.";
        }
        String textooo="El camino: ";
        for(int i=0; i<camino.devolverLongitud(); i++){
            textooo=textooo+"["+camino.acceder(i).getNombre()+" Línea: "+camino.acceder(i).getLinea()+"]";
            if(i<camino.devolverLongitud()-1){
                textooo=textooo+", ";
            }
        }
        textooo=textooo+"\n"+"Estaciones a recorrer: "+camino.devolverLongitud();
        return textooo;
    }


    public String caminoCortoNoPonderado(String nombreOri, String lineaOri, String nombreDes, String lineaDes){
        Estacion o=buscarEstacion(nombreOri, lineaOri);
        Estacion d=buscarEstacion(nombreDes, lineaDes);
        if(o==null||d==null){
            return "\n"+"Escribelo bien el nombre y la linea xdddd";
        }
        try{
        ListaDoblementeLigada<Estacion> caminote=grafica.devolverRutaMasCortaNoPonderada(o, d);
        return cadenaCamino(caminote);
        }catch(Exception e){
            return "\n"+"Hubo un error xdxdxd " +e.getMessage();
        }
    }

    public String caminoCortoPonderado(String nombreOri, String lineaOri, String nombreDes, String lineaDes){
        Estacion o=buscarEstacion(nombreOri, lineaOri);
        Estacion d=buscarEstacion(nombreDes, lineaDes);
        if(o==null||d==null){
            return "\n"+"Escribelo bien el nombre y la linea xdddd";
        }
        try{
        ListaDoblementeLigada<Estacion> caminote=grafica.rutaMasCortaPonderada(o, d);
        return cadenaCamino(caminote);
        }catch(Exception e){
            return "\n"+"Hubo un error xdxdxd " +e.getMessage();
        }
    }

    public String numeroEstaciones(){
        return "\n"+"El total de estaciones(vértices) es: "+grafica.devolverNumeroDeVertices();
    }

    public String numeroTuneles(){
        return "\n"+"El total de tuneles(aristas) es: "+grafica.devolverNumeroDeAristas();
    }

    public String obtenerComponentesConexas(String nombre, String linea){
        Estacion v=buscarEstacion(nombre, linea);
        if(v==null){
            return "\n"+"NO se encontro nadota wa wa :,v";
        }
        try{
            ListaDoblementeLigada<ListaDoblementeLigada<Estacion>> componentes= grafica.componentesconexas(v);
            if(componentes==null||componentes.devolverLongitud()==0){
                return "\n"+"No hay nadotaa xd";
            }
            String textote="\n"+"===Componentes Conexas==="+"\n";
            textote=textote+"Componentes encontradas: "+componentes.devolverLongitud()+"\n";
            for(int i=0;i<componentes.devolverLongitud(); i++){
                textote=textote+"\n"+"Componente "+(i+1)+":"+"\n";
                ListaDoblementeLigada<Estacion> comp=componentes.acceder(i);
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
