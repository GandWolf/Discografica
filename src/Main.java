/**
 * Created by Gand on 23/02/17.
 */
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import BD.Album;
import BD.Cancion;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author Gand
 */
public class Main {

    static Session session;
    static Transaction tx;

    static Scanner teclado = new Scanner(System.in);
    static boolean salir = false;

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
//        session = HibernateUtil.getSessionFactory().openSession();
//
//        menu();
//
//        session.close();
//    }//Fin main

    private static void menu() {
        int ejer = 0;
        while (!salir){
            System.out.println(
                    "+--------------------------------------------------+\n" +
                            "|            BASE DE DATOS DISCOGRÁFICA            |\n" +
                            "+--------------------------------------------------+\n" +
                            "\nSELECCIONA UNA OPCION:\n" +
                            "\n– Consultar una sola fila\n" +
                            "\t(1.-)* Recupera un album cuyo id se pasa a través del teclado.\n" +
                            "\n– Consultar varias filas sin utilizar query\n" +
                            "\t(2.-)* Consultar los datos de la tabla album donde el titulo empiece por  'B%'\"\n" +
                            "\n– Consultar varias filas (tabla cancion) con query y con el metodo list() y  con el metodo iterate().\n" +
                            "\t(3.-)* obtener datos de todas las canciones de un album dado su título\n" +
                            "\n– Consultar varias filas con query y con parámetros: dos formas:\n" +
                            "        Parametros nombrados\n" +
                            "        Parametros por posicion\n" +
                            "        Consultar varias filas con query y funciones de grupo\n" +
                            "\t\t(4.-)* cuantas canciones tiene el numero id 3 (cancion)\n" +
                            "\t\t(5.-)* visualizar el id más alto en cancion\n" +
                            "\t(6.-)* visualizar el el total en minutos de cada id de album\n" +
                            "\n\tSENTENCIAS DE MANIPULACION:\n" +
                            "\n(7.-)* Actualizar la duracion en cancion de dos formas:\n" +
                            "\t(8.-)* Modificación del año sumándole 1 en todos los id menores de 10.\n" +
                            "\tCon createquery()  con metodo  update\n" +
                            "\n– Borrar la cancion de titulo “prueba”\n" +
                            "\t(9.-)* Borrado de una fila con metodo delete y con createquery().\n" +
                            "\n– Insertar un registro en cancion\n" +
                            "\t(10.-)* Inserción de una fila con metodo save y con createquery().\n" +
                            "\t(0.-)* SALIR\n");

            ejer = teclado.nextInt();
            switch (ejer){
                case 1: ejercicio1();
                    break;
                case 2: ejercicio2();
                    break;
                case 3: ejercicio3();
                    break;
                case 4: ejercicio4();
                    break;
                case 5: ejercicio5();
                    break;
                case 6: ejercicio6();
                    break;
                case 7: ejercicio7();
                    break;
                case 8: ejercicio8();
                    break;
                case 9: ejercicio9();
                    break;
                case 10: ejercicio10();
                    break;
                case 0: salir = true;
                    break;
                default:
            }
        }

    }//Fin menu

    private static void ejercicio1() {
        tx = session.beginTransaction();
        //Recupera un album cuyo id se pasa a través del teclado.
        System.out.println("Introduce el id del albúm: ");
        int idAlb = teclado.nextInt();

        String c = "from Album where id = " + idAlb;

        Query q = session.createQuery(c);
        List results = q.list();

        Iterator albumiterator = results.iterator();
        while (albumiterator.hasNext()) {
            Album alb = (Album) albumiterator.next();
            System.out.println ( "* ID: " + alb.getId() + ", TITULO: "
                    + alb.getTituloAl() + ", AÑO: " + alb.getAnio() );
        }

        tx.commit();
    }//Fin ejer1

    private static void ejercicio2() {
        //Consultar los datos de la tabla album donde el titulo empiece por  'B%'
        System.out.println("Album donde el titulo empiece por: ");
        char ini = teclado.nextLine().charAt(0);

        Query q = session.createQuery("from Album where tituloAl like '" + ini + "%'");
        List results = q.list();

        Iterator albumiterator = results.iterator();
        while (albumiterator.hasNext()) {
            Album alb = (Album) albumiterator.next();
            System.out.println ( "* ID: " + alb.getId() + ", TITULO: "
                    + alb.getTituloAl() + ", AÑO: " + alb.getAnio() );
        }
    }//Fin ejer2

    private static void ejercicio3() {
        tx = session.beginTransaction();
        teclado = new Scanner(System.in);
        //Obtener datos de todas las canciones de un album dado su título
        System.out.println("Introduce el título del albúm: ");
        String titulo = teclado.nextLine();

        String c = "select titulo, Cancion.duracion, Cancion.letra, Cancion.idAlbum " +
                "from Cancion , Album  where Album.id=Cancion.idAlbum and tituloAl='" + titulo + "'";

        String d = "from Cancion where idAlbum = (select id from Album where tituloAl='" + titulo + "')";

//        String f = "from Album where ti"

        Query q = session.createQuery(d);
        List results = q.list();

        Iterator cancioniterator = results.iterator();
        while (cancioniterator.hasNext()) {
            Cancion cnc = (Cancion) cancioniterator.next();
            System.out.println ( "* TITULO: " + cnc.getTitulo() + ", DURACION: "
                    + cnc.getDuracion() + ", LETRA: " + cnc.getLetra() +
                    ", IDALBUM: " + cnc.getIdAlbum());
        }
        tx.commit();
    }//Fin ejer3

    private static void ejercicio4() {
        //cuantas canciones tiene el numero id 3 (cancion)
        System.out.println("Introduce el id del albúm: ");
        int idAlb = teclado.nextInt();

        Query q1 = session.createQuery("from Cancion where idAlbum =:num");
        q1.setInteger("num", idAlb);

        Query q2 = session.createQuery("from Cancion where idAlbum = ?");
        q2.setInteger(0, idAlb);

        System.out.println("Resultados con idAlbum =:num");
        List results1 = q1.list();

        Iterator cancioniterator1 = results1.iterator();
        while (cancioniterator1.hasNext()) {
            Cancion cnc = (Cancion) cancioniterator1.next();
            System.out.println ( "* TITULO: " + cnc.getTitulo() + ", DURACION: "
                    + cnc.getDuracion() + ", LETRA: " + cnc.getLetra() +
                    ", IDALBUM: " + cnc.getIdAlbum());
        }

        System.out.println("Resultados con idAlbum =?");
        List results2 = q2.list();

        Iterator cancioniterator2 = results2.iterator();
        while (cancioniterator2.hasNext()) {
            Cancion cnc = (Cancion) cancioniterator2.next();
            System.out.println ( "* TITULO: " + cnc.getTitulo() + ", DURACION: "
                    + cnc.getDuracion() + ", LETRA: " + cnc.getLetra() +
                    ", IDALBUM: " + cnc.getIdAlbum());
        }
    }//Fin ejer4

    private static void ejercicio5() {
        //visualizar el id más alto en cancion
        System.out.println("Canciones con el IDAlbum más alto");

        String c = "from Cancion  where idAlbum = max(idAlbum)";

        Query q = session.createQuery(c);
        List results = q.list();

        Iterator cancioniterator = results.iterator();
        while (cancioniterator.hasNext()) {
            Cancion cnc = (Cancion) cancioniterator.next();
            System.out.println ( "* TITULO: " + cnc.getTitulo() + ", DURACION: "
                    + cnc.getDuracion() + ", LETRA: " + cnc.getLetra() +
                    ", IDALBUM: " + cnc.getIdAlbum());
        }
    }//Fin ejer5

    private static void ejercicio6() {
        //visualizar el el total en minutos de cada id de album
        System.out.println("Duracion total de cada album");

        Query q = session.createQuery("select Album.tituloAl, count(Cancion.duracion) as total "
                + "from Cancion , Album  where Album.id=Cancion.idAlbum "
                + "group by Album.tituloAl");
        List results = q.list();

        Iterator itinerator = results.iterator();
        while (itinerator.hasNext()) {
            Object result[] = (Object[]) itinerator.next();
            System.out.println ( "* TITULO: " + result[0] + ", DURACION: "
                    + result[1] );
        }
    }//Fin ejer6

    private static void ejercicio7() {
        //Actualizar la duracion en cancion de dos formas
        System.out.println("Actualización en +5seg la duración de las canciones");
        System.out.println("Introduce el id del albúm: ");
        int idAlb = teclado.nextInt();

        Query q = session.createQuery("update Cancion set duracion = duracion + 5 "
                + "where idAlbum = " + idAlb );
        int result = q.executeUpdate();
        if (result != 0){
            System.out.println ("Duración actualizada correctamente");
        }
    }//Fin ejer7

    private static void ejercicio8() {
        //Modificación del año sumándole 1 en todos los id menores de 10
        System.out.println("Modificación del año sumándole 1 en todos los id menores de 10");

        Query q = session.createQuery("update Album set anio = anio + 1 "
                + "where id < 10");
        int result = q.executeUpdate();
        if (result != 0){
            System.out.println ("Año actualizado correctamente");
        }
    }//Fin ejer8

    private static void ejercicio9() {
        //Borrado de una fila con metodo delete y con createquery()
        System.out.println("Introduce el titulo de la cancion a borrar: ");
        String titulo = teclado.nextLine();

        Query query = session.createQuery("delete Cancion where titulo = :titulo");
        query.setParameter("titulo", titulo);
        int result = query.executeUpdate();
        if (result != 0){
            System.out.println("Canción \"" + titulo + "\" borrada correctamente");
        }
    }//Fin ejer9

    private static void ejercicio10() {
        //Inserción de una fila con metodo save y con createquery()
        System.out.println("Introduce el titulo de la nueva cancion: ");
        String titulo = teclado.nextLine();
        System.out.println("Introduce la duracion de la nueva cancion: ");
        int duracion = teclado.nextInt();
        System.out.println("Introduce la letra de la nueva cancion: ");
        String letra = teclado.nextLine();
        System.out.println("Introduce el id del album de la nueva cancion: ");
        int album = teclado.nextInt();

        Cancion cnc = new Cancion (titulo, duracion, letra, album);

        Query query = session.createQuery("insert into Cancion "
                + "(titulo, duracion, letra, idAlbum) select titulo, duracion, letra, idAlbum from Cancion ");
//        query.setParameter("titulo", titulo);
        int result = query.executeUpdate();
        if (result != 0){
            System.out.println("Canción \"" + titulo + "\" añadida correctamente");
        }
    }//Fin ejer10



}//Fin Class
