import BD.Album;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Gand on 23/02/17.
 */
public class Resumen {

    static Scanner teclado;

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        teclado = new Scanner(System.in);

        System.out.println("Selecciona un ejercicio:");
        System.out.println("\t1.- CARGA DE DATOS.");
        System.out.println("\t2.- CONSULTAS DE MANIPULACION (Update, Delete, Insert)");
        char e = teclado.nextLine().charAt(0);

        switch (e){
            case '1':   ejer1(); menu();
                break;
            case '2':   ejer2(); menu();
                break;
            case '3':   ejer3(); menu();
                break;

            default:
                menu();
        }
    }

    private static void ejer1() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        //--------------------------------------------------------------//
        Album alb = new Album();
        alb = session.load(Album.class, 1); //Indicando la PK del objeto a buscar
        System.out.println("TITULO: " + alb.getTituloAl() + ", AÑO: " + alb.getAnio());

        //**************************************************************//
        //Con el método get se puede comprobar si el objeto es nulo o no, con el load no
        Album alb2 = new Album();
        alb2 = session.get(Album.class, 2);
        if (alb2 == null)
            System.out.println("El album NO existe");
        else
            System.out.println("TITULO: " + alb2.getTituloAl() + ", AÑO: " + alb2.getAnio());
        //--------------------------------------------------------------//
        tx.commit();
        session.close();
    }//1

    private static void ejer2() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        //--------------------------------------------------------------//
        //Insert = save
        Album alb = new Album();        //Instancia un objeto vacío
        alb.setId(20);
        alb.setTituloAl("Album20");     //Añade los datos con los métodos set del objeto
        alb.setAnio(1999);
        session.save(alb);              //Se usa save para guardar el objeto en la BD

        //**************************************************************//
        //Delete = delete
        Album alb2 = new Album();                           //Se instancia un objeto vacío
        alb2 = session.load(Album.class, 20);    //Se carga el objeto con un load o get
        session.delete(alb2);                               //Se borra el objeto con delete
        //**************************************************************//
        //Update = update
        Album alb3 = new Album();                                        //Se instancia un objeto vacío
        alb3 = session.load(Album.class, 1);                  //Se carga el objeto con un load o get
        System.out.println("Titulo antiguo: " + alb3.getTituloAl());     //Mostramos datos antiguos
        alb3.setTituloAl("Numero1");                                     //Modificamos los datos del objeto con los setter
        session.update(alb3);                                            //Modificamos el objeto con update
        System.out.println("Titulo nuevo: " + alb3.getTituloAl());       //Mostramos datos nuevos

        //--------------------------------------------------------------//
        tx.commit();
        session.close();
    }//2

    private static void ejer3() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        //--------------------------------------------------------------//
        String consulta = "from Album";
        Query q = session.createQuery(consulta);

        //Mostramos datos con list
        List<Album> list = q.list();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Album album = (Album) iterator.next();
            System.out.println ( "* TITULO: " + album.getTituloAl() + ", AÑO: " + album.getAnio());
        }

        //Mosrtramos con iterate
        Iterator iterator1 = q.iterate();
        while (iterator1.hasNext()){
            Album album = (Album) iterator1.next();
            System.out.println ( "* TITULO: " + album.getTituloAl() + ", AÑO: " + album.getAnio());
        }

        //--------------------------------------------------------------//
        tx.commit();
        session.close();
    }//3

    //Resumen de HQL
    /**

        Update:
     String update = "update Album SET Anio = :anio WHERE tituloAl = :titulo";

     int filas = session.createQuery(update)
        .setInt("anio", 1990)
        .setString("titulo", "Numero1")
        .executeUpdate();

     if (filas != 0)
        System.out.println("Registros actualizados = " + filas);
     else
        System.out.println("No se ha podido actualizar nada");

     **************************************************************************************
        Delete:
     String delete = "delete Album WHERE id = ?"

     int filas = session.createQuery(delete)
        .setInteget( 0, 1)
        .executeUpdate();

     if (filas != 0)
        System.out.println("Registro eliminado");
     else
        System.out.println("No se ha borrado ningun registro");

     *****
     * tx.rollback(); //Deshace la transacción
     * tx.commit(); //Valida la transacción
     *****

     *****************************************************************************
        Insert:
     String insert = "insert into Album (id, titulo, anio) select Album.id, Album.tituloAl, Album.anio from Album";

     int filas = session.createQuery(insert)
        .executeUpdate();

     if (filas != 0)
     System.out.println("Registro añadido");
     else
     System.out.println("No se ha añadido ningun registro");

     */
}
