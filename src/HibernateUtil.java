import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil{
    private static SessionFactory sessionFactory;
    final static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static{
        try{
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }catch(Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    public static SessionFactory getSessionFactory () {
        return sessionFactory;
    }
}