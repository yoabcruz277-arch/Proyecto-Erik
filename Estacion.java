public class Estacion implements Comparable<Estacion>{

    private String nombre;
    private String linea;
    private int posicion;

    public Estacion(String nombre, String linea, int posicion){
        this.nombre=nombre;
        this.linea=linea;
        this.posicion=posicion;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getLinea(){
        return this.linea;
    }
    public int getPosicion(){
        return this.posicion;
    }

    @Override
    public int compareTo(Estacion e){
        return this.nombre.compareToIgnoreCase(e.getNombre());
    }

    @Override
    public boolean equals(Object xd){
        if(xd instanceof Estacion){
            Estacion e=(Estacion) xd;
            return this.nombre.equalsIgnoreCase(e.getNombre()) && this.linea.equalsIgnoreCase(e.getLinea());
        }
        return false;
    }

    @Override
    public String toString(){
        return "Nombre: "+this.nombre+", Línea: "+this.linea+ ", Posición(de izquierda a derecha): "+this.posicion;
    }
}
