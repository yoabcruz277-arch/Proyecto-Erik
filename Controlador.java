/**
 * Clase principal que tiene el main.
 * Gestiona el flujo del programa vinculando la interfaz de usuario con la lógica del sistema del metro.
 * @author Cruz Prieto Donovan Yoab, Gómez Flores Sergio Erick, Navarro Moreno Daniel
 * @version mayo de 2026
 */
public class Controlador {

    /**
     * La entrada principal del programa.
     * Despliega el menú interactivo, captura las peticiones del usuario mediante la vista 
     * y ejecuta las operaciones correspondientes en el modelo de la gráfica.
     * @param args Argumentos.
     */
    public static void main(String[] args) {
        Modelo modelo=new Modelo();
        Vista vista=new Vista();
        int opcion=0;

        vista.titulo();

        do{
            vista.menu();
            opcion=vista.opcion();

            if(opcion==-1){
                vista.imprimir("\n"+ "Pon un número, no letras :v");
                continue;
            }
            
            switch(opcion){
                case 1:
                    vista.imprimir(modelo.toString());
                    break;
                
                case 2:
                    String lineaBuscada=vista.texto("Escribe la línea del metro que quieras: ");
                    vista.imprimir(modelo.estacionLinea(lineaBuscada));
                    break;
                case 3:
                    if (!verificarAdmin(vista)) break;
                    String nombreAgr=vista.texto("Escribe el nombre de la estación que quieres agregar: ");
                    String lineaAgr=vista.texto("Escribe el nombre de la línea de la estación: ");
                    vista.imprimir(modelo.agregarEstacion(nombreAgr, lineaAgr));
                    break;

                case 4:
                    if (!verificarAdmin(vista)) break;
                    String nombreElim=vista.texto("Escribe el nombre de la estación que quieres eliminar: ");
                    String lineaElim=vista.texto("Escribe el nombre de la línea de la estación: ");
                    vista.imprimir(modelo.eliminarEstacion(nombreElim, lineaElim));
                    break;

                case 5:
                    if (!verificarAdmin(vista)) break;
                    String eso=vista.texto("Escribe el nombre de la estación de origen: ");
                    String tilin=vista.texto("Escribe el nombre de la línea de la estación de origen: ");
                    String sixse=vista.texto("Escribe el nombre de la estación de destino: ");
                    String ven=vista.texto("Escribe el nombre de la línea de la estación de destino: ");
                    int pesoPluma=vista.entero("Peso del túnel/tiempo: ");
                    if (pesoPluma==-1) {
                        vista.imprimir("\n"+"Error xd, el peso debe ser un número :v");
                    }else{
                        vista.imprimir(modelo.agregarArista(eso, tilin, sixse, ven, pesoPluma));
                    }
                    break;  

                case 6:
                    if (!verificarAdmin(vista)) break;
                    String niñoDel=vista.texto("Escribe el nombre de la estación de origen: ");
                    String oxxo=vista.texto("Escribe el nombre de la línea de la estación de origen: ");
                    String ete=vista.texto("Escribe el nombre de la estación de destino: ");
                    String sech=vista.texto("Escribe el nombre de la línea de la estación de destino: ");
                    vista.imprimir(modelo.eliminarArista(niñoDel, oxxo, ete, sech));
                    break;

                case 7:
                    String nomOrig=vista.texto("Escribe el nombre de la estación de origen: ");
                    String linOrig=vista.texto("Escribe el nombre de la línea de la estación de origen: ");
                    String nomDest=vista.texto("Escribe el nombre de la estación de destino: ");
                    String linDest=vista.texto("Escribe el nombre de la línea de la estación de destino: ");
                    vista.imprimir(modelo.caminoCortoNoPonderado(nomOrig, linOrig, nomDest, linDest));
                    break;  
                
                case 8:
                    String nomOrig2=vista.texto("Escribe el nombre de la estación de origen: ");
                    String linOrig2=vista.texto("Escribe el nombre de la línea de la estación de origen: ");
                    String nomDest2=vista.texto("Escribe el nombre de la estación de destino: ");
                    String linDest2=vista.texto("Escribe el nombre de la línea de la estación de destino: ");
                    vista.imprimir(modelo.caminoCortoPonderado(nomOrig2, linOrig2, nomDest2, linDest2));
                    break;

                case 9:
                    vista.imprimir(modelo.numeroEstaciones());
                    break;

                case 10:
                    vista.imprimir(modelo.numeroTuneles());
                    break;
                
                case 11:
                    if (!verificarAdmin(vista)) break;
                    String nomComp=vista.texto("Nombre de la estación que quieres simular su eliminación: ");
                    String linComp=vista.texto("Línea de la estación: ");
                    vista.imprimir(modelo.obtenerComponentesConexas(nomComp, linComp));
                    break;
                
                case 12:
                    vista.imprimir(modelo.mostrarEstacionesEliminadas());
                    break;
                
                case 13:
                    vista.imprimir("CHAO");
                    break;

                default:
                    vista.imprimir("Esa opción no jala, intenta de nuevo :,v");
            }

        }while(opcion!=13);
        vista.apagarAuto();
    }
    /**
     * Solicita la contraseña al usuario para operaciones de administrador.
     * @param vista El objeto vista para interactuar con el usuario.
     * @return true si la contraseña es correcta, false si es incorrecta.
     */
    private static boolean verificarAdmin(Vista vista) {
        String pass = vista.texto("SUDO MODE: ingresa la palabra magica.");
        if (pass.equals("sixeventungtung123")) { 
            vista.imprimir("SUDO MODE: ACTIVADO");
            return true;
        }else {
            vista.imprimir("SUDO MODE: DENEGADO");
            return false;
        }
    }
}
