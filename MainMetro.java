import java.util.Scanner;

public class MainMetro {
    public static void main(String[] args) {
        Metro metrote=new Metro();
        Scanner sc=new Scanner(System.in);
        int opcion=0;

        System.out.println("=============================");
        System.out.println("   Menu de Rutas del Metro   ");
        System.out.println("=============================");

        do{
            System.out.println();
            System.out.println("1. Mostrar estaciones del metro (completamente");
            System.out.println("2. Mostrar estaciones por línea");
            System.out.println("3. Agregar estación (agrega vértice y arista)");
            System.out.println("4. Eliminar estación (elimina vétice)");
            System.out.println("5. Agregar transborde o tunel (agregar arista)");
            System.out.println("6. Eliminar transborde o tunel (eliminar arista)");
            System.out.println("7. Camino más corto sin considerar estaciones");
            System.out.println("8. Camino más corto considerando estaciones o tramos cerrados");
            System.out.println("9. Devolver número de estaciones (vértices)");
            System.out.println("10. Devolver número de tuneles (aristas)");
            System.out.println("11. Mostrar componentes conexas tras eliminar estación");
            System.out.println("12. Salir");
            System.out.println();

            try{
                opcion=sc.nextInt();
                sc.nextLine(); 
            }catch(java.util.InputMismatchException e) {
                System.out.println("\n"+"Ingresa un número válido, no letras :v");
                sc.nextLine(); 
                continue; 
            }

            switch(opcion){
                case 1:
                    System.out.println(metrote.toString());
                    break;

                case 2:
                    System.out.println("Escribe la línea del metro que quieras: ");
                    String lineaBuscada=sc.nextLine();
                    System.out.println(metrote.estacionLinea(lineaBuscada));
                    break;
                
                case 3:
                    System.out.println("Escribe el nombre de la estación que quieres agregar: ");
                    String nombreAgr=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de  la estación: ");
                    String lineaAgr=sc.nextLine();
                    System.out.println(metrote.agregarEstacion(nombreAgr, lineaAgr));
                    break;
                
                case 4:
                   System.out.println("Escribe el nombre de la estación que quieres eliminar: ");
                    String nombreElim=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de  la estación: ");
                    String lineaElim=sc.nextLine(); 
                    System.out.println(metrote.eliminarEstacion(nombreElim, lineaElim));
                    break;
                case 5:
                    System.out.println("Escribe el nombre de la estación de origen: ");
                    String eso=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de la estación de origen: ");
                    String tilin=sc.nextLine();
                    System.out.println("Escribe el nombre de la estación de destino: ");
                    String sixse=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de la estación de destino: ");
                    String ven=sc.nextLine();
                    System.out.println("Peso del túnel/tiempo: ");
                    int pesoNuevo=sc.nextInt();
                    sc.nextLine();
                    System.out.println(metrote.agregarArista(eso, tilin, sixse, ven, pesoNuevo));
                    break;

                case 6:
                    System.out.println("Escribe el nombre de la estación de origen: ");
                    String niñoDel=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de la estación de origen: ");
                    String oxxo=sc.nextLine();
                    System.out.println("Escribe el nombre de la estación de destino: ");
                    String ete=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de la estación de destino: ");
                    String sech=sc.nextLine();
                    System.out.println(metrote.eliminarArista(niñoDel, oxxo, ete, sech));
                    break;

                case 7:
                    System.out.println("Escribe el nombre de la estación de origen: ");
                    String nomOrig=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de la estación de origen: ");
                    String linOrig=sc.nextLine();
                    System.out.println("Escribe el nombre de la estación de destino: ");
                    String nomDest=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de la estación de destino: ");
                    String linDest = sc.nextLine();
                    System.out.println(metrote.caminoCortoNoPonderado(nomOrig, linOrig, nomDest, linDest));
                    break;

                case 8:
                    System.out.println("Escribe el nombre de la estación de origen: ");
                    String nomOrig2=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de la estación de origen: ");
                    String linOrig2=sc.nextLine();
                    System.out.println("Escribe el nombre de la estación de destino: ");
                    String nomDest2=sc.nextLine();
                    System.out.println("Escribe el nombre de la línea de la estación de destino: ");
                    String linDest2= sc.nextLine();
                    System.out.println(metrote.caminoCortoPonderado(nomOrig2, linOrig2, nomDest2, linDest2));
                    break;
                
                case 9:
                    System.out.println(metrote.numeroEstaciones());
                    break;

                case 10:
                    System.out.println(metrote.numeroTuneles());
                    break;

                case 11:
                    System.out.println("Nombre de la estación que quieres simular su eliminación: ");
                    String nomComp=sc.nextLine();
                    System.out.println("Línea de la estación: ");
                    String linComp=sc.nextLine();
                    System.out.println(metrote.obtenerComponentesConexas(nomComp, linComp));
                    break;
                
                case 12:
                    System.out.println("CHAO");
                    break;
                
                default:
                    System.out.println("Esa opción no jala, intenta de nuevo :V");
                
            }

        } while(opcion!=12);
        sc.close();
    }
    
}
